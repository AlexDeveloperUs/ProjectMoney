package com.flexsoft.cardpassword;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.flexsoft.cardpassword.data.AppDataBase;

public class App extends Application {

    public static App mInstance;

    public static AppDataBase mDataBase;

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;
        mDataBase = Room.databaseBuilder(this, AppDataBase.class, "database").build();
    }

    public static App getmInstance() {

        return mInstance;
    }

    public AppDataBase getDataBase() {

        return mDataBase;
    }
}
