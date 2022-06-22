package com.bawp.mycardsapp.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TopicWithDeckAndCards {
    @Embedded
    private Topic topic;
    @Relation(
            parentColumn = "topicId",
            entityColumn = "parentTopic",
            entity = Deck.class
    )
    private List<CardsInDeck> cardsInDecks;

    public TopicWithDeckAndCards() {
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<CardsInDeck> getCardsInDecks() {
        return cardsInDecks;
    }

    public void setCardsInDecks(List<CardsInDeck> cardsInDecks) {
        this.cardsInDecks = cardsInDecks;
    }
}
