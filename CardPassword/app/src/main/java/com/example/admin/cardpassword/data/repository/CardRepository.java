package com.example.admin.cardpassword.data.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

import java.util.List;

public class CardRepository {

    private LiveData<List<Card>> mAllCards;
    private CardDao mCardDao;

    CardRepository(Application pApplication) {

        AppDataBase appDataBase = AppDataBase.getDatabase(pApplication);
        mCardDao = appDataBase.mCardDao();
    }

    LiveData<List<Card>> getAll() {

        return mAllCards;
    }

    public void insert(Card pCard) {

        new insertAsyncTask(mCardDao).execute(pCard);
    }

    private static class insertAsyncTask extends AsyncTask<Card, Void, Void> {

        private CardDao mCardDaoAsyncDao;

        insertAsyncTask(CardDao pCardDao) {

            mCardDaoAsyncDao = pCardDao;
        }

        @Override
        protected Void doInBackground(Card... pCards) {

            mCardDaoAsyncDao.insert(pCards[0]);
            return null;
        }
    }
}
