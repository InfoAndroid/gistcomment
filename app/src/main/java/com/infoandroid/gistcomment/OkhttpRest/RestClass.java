package com.infoandroid.gistcomment.OkhttpRest;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.infoandroid.gistcomment.preferences.AppSharedPreference;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClass extends BaseRestClient{
    private OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    ResponceListeners responceListeners;

    public RestClass(Context _context) {
        super(_context);
    }

    public RestClass callback(ResponceListeners responceListeners) {
        this.responceListeners = responceListeners;
        return this;
    }


    public void postJsonRequest(final int apiID, String postUrl, String auth,String postBody) throws IOException {
        showProgDialog("Loding...");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, postBody);
        final String basic = "Basic " + Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        Request request = new Request.Builder()
                .url(postUrl)
                .addHeader("Authorization",basic)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                responceListeners.onFailearResponce(apiID,e.getMessage());
                hideProgDialog();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hideProgDialog();
                if (response.code()==401){
                    responceListeners.onFailearResponce(apiID,response.message());
                }else {
                    String JsonData = response.body().string();
                    Log.d("TAG", JsonData);
                    responceListeners.onSuccessResponce(apiID,JsonData);
                }


            }
        });
    }
    public void asynchronousGet(final int apiID, String getUrl, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responceListeners.onFailearResponce(apiID,e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responceListeners.onSuccessResponce(apiID,response.body().string());
            //    Log.d("TAG", response.body().string());
            }
        });
    }


}
