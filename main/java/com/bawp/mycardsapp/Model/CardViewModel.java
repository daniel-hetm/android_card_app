package com.bawp.mycardsapp.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Util.CardRepository;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository cardRepository;
    private LiveData<List<Card>> allCards;

    public CardViewModel(@NonNull Application application) {
        super(application);
        cardRepository = new CardRepository(application);
        allCards = cardRepository.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public void insertCard(Card card) {
        cardRepository.insertCard(card);
    }

    public void deleteAll() {
        cardRepository.deleteAll();
    }

    public void deleteCard(Card card) {
        cardRepository.deleteCard(card);
    }
}
