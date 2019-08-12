package com.flexsoft.cardpassword;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;

import com.flexsoft.cardpassword.data.AppDataBase;

public class App extends Application {

    public static App mInstance;

    public static AppDataBase mDataBase;

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;
        mDataBase = Room.databaseBuilder(this, AppDataBase.class, "cardsDB")
                .addMigrations(MIGRATION_2_3)
                .build();
    }

    public static App getmInstance() {

        return mInstance;
    }

    public AppDataBase getDataBase() {

        return mDataBase;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE cards_new (cardId INTEGER, cardName TEXT, cardNumber LONG, cardCVC TEXT, " +
                            "cardValidity TEXT, cardHoldersName TEXT, cardHolderSurname TEXT," +
                            "cardType TEXT, cardPIN TEXT, PRIMARY KEY(cardId))");

            database.execSQL(
                    "INSERT INTO cards_new (cardId, cardName, cardNumber, cardCVC, " +
                            "cardValidity, cardHoldersName, cardHolderSurname, " +
                            "cardType, cardPIN) SELECT cardId, cardName, cardNumber, cardCVC, " +
                            "cardValidity, cardHoldersName, cardHolderSurname, " +
                            "cardType, cardPIN FROM cardsDB");

            database.execSQL("DROP TABLE cardsDB");

            database.execSQL("ALTER TABLE cards_new RENAME TO cardsDB");
        }
    };
}
