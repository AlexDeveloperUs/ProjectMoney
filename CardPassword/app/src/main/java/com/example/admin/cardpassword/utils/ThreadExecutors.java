package com.example.admin.cardpassword.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutors {

    private ExecutorService mDbThreadExecutor = Executors.newSingleThreadExecutor();

    public ExecutorService dbExecutor() {

        return mDbThreadExecutor;
    }
}
