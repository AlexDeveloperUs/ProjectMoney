package com.example.admin.cardpassword.ui.activity.create;

import com.example.admin.cardpassword.data.models.Card;

public interface CreateContract {

    interface View {

        void showExistenceError();

        void showNumberError();

        void showCvcError();

        void showPinError();

        void removeNumberError();

        void removeCvcError();

        void removePinError();

        void createAndUpdateCard();
    }

    interface Presenter {

        void createCard(Card pCard);

        void updateCard(Card pCard);

        String checkCardType(String pS);
    }

    interface Model {

        void createCard(Card pCard);

        void editCard(Card pCard);
    }
}
