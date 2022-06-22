package com.bawp.mycardsapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.DecksInTopic;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.Model.TopicWithDeckAndCards;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

@Dao
public interface MasterDao {
    @Insert
    void insertTopic(Topic topic);

    @Update
    void updateTopic(Topic topic);

    @Query("DELETE FROM " + Util.TOPIC_TABLE)
    void deleteAllTopics();

    @Query("DELETE FROM " + Util.TOPIC_TABLE + " WHERE topicId = :id")
    void deleteTopic(int id);

    @Query("SELECT * FROM " + Util.TOPIC_TABLE + " ORDER BY topicName ASC")
    LiveData<List<Topic>> getAllTopics();

    @Transaction
    @Query("SELECT * FROM " + Util.TOPIC_TABLE + " ORDER BY topicName ASC")
    LiveData<List<DecksInTopic>> getTopicsWithDecks();

    @Transaction
    @Query("SELECT * FROM " + Util.TOPIC_TABLE + " ORDER BY topicName ASC")
    LiveData<List<TopicWithDeckAndCards>> getTopicsWithDecksAndCards();


    @Insert
    void insertDeck(Deck deck);

    @Query("DELETE FROM " + Util.DECK_TABLE)
    void deleteAllDecks();

    @Query("DELETE FROM " + Util.DECK_TABLE + " WHERE deckId = :id")
    void deleteDeck(int id);

    @Query("DELETE FROM " + Util.DECK_TABLE + " WHERE parentTopic = :topicId")
    void deleteDeckByTopicId(int topicId);

    @Query("SELECT * FROM " + Util.DECK_TABLE + " ORDER BY deckName DESC")
    LiveData<List<Deck>> getAllDecks();

    @Transaction
    @Query("SELECT * FROM " + Util.DECK_TABLE + " ORDER BY deckName DESC")
    LiveData<List<CardsInDeck>> getDecksWithCards();


    @Insert
    void insertCard(Card card);

    @Update
    void updateCard(Card card);

    @Query("DELETE FROM " + Util.CARD_TABLE)
    void deleteAllCards();

    @Query("DELETE FROM " + Util.CARD_TABLE + " WHERE cardId = :id")
    void deleteCard(int id);

    @Query("DELETE FROM " + Util.CARD_TABLE + " WHERE parentDeck = :deckId")
    void deleteCardByDeckId(int deckId);

    @Query("SELECT * FROM " + Util.CARD_TABLE + " ORDER BY question DESC")
    LiveData<List<Card>> getAllCards();
}
