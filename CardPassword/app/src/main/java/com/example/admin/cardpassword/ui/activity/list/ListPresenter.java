package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.data.dao.CardDao;
import com.example.admin.cardpassword.data.models.Card;

import java.util.List;

public class ListPresenter implements ListContract.Presenter, ListContract.Presenter.RequestListener {

    private Card mCard;
    private ListContract.View mCreateActivity;
    private ListContract.Model mModel;
    private CardDao mCardDao;

    ListPresenter(ListActivity pListActivity) {

        mModel = new ListModel();
        ((ListModel) mModel).addRequestListener(this);
        mCreateActivity = pListActivity;
    }

    @Override
    public void deleteData() {

    }

    @Override
    public void showPass() {

    }

    @Override
    public void editCard() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void cardExistence(int pI) {

    }

    @Override
    public void onSuccess(List<Card> pCards) {

    }

    @Override
    public void onFailed(Exception pE) {

    }
}
