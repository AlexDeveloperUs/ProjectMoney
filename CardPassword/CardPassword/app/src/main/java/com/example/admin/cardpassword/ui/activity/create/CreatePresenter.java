package com.flexsoft.cardpassword.ui.activity.create;

import android.view.View;

import com.flexsoft.cardpassword.data.models.Card;

public class CreatePresenter implements CreateContract.Presenter{

    private Card mCard;
    private CreateContract.View mCreateActivity;
    private View mView;

    CreatePresenter( CreateActivity pCreateActivity) {

        mCreateActivity = pCreateActivity;
    }

    @Override
    public void cardNumberValidation(String pNumber) {

        if (pNumber.toLowerCase().contains("x") || mCreateActivity.shouldShowError()) {

            mCreateActivity.showError();
        } else {

            mCreateActivity.hideError();
        }
    }
}
