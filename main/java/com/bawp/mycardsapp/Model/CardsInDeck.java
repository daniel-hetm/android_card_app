package com.bawp.mycardsapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.ForeignKey;
import androidx.room.Relation;

import java.util.List;

public class CardsInDeck {
    @Embedded
    private Deck deck;
    @Relation(
            parentColumn = "deckId",
            entityColumn = "parentDeck",
            entity = Card.class
    )
    private List<Card> cards;


    public CardsInDeck() {
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
