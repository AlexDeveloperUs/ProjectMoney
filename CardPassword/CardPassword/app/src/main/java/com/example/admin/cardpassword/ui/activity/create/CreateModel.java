package com.flexsoft.cardpassword.ui.activity.create;

import com.flexsoft.cardpassword.App;
import com.flexsoft.cardpassword.data.AppDataBase;
import com.flexsoft.cardpassword.data.dao.CardDao;
import com.flexsoft.cardpassword.data.models.Card;

public class CreateModel implements CreateContract.Model {

    private CardDao mCardDao;
    private AppDataBase mDataBase;

    CreateModel() {

        mDataBase = App.getmInstance().getDataBase();
        mCardDao = mDataBase.mCardDao();
    }

    @Override
    public void addCard(Card pCard) {

        mCardDao.insert(new Card(pCard.mCardNumber, pCard.mCVC, pCard.mValidity, pCard.mCardHolderName, pCard.mCardType, pCard.mCardHolderSurname, pCard.mPin));
    }

    @Override
    public void editCardData(Card pCard) {

    }
}
