package com.example.admin.cardpassword.utils;

import com.example.admin.cardpassword.data.DBThreadCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutors {

    private ExecutorService mDbThreadExecutor = Executors.newSingleThreadExecutor();

    public ExecutorService dbExecutor() {

        return mDbThreadExecutor;
    }

    public void dbExecuteTask(Runnable pRunnable, DBThreadCallback pCallback) {

        mDbThreadExecutor.execute(() -> {

            try {

                pRunnable.run();
                pCallback.onFinished();
            } catch (Exception pE) {

                pCallback.onFailed(pE);
            }
        });
    }
}
