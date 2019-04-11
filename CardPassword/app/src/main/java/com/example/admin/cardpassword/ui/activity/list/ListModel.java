package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.data.dao.CardDao;

public class ListModel implements ListContract.Model {

    private CardDao mCardDao;
    private AppDataBase mDataBase;
    private ListContract.Presenter.RequestListener mListener;

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

    void addRequestListener(ListContract.Presenter.RequestListener pListener) {

        mListener = pListener;
    }
}
