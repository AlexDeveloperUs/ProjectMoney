package com.flexsoft.cardpassword.ui.activity.list;

import com.flexsoft.cardpassword.App;
import com.flexsoft.cardpassword.data.AppDataBase;
import com.flexsoft.cardpassword.data.models.Card;
import com.flexsoft.cardpassword.data.dao.CardDao;

public class ListModel implements ListContract.Model {

    private CardDao mCardDao;
    private AppDataBase mDataBase;

    ListModel() {

        mDataBase = App.getmInstance().getDataBase();
        mCardDao = mDataBase.mCardDao();
    }

    @Override
    public void getCards() {

        mCardDao.getAll();
    }

    @Override
    public void deleteCardData(Card pCard) {

        mCardDao.delete(pCard);
    }

    @Override
    public void setEditedCardData(Card pCard) {

        mCardDao.update(pCard);
    }
}
