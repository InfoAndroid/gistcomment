package com.infoandroid.gistcomment.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.infoandroid.gistcomment.auth.GithubOauth;


public class HomeActivity extends AppCompatActivity {
    public static final String TAG = UserActivity.class.getSimpleName();
    public static final String GITHUB_ID = "ba164d07d1a79453f5dc";
    public static final String GITHUB_SECRET = "0cdcd97d709b128b090226d76419bb2191b16a35";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        GithubOauth
                .Builder()
                .withClientId(GITHUB_ID)
                .withClientSecret(GITHUB_SECRET)
                .withContext(HomeActivity.this)
                .packageName("com.infoandroid.gistcomment")
                .nextActivity("com.infoandroid.gistcomment.view.UserActivity")
                .debug(true)
                .execute();

    }
}
