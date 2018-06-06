package com.infoandroid.gistcomment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.infoandroid.gistcomment.auth.GithubOauth;


public class HomeActivity extends AppCompatActivity {
    public static final String TAG = UserActivity.class.getSimpleName();
    public static final String GITHUB_ID = "ba164d07d1a79453f5dc";
    public static final String GITHUB_SECRET = "0cdcd97d709b128b090226d76419bb2191b16a35";
    Button loginButton;
    SharedPreferences sharedPreferences;
    Context context;
    boolean check =false;
    public static final String PREFERENCE = "github_prefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loginButton = (Button) findViewById(R.id.login_btn);
        sharedPreferences = getSharedPreferences(PREFERENCE, 0);
        final String oauthToken = sharedPreferences.getString("oauth_token", null);
        context = getApplicationContext();
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //if (oauthToken.equals("")||oauthToken.equals(null)) {


                GithubOauth
                        .Builder()
                        .withClientId(GITHUB_ID)
                        .withClientSecret(GITHUB_SECRET)
                        .withContext(HomeActivity.this)
                        .packageName("com.infoandroid.gistcomment")
                        .nextActivity("com.infoandroid.gistcomment.UserActivity")
                        .debug(true)
                        .execute();


            /*}

            else{
                    Log.d(TAG, "oauth token for github loged in user is :" + oauthToken);

            }*/
            }
        });
    }
}
