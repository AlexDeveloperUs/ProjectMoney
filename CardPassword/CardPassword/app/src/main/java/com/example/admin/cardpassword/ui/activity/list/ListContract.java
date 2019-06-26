package com.flexsoft.cardpassword.ui.activity.list;

import com.flexsoft.cardpassword.data.models.Card;

public interface ListContract {

    interface View {

        void blur();

        void showCardList();

        void showToast();

        void checkIdExistence(int pI);
    }

    interface Model {

        void getCards();

        void deleteCardData(Card pCard);

        void setEditedCardData(Card pCard);
    }

    interface Presenter {

        void deleteData();

        void showPass();

        void editCard();

        void getData();

        void cardExistence(int pI);
    }
}
