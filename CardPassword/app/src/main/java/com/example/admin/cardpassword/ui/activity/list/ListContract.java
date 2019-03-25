package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.data.models.Card;

public interface ListContract {

    interface View {

        void blur();

        void showCardList();
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
    }
}
