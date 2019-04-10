package com.example.admin.cardpassword.data.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

import java.util.List;

public class CardRepository {

    private CardDao mCardDao;
    private LiveData<List<Card>> mAllCards;

    public CardRepository(Application pApplication) {

        AppDataBase dataBase = AppDataBase.getDatabase(pApplication);
        mCardDao = dataBase.mCardDao();
        mAllCards = mCardDao.getAll();
    }

    public LiveData<List<Card>> getAllCards() {

        return mAllCards;
    }

    public void insert(Card pCard) {

        new insertAsyncTask(mCardDao).execute(pCard);
    }

    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao mAsyncTaskDao;

        insertAsyncTask(CardDao pCardDao) {

            mAsyncTaskDao = pCardDao;
        }

        @Override
        protected Void doInBackground(Card... pCards) {

            mAsyncTaskDao.insert(pCards[0]);
            return null;
        }
    }
}
