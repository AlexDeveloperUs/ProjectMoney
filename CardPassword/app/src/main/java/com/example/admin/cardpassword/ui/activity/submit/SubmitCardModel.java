package com.example.admin.cardpassword.ui.activity.submit;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.utils.ThreadExecutors;

public class SubmitCardModel implements SubmitCardContract.Model {

    private CardDao mCardDao;
    private ThreadExecutors mExecutors;

    SubmitCardModel() {

        mExecutors = new ThreadExecutors();
        AppDataBase appDataBase = App.getmInstance().getDataBase();
        mCardDao = appDataBase.mCardDao();
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
