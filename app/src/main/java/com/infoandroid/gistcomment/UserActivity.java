package com.infoandroid.gistcomment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.infoandroid.gistcomment.OkhttpRest.ApiIds;
import com.infoandroid.gistcomment.OkhttpRest.ResponceListeners;
import com.infoandroid.gistcomment.OkhttpRest.RestClass;
import com.infoandroid.gistcomment.adapter.GistRepoAdapter;
import com.infoandroid.gistcomment.model.GistRepo;
import com.infoandroid.gistcomment.preferences.AppSharedPreference;

import org.json.JSONArray;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class UserActivity extends Activity implements ResponceListeners{

    SharedPreferences sharedPreferences;
    public static final String TAG = UserActivity.class.getSimpleName();
    public static final String PREFERENCE = "github_prefs";
    String url;
    String repoName;
    RestClass restClass;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        recyclerView=findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sharedPreferences = getSharedPreferences(PREFERENCE, 0);
        String oauthToken = sharedPreferences.getString("oauth_token", null);
        url= AppSharedPreference.getString("url","",UserActivity.this);
        try {
            URL aURL = new URL(url);
            repoName=aURL.getFile();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "oauth token for github loged in user is :" + oauthToken);
        String bodyData="{\n" +
                "  \"body\": \"Just commenting for the sake of commenting from Android App hahaha\"\n" +
                "}";
        restClass = new RestClass(UserActivity.this);
        try {
            restClass.callback(this).asynchronousGet(ApiIds.API_USER_LIST,"https://api.github.com/users"+repoName+"/gists","");
            //restClass.callback(UserActivity.this).postJsonRequest(ApiIds.API_USER_LIST, "https://api.github.com/gists/cc05a5802850c1e109d932adb59b01de/comments",oauthToken,bodyData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponce(int apiId, Object responce) {
        final List<GistRepo> list;
                try {
                    GistRepo[] base= new Gson().fromJson(responce.toString(),GistRepo[].class);
                 list = Arrays.asList(base);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GistRepoAdapter gistRepoAdapter = new GistRepoAdapter(UserActivity.this, list);
                            recyclerView.setAdapter(gistRepoAdapter);

                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }


        /*runOnUiThread(new Runnable() {
            public void run() {
                GistRepoAdapter gistRepoAdapter = new GistRepoAdapter(this, List);
                recyclerView.setAdapter(gistRepoAdapter);
            }

        }*/

    }

    @Override
    public void onFailearResponce(int apiId, String error) {

    }
}
