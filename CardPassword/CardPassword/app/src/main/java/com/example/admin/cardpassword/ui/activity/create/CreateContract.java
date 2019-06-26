package com.flexsoft.cardpassword.ui.activity.create;

import com.flexsoft.cardpassword.data.models.Card;

public interface CreateContract {

    interface View {

        void showError();

        boolean shouldShowError();

        void hideError();
    }

    interface Presenter {

        void cardNumberValidation(String pNumber);
    }

    interface Model {

        void addCard(Card pCard);

        void editCardData(Card pCard);
    }
}
