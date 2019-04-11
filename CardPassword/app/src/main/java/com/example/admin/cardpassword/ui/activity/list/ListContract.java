package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.data.models.Card;

import java.util.List;

public interface ListContract {

    interface View {

        void blur();

        void showCardList();

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

        interface RequestListener {

            void onSuccess(List<Card> pCards);

            void onFailed(Exception pE);
        }
    }
}
