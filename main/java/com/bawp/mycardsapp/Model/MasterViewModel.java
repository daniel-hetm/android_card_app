package com.bawp.mycardsapp.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Util.MasterRepository;

import java.util.List;

public class MasterViewModel extends AndroidViewModel {
    private final MasterRepository masterRepository;
    private final LiveData<List<Topic>> allTopics;
    private final LiveData<List<DecksInTopic>> topicsWithDecks;
    private final LiveData<List<TopicWithDeckAndCards>> topicsWithDecksAndCards;
    private final LiveData<List<Deck>> allDecks;
    private final LiveData<List<CardsInDeck>> decksWithCards;
    private final LiveData<List<Card>> allCards;

    public MasterViewModel(@NonNull Application application) {
        super(application);
        masterRepository = new MasterRepository(application);
        allTopics = masterRepository.getAllTopics();
        topicsWithDecks = masterRepository.getTopicsWithDecks();
        topicsWithDecksAndCards = masterRepository.getTopicsWithDecksAndCards();
        allDecks = masterRepository.getAllDecks();
        decksWithCards = masterRepository.getDecksWithCards();
        allCards = masterRepository.getAllCards();
    }


    public LiveData<List<Topic>> getAllTopics() {
        return allTopics;
    }
    public void insertTopic(Topic topic) {
        masterRepository.insertTopic(topic);
    }
    public void updateTopic(Topic topic) { masterRepository.updateTopic(topic); }
    public void deleteAllTopics() {
        masterRepository.deleteAllTopics();
    }
    public void deleteTopic(Topic topic) {
        masterRepository.deleteTopic(topic);
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
        masterRepository.insertDeck(deck);
    }
    public void deleteAllDecks() {
        masterRepository.deleteAllDecks();
    }
    public void deleteDeck(Deck deck) {
        masterRepository.deleteDeck(deck);
    }
    public void deleteDeckByTopicId(int topicId) {
        masterRepository.deleteDeckByTopicId(topicId);
    }


    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }
    public void insertCard(Card card) {
        masterRepository.insertCard(card);
    }
    public void updateCard(Card card) {
        masterRepository.updateCard(card);
    }
    public void deleteAllCards() {
        masterRepository.deleteAllCards();
    }
    public void deleteCard(Card card) {
        masterRepository.deleteCard(card);
    }
    public void deleteCardByDeckId(int deckId) {
        masterRepository.deleteCardByDeckId(deckId);
    }
}
