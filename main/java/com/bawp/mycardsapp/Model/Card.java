package com.bawp.mycardsapp.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.bawp.mycardsapp.Util.TypeConverterListString;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

@Entity(tableName = Util.CARD_TABLE, foreignKeys = @ForeignKey(entity = Deck.class,
        parentColumns = "deckId",
        childColumns = "parentDeck",
        onDelete = ForeignKey.CASCADE))
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int cardId;

    private int parentDeck;
    private String question;

    @TypeConverters(TypeConverterListString.class)
    private List<String> answers;
    @TypeConverters(TypeConverterListString.class)
    private List<String> hints;
    private int categoryId;
    private int answeredCorrect = 0;

    public Card() {
    }

    @Ignore
    public Card(int parentDeck, String question, List<String> answers, List<String> hints) {
        this.parentDeck = parentDeck;
        this.question = question;
        this.answers = answers;
        this.hints = hints;
        this.categoryId = -1;
    }


    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getParentDeck() {
        return parentDeck;
    }

    public void setParentDeck(int parentDeck) {
        this.parentDeck = parentDeck;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public List<String> getHints() {
        return hints;
    }

    public void setHints(List<String> hints) {
        this.hints = hints;
    }

    public int getAnsweredCorrect() {
        return answeredCorrect;
    }

    public void setAnsweredCorrect(int answeredCorrect) {
        this.answeredCorrect = answeredCorrect;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
