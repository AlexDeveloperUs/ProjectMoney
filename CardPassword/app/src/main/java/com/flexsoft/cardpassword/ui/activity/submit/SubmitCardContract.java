package com.flexsoft.cardpassword.ui.activity.submit;

import com.flexsoft.cardpassword.data.models.Card;

public interface SubmitCardContract {

    interface View {

        void showToastNumber();

        void showToastCVC();

        void showToastPin();

        void showToastCardExistence();
    }

    interface Model {

        void createCard(Card pCard);

        void editCard(Card pCard);
    }

    interface Presenter {

        void createCard(Card pCard);

        void updateCard(Card pCard);

        boolean checkCardExistence(long pNumber);

        String checkCardType(String pS);

        boolean checkByLuhnAlgorithm(String pS);

        boolean checkCVC(int pI);

        boolean checkPin(int pI);

        String appendVoid(String pS);
    }
}
