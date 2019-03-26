package com.example.admin.cardpassword.data;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseClient {

    private Context mContext;
    private static DatabaseClient mInstance;
    private AppDataBase mAppDataBase;

    private DatabaseClient(Context pContext) {

        mContext = pContext;

        mAppDataBase = Room.databaseBuilder(mContext, AppDataBase.class, "cards").build();
    }

    public static synchronized DatabaseClient getmInstance(Context pContext) {

        if (mInstance == null) {

            mInstance = new DatabaseClient(pContext);
        }

        return mInstance;
    }

    public AppDataBase getAppDataBase() {

        return mAppDataBase;
    }
}
