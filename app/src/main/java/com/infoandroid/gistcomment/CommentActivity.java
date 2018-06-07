package com.infoandroid.gistcomment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.infoandroid.gistcomment.OkhttpRest.ApiIds;
import com.infoandroid.gistcomment.OkhttpRest.ResponceListeners;
import com.infoandroid.gistcomment.OkhttpRest.RestClass;
import com.infoandroid.gistcomment.preferences.AppSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity implements ResponceListeners{
    @BindView(R.id.edt_comment)
    EditText edtComment;

     @BindView(R.id.button)
     Button button;

     @BindView(R.id.rootView)
     ConstraintLayout rootView;

        RestClass restClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        restClass = new RestClass(CommentActivity.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edtComment.getText().toString();
                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj.put("body",comment);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                comment=jsonobj.toString();

                try {
                    String username = AppSharedPreference.getString("name","",CommentActivity.this);
                    String password = AppSharedPreference.getString("pass","",CommentActivity.this);;
                    String id = AppSharedPreference.getString("id","",CommentActivity.this);;
                    String credentials = username + ":" + password;
                    restClass.callback(CommentActivity.this).postJsonRequest(ApiIds.API_USER_LIST, "https://api.github.com/gists/"+id+"/comments",credentials,comment);
                    restClass.showProgDialog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onSuccessResponce(int apiId, Object responce) {
        restClass.showErrorSnackBar(rootView,"your comment successfully posted");
        restClass.hideProgDialog();
        finish();

    }

    @Override
    public void onFailearResponce(int apiId, String error) {
        restClass.hideProgDialog();
        finish();
    }
}
