package com.example.admin.cardpassword.ui.activity.create;

import com.example.admin.cardpassword.data.models.Card;

public interface CreateContract {

    interface View {

        void showError();

        boolean shouldShowError();

        void hideError();
    }

    interface Presenter {

    }

    interface Model {

        void addCard(Card pCard);

        void editCardData(Card pCard);
    }
}
