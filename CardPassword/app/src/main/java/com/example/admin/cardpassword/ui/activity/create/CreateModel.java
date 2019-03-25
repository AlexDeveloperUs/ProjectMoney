package com.example.admin.cardpassword.ui.activity.create;

import com.example.admin.cardpassword.App;
import com.example.admin.cardpassword.data.AppDataBase;
import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;
import com.example.admin.cardpassword.data.repository.CardRepository;

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
