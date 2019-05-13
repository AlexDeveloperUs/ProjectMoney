package com.example.admin.cardpassword.ui.activity.list;

import com.example.admin.cardpassword.data.models.Card;

import java.util.ArrayList;

public interface ListContract {

    interface View {

        void initData(ArrayList<Card> pCards);

        void deleteAll();
    }

    interface Model {

        void getCards();

        void deleteCard(Card pCard);

        void deleteAll();
    }

    interface Presenter {

        interface RequestListener {

            void onSuccess(ArrayList<Card> pCards);

            void onFailed(Exception pE);
        }

        void loadCards();

        void deleteCard(Card pCard);

        void deleteAll();
    }
}
