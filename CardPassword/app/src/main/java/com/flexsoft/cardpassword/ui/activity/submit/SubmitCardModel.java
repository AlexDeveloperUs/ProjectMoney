package com.flexsoft.cardpassword.ui.activity.submit;

import com.flexsoft.cardpassword.App;
import com.flexsoft.cardpassword.data.AppDataBase;
import com.flexsoft.cardpassword.data.dao.CardDao;
import com.flexsoft.cardpassword.data.models.Card;
import com.flexsoft.cardpassword.utils.ThreadExecutors;

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
