package com.example.admin.cardpassword.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

@Database(entities = {Card.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {

    public abstract CardDao mCardDao();
}
