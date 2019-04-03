package com.example.admin.cardpassword.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {Card.class}, version = 1)
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
                            .addCallback(sRoomDatabase)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabase = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {

            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CardDao mDao;

        PopulateDbAsync(AppDataBase pDataBase) {

            mDao = pDataBase.mCardDao();
        }

        @Override
        protected Void doInBackground(Void... pVoids) {

            mDao.insert(new Card(1234567812345678L, 123, 1213, "Alex", "Qwaswe", "visa", 1234));
            mDao.insert(new Card(8765432187654321L, 321, 3121, "Pasha", "ASwqsqs", "mastercard", 4321));
            mDao.insert(new Card(1111111111111111L, 111, 1111, "Dima", "Qwerty", "visa", 1111));
            mDao.insert(new Card(2222222222222222L, 222, 2222, "Roman", "Asdfg", "mastercard", 2222));

            return null;
        }
    }
}
