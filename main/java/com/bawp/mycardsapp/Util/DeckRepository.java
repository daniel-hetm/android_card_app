package com.bawp.mycardsapp.Util;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Data.DeckDao;
import com.bawp.mycardsapp.Data.DeckRoomDatabase;
import com.bawp.mycardsapp.Model.Deck;

import java.util.List;

public class DeckRepository {
    private DeckDao deckDao;
    private LiveData<List<Deck>> allDecks;

    public DeckRepository(Application application) {
        DeckRoomDatabase db = DeckRoomDatabase.getDeckDatabase(application);
        deckDao = db.deckDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() { return allDecks; }

    public void insertDeck(Deck deck) {
        DeckRoomDatabase.deckDatabaseWriteExecutor.execute(() -> {
            deckDao.insertDeck(deck);
        });
    }

    public void deleteAll() {
        DeckRoomDatabase.deckDatabaseWriteExecutor.execute(() -> {
            deckDao.deleteAll();
        });
    }

    public void deleteDeck(Deck deck) {
        DeckRoomDatabase.deckDatabaseWriteExecutor.execute(() -> {
            deckDao.deleteDeck(deck.getDeckId());
        });
    }
}
