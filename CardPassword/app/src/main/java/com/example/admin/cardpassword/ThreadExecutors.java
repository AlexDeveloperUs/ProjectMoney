package com.example.admin.cardpassword;

import com.example.admin.cardpassword.data.dao.DBThreadCallBack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutors {

    private ExecutorService mDbThreadExecutor = Executors.newSingleThreadExecutor();

    public ExecutorService getDbThreadExecutor() {

        return mDbThreadExecutor;
    }

    public void dbExecuteTask(Runnable pRunnable, DBThreadCallBack pCallBack) {

        mDbThreadExecutor.execute(() -> {

            try {

                pRunnable.run();
                pCallBack.onFinished();
            } catch (Exception ignored) {

                pCallBack.onFailed(ignored);
            }
        });
    }
}
