package com.example.admin.cardpassword.data.dao;

public interface DBThreadCallBack {

    void onFinished();

    void onFailed(Exception ex);
}
