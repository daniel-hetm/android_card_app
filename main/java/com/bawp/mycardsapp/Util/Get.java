package com.bawp.mycardsapp.Util;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.TopicWithDeckAndCards;

import java.util.ArrayList;
import java.util.List;

public class Get {
    public static int deckIdForLevel(List<Deck> decks, int level) {
        int id;
        for (Deck deck : decks) {
            if (deck.getDeckLevel() ==  level) {
                id = deck.getDeckId();
                return id;
            }
        }
        return -1;
    }

    public static Category categoryFromId(List<Category> categories, int id) {
        for (Category category : categories)
            if (category.getCategoryId() == id)
                return category;
        return null;
    }

    public static TopicWithDeckAndCards topicFromId(List<TopicWithDeckAndCards> topicWithDeckAndCardsList, int topicId) {
        TopicWithDeckAndCards topicWithDeckAndCards;
        for (TopicWithDeckAndCards topic : topicWithDeckAndCardsList) {
            if (topic.getTopic().getTopicId() == topicId) {
                topicWithDeckAndCards = topic;
                return topicWithDeckAndCards;
            }
        }
        return null;
    }

    public static CardsInDeck deckFromId(List<CardsInDeck> cardsInDecks, int deckId) {
        CardsInDeck cardsInDeck;
        for (CardsInDeck deck : cardsInDecks) {
            if (deck.getDeck().getDeckId() == deckId) {
                cardsInDeck = deck;
                return cardsInDeck;
            }
        }
        return null;
    }

    public static List<Card> filterCardsForCategory(List<Card> cardList, List<Integer> categoryIds) {
        ArrayList<Card> cards = new ArrayList<>();
        for (Card card : cardList)
            for (int cat : categoryIds)
                if (card.getCategoryId() == cat)
                    cards.add(card);
        return cards;
    }
}
