package com.example.admin.cardpassword;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor {

    private ExecutorService mDbThreadExecutor = Executors.newSingleThreadExecutor();

    public ExecutorService getDbThreadExecutor() {

        return mDbThreadExecutor;
    }

    public void dbExecuteTask(Runnable pRunnable) {

        mDbThreadExecutor.execute(() -> {

            try {

                pRunnable.run();
            } catch (Exception ignored) {

            }
        });
    }
}
