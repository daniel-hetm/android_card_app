package com.bawp.mycardsapp.Util;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Data.MasterDao;
import com.bawp.mycardsapp.Data.MasterRoomDatabase;
import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.DecksInTopic;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.Model.TopicWithDeckAndCards;

import java.util.List;

public class MasterRepository {
    private MasterDao masterDao;

    private LiveData<List<Topic>> allTopics;
    private LiveData<List<DecksInTopic>> topicsWithDecks;
    private LiveData<List<TopicWithDeckAndCards>> topicsWithDecksAndCards;

    private LiveData<List<Deck>> allDecks;
    private LiveData<List<CardsInDeck>> decksWithCards;

    private LiveData<List<Card>> allCards;

    public MasterRepository(Application application) {
        MasterRoomDatabase db = MasterRoomDatabase.getMasterDatabase(application);
        masterDao = db.masterDao();
        allTopics = masterDao.getAllTopics();
        topicsWithDecks = masterDao.getTopicsWithDecks();
        topicsWithDecksAndCards = masterDao.getTopicsWithDecksAndCards();
        allDecks = masterDao.getAllDecks();
        decksWithCards = masterDao.getDecksWithCards();
        allCards = masterDao.getAllCards();
    }

    public LiveData<List<Topic>> getAllTopics() {
        return allTopics;
    }

    public void insertTopic(Topic topic) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.insertTopic(topic);
        });
    }

    public void updateTopic(Topic topic) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.updateTopic(topic);
        });
    }

    public void deleteAllTopics() {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteAllTopics();
        });
    }

    public void deleteTopic(Topic topic) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteTopic(topic.getTopicId());
        });
    }

    public LiveData<List<DecksInTopic>> getTopicsWithDecks() {
        return topicsWithDecks;
    }

    public LiveData<List<TopicWithDeckAndCards>> getTopicsWithDecksAndCards() {
        return topicsWithDecksAndCards;
    }


    public LiveData<List<Deck>> getAllDecks() {
        return allDecks;
    }

    public LiveData<List<CardsInDeck>> getDecksWithCards() {
        return decksWithCards;
    }

    public void insertDeck(Deck deck) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.insertDeck(deck);
        });
    }

    public void deleteDeckByTopicId(int topicId) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteDeckByTopicId(topicId);
        });
    }

    public void deleteAllDecks() {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteAllDecks();
        });
    }

    public void deleteDeck(Deck deck) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteDeck(deck.getDeckId());
        });
    }


    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public void insertCard(final Card card) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.insertCard(card);
        });
    }

    public void updateCard(final Card card) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.updateCard(card);
        });
    }

    public void deleteCard(final Card card) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteCard(card.getCardId());
        });
    }

    public void deleteCardByDeckId(int deckId) {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteCardByDeckId(deckId);
        });
    }

    public void deleteAllCards() {
        MasterRoomDatabase.masterDatabaseWriteExecutor.execute(() -> {
            masterDao.deleteAllCards();
        });
    }


}
