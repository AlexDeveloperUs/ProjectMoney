package com.example.admin.cardpassword.data.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.admin.cardpassword.data.repository.CardRepository;

import java.util.List;

public class CardViewModel extends AndroidViewModel {

    private CardRepository mRepository;
    private LiveData<List<Card>> mAllCards;

    public CardViewModel(@NonNull Application application) {

        super(application);
        mRepository = new CardRepository(application);
        mAllCards = mRepository.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {

        return mAllCards;
    }

    public void insert(Card pCard) {

        mRepository.insert(pCard);
    }

    public void deleteAll() {

        mRepository.deleteAll();
    }
}
