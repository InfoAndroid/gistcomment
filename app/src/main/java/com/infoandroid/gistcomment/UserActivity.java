package com.infoandroid.gistcomment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.infoandroid.gistcomment.OkhttpRest.ApiIds;
import com.infoandroid.gistcomment.OkhttpRest.ResponceListeners;
import com.infoandroid.gistcomment.OkhttpRest.RestClass;

import java.io.IOException;


public class UserActivity extends Activity implements ResponceListeners{

    SharedPreferences sharedPreferences;
    public static final String TAG = UserActivity.class.getSimpleName();
    public static final String PREFERENCE = "github_prefs";
    RestClass restClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        sharedPreferences = getSharedPreferences(PREFERENCE, 0);
        String oauthToken = sharedPreferences.getString("oauth_token", null);
        Log.d(TAG, "oauth token for github loged in user is :" + oauthToken);
        String bodyData="{\n" +
                "  \"body\": \"Just commenting for the sake of commenting from Android App hahaha\"\n" +
                "}";
        restClass = new RestClass(UserActivity.this);
        try {
            restClass.callback(UserActivity.this).postJsonRequest(ApiIds.API_USER_LIST, "https://api.github.com/gists/cc05a5802850c1e109d932adb59b01de/comments",oauthToken,bodyData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccessResponce(int apiId, Object responce) {

    }

    @Override
    public void onFailearResponce(int apiId, String error) {

    }
}
