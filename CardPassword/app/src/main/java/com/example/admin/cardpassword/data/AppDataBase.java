package com.example.admin.cardpassword.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

@Database(entities = {Card.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CardDao mCardDao();

    private static AppDataBase INSTANCE;

    public static AppDataBase getDatabase(final Context pContext) {

        if (INSTANCE == null) {

            synchronized (AppDataBase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(pContext.getApplicationContext(),
                            AppDataBase.class, "cards")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
