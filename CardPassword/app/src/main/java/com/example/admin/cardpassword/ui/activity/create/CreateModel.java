package com.example.admin.cardpassword.ui.activity.create;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.utils.ThreadExecutors;

public class CreateModel implements CreateContract.Model {

    private CardDao mCardDao;
    private ThreadExecutors mExecutors;

    CreateModel() {

        mExecutors = new ThreadExecutors();
        AppDataBase dataBase = App.getmInstance().getDataBase();
        mCardDao = dataBase.mCardDao();
    }

    @Override
    public void createCard(Card pCard) {

        mExecutors.dbExecutor().execute(() -> mCardDao.insert(pCard));
    }

    @Override
    public void editCard(Card pCard) {

        mExecutors.dbExecutor().execute(() -> mCardDao.update(pCard));
    }
}
