package com.bawp.mycardsapp.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DecksInTopic {
    @Embedded
    private Topic topic;
    @Relation(
            parentColumn = "topicId",
            entityColumn = "parentTopic",
            entity = Deck.class
    )
    private List<Deck> decks;


    public DecksInTopic() {}

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }
}
