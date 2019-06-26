package com.flexsoft.cardpassword.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.flexsoft.cardpassword.data.dao.CardDao;
import com.flexsoft.cardpassword.data.models.Card;

@Database(entities = {Card.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CardDao mCardDao();
}
