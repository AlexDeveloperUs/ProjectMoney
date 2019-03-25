package com.example.admin.cardpassword.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

@Database(entities = {Card.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase INSTANCE;

    public abstract CardDao mCardDao();

    public static AppDataBase getDatabase(final Context pContext) {

        if (INSTANCE == null) {

            synchronized (AppDataBase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(pContext.getApplicationContext(),
                            AppDataBase.class, "cards")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
