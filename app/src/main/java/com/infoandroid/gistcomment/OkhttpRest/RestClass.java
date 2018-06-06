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

   /* public void postJsonRequest(final int apiID, String postUrl, String auth,String postBody) throws IOException {
        showProgDialog("Loding...");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, postBody);
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/cc05a5802850c1e109d932adb59b01de/comments")
                .addHeader("access","Basic "+auth)
                .addHeader("Cache-Control", "no-cache")
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
                String JsonData = response.body().string();
                Log.d("TAG", JsonData);
                responceListeners.onSuccessResponce(apiID,JsonData);
                hideProgDialog();
            }
        });
    }
*/

    public void postJsonRequest(final int apiID, String postUrl, String auth,String postBody) throws IOException {
        showProgDialog("Loding...");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, postBody);
        final String basic = "Basic " + Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
        Request request = new Request.Builder()
                .url("https://api.github.com/gists/cc05a5802850c1e109d932adb59b01de/comments")
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
                String JsonData = response.body().string();
                Log.d("TAG", JsonData);
                responceListeners.onSuccessResponce(apiID,JsonData);
                hideProgDialog();
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

    public void synchronousGet(final int apiID,String getUrl){
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.execute(getUrl);
    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    public void multipartRequest(final int apiID,String getUrl) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("image", "logo-square.png",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        try {
                Response response = client.newCall(request).execute() ;
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }catch (Exception e){
            e.printStackTrace();
        }


    }



}
