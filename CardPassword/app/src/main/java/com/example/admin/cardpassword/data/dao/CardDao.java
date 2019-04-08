package com.example.admin.cardpassword.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.admin.cardpassword.data.models.Card;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CardDao {

    @Query("Select * from cards")
    LiveData<List<Card>> getAll();

    @Query("Select * from cards where cardId = :id")
    Card  getById(long id);

    @Query("Select * from cards LIMIT 1")
    Card[] getAnyCard();

    @Insert(onConflict = REPLACE)
    void insert(Card pCard);

    @Update
    void update(Card pCard);

    @Delete
    void delete(Card pCard);

    @Query("Delete from cards")
    void deleteAll();
}
