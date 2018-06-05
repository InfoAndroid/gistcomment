package com.infoandroid.gistcomment.OkhttpRest;

public interface ResponceListeners {
    void onSuccessResponce(int apiId, Object responce);

    void onFailearResponce(int apiId, String error);
}
