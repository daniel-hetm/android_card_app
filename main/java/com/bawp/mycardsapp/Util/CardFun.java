package com.bawp.mycardsapp.Util;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.Deck;

import java.util.List;

public abstract class CardFun {
    private int correctNeeded;
    private int deckLevel;
    private int maxLevel;
    private List<Deck> decks;

    public CardFun(int correctNeeded, int deckLevel, int maxLevel, List<Deck> decks) {
        this.correctNeeded = correctNeeded;
        this.deckLevel = deckLevel;
        this.maxLevel = maxLevel;
        this.decks = decks;
    }

    public void processAnswer(Card card, boolean isCorrect) {
        if (card == null)
            return;
        int answersCorrect = card.getAnsweredCorrect();
        if (isCorrect) {
            answersCorrect++;
        } else {
            answersCorrect--;
        }

        if (answersCorrect >= correctNeeded) {
            if (deckLevel < maxLevel)
                moveCard(card, deckLevel + 1);
            else
                card.setAnsweredCorrect(correctNeeded);
        } else if (answersCorrect <= -correctNeeded) {
            if (deckLevel > 1)
                moveCard(card, deckLevel - 1);
            else
                card.setAnsweredCorrect(-correctNeeded);
        } else {
            card.setAnsweredCorrect(answersCorrect);
            updateCard(card, false);
        }
    }

    private void moveCard(Card card, int targetLevel) {
        int deckId = Get.deckIdForLevel(decks, targetLevel);
        card.setAnsweredCorrect(0);
        card.setParentDeck(deckId);
        updateCard(card, true);
    }


    public abstract void updateCard(Card card, boolean lowerNumberCards);
}
