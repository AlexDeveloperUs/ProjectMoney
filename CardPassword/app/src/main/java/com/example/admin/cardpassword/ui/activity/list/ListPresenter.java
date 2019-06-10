package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.data.models.Card;

import java.util.ArrayList;

public class ListPresenter implements ListContract.Presenter, ListContract.Presenter.RequestListener {

    private ListContract.View mCreateActivity;
    private ListContract.Model mModel;

    ListPresenter(ListActivity pListActivity) {

        mModel = new ListModel();
        ((ListModel) mModel).addRequestListener(this);
        mCreateActivity = pListActivity;
    }

    @Override
    public void onSuccess(ArrayList<Card> pCards) {

        if (pCards.size() == 0) {

            mCreateActivity.setForEmptyScreen();
            mCreateActivity.initData(pCards);
        } else {

            mCreateActivity.initData(pCards);
            mCreateActivity.setForNonEmptyScreen();
        }
    }

    @Override
    public void onFailed(Exception pE) {

    }

    @Override
    public void loadCards() {

        mModel.getCards();
    }

    @Override
    public void deleteCard(Card pCard) {

        mModel.deleteCard(pCard);
    }
}
