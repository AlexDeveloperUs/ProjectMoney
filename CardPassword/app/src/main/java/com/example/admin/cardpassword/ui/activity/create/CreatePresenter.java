package com.example.admin.cardpassword.ui.activity.create;

import android.view.View;

import com.example.admin.cardpassword.data.models.Card;

public class CreatePresenter implements CreateContract.Presenter{

    private Card mCard;
    private CreateContract.View mCreateActivity;
    private View mView;

    CreatePresenter( CreateActivity pCreateActivity) {

        mCreateActivity = pCreateActivity;
    }
}
