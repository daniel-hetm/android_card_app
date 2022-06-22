package com.bawp.mycardsapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

@Dao
public interface DeckDao {

    @Insert
    void insertDeck(Deck deck);

    @Query("DELETE FROM " + Util.DECK_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + Util.DECK_TABLE + " WHERE deckId = :id")
    void deleteDeck(int id);

    @Query("SELECT * FROM " + Util.DECK_TABLE + " ORDER BY deckName DESC")
    LiveData<List<Deck>> getAllDecks();

    @Transaction
    @Query("SELECT * FROM " + Util.DECK_TABLE + " ORDER BY deckName DESC")
    LiveData<List<CardsInDeck>> getDecksWithCards();
}
