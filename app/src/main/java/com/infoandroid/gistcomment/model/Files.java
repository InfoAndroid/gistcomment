
package com.infoandroid.gistcomment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Files {

    @SerializedName("Second Android Gist")
    @Expose
    private Object secondAndroidGist;
    @SerializedName("Android Info")
    @Expose
    private Object androidInfo;

    public Object getSecondAndroidGist() {
        return secondAndroidGist;
    }

    public void setSecondAndroidGist(Object secondAndroidGist) {
        this.secondAndroidGist = secondAndroidGist;
    }

    public Object getAndroidInfo() {
        return androidInfo;
    }

    public void setAndroidInfo(Object androidInfo) {
        this.androidInfo = androidInfo;
    }

}
