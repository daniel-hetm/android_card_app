package com.bawp.mycardsapp.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Util.DeckRepository;

import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    private DeckRepository deckRepository;
    private LiveData<List<Deck>> allDecks;

    public DeckViewModel(@NonNull Application application) {
        super(application);
        deckRepository = new DeckRepository(application);
        allDecks = deckRepository.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return allDecks;
    }

    public void insertDeck(Deck deck) {
        deckRepository.insertDeck(deck);
    }

    public void deleteAll() {
        deckRepository.deleteAll();
    }

    public void deleteDeck(Deck deck) {
        deckRepository.deleteDeck(deck);
    }
}
