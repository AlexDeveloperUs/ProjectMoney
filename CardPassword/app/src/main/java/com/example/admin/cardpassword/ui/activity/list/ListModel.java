package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.data.dao.CardDao;

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
