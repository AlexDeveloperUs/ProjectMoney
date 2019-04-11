package com.example.admin.cardpassword.data.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.admin.cardpassword.data.repository.CardRepository;

import java.util.List;

import io.reactivex.Flowable;

public class CardViewModel extends AndroidViewModel {

    private CardRepository mRepository;
    private Flowable<List<Card>> mAllCards;

    public CardViewModel(@NonNull Application application) {

        super(application);
        mRepository = new CardRepository(application);
        mAllCards = mRepository.getAllCards();
    }

    Flowable<List<Card>> getAllCards() {

        return mAllCards;
    }

    public void insert(Card pCard) {

        mRepository.insert(pCard);
    }


}
