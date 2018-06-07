package com.infoandroid.gistcomment.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.infoandroid.gistcomment.OkhttpRest.ApiIds;
import com.infoandroid.gistcomment.OkhttpRest.ResponceListeners;
import com.infoandroid.gistcomment.OkhttpRest.RestClass;
import com.infoandroid.gistcomment.R;
import com.infoandroid.gistcomment.adapter.GistRepoAdapter;
import com.infoandroid.gistcomment.model.GistRepo;
import com.infoandroid.gistcomment.preferences.AppSharedPreference;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UserActivity extends AppCompatActivity implements ResponceListeners{

    public static final String TAG = UserActivity.class.getSimpleName();
    public static final String PREFERENCE = "github_prefs";
    String url;
    String repoName;
    RestClass restClass;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.activity_user)
    RelativeLayout activityUser;

    @BindView(R.id.tvWarning)
    TextView tvWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        String oauthToken = AppSharedPreference.getString("oauth_token", null,UserActivity.this);
        url= AppSharedPreference.getString("url","",UserActivity.this);
        try {
            URL aURL = new URL(url);
            repoName=aURL.getFile();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "oauth token for github loged in user is :" + oauthToken);
        restClass = new RestClass(UserActivity.this);
        try {
            restClass.showProgDialog();
            restClass.callback(this).asynchronousGet(ApiIds.API_USER_LIST,"https://api.github.com/users"+repoName+"/gists","");
            } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponce(int apiId, Object responce) {
        restClass.hideProgDialog();
        final List<GistRepo> list;
                try {
                    GistRepo[] base= new Gson().fromJson(responce.toString(),GistRepo[].class);
                     list = Arrays.asList(base);
                    if (list.size()!=0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GistRepoAdapter gistRepoAdapter = new GistRepoAdapter(UserActivity.this, list);
                            recyclerView.setAdapter(gistRepoAdapter);

                        }
                    });

                    }else {
                        recyclerView.setVisibility(View.GONE);
                        tvWarning.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);

    }

    @Override
    public void onFailearResponce(int apiId, String error) {
        restClass.hideProgDialog();
        restClass.showErrorSnackBar(activityUser,error);
    }
}
