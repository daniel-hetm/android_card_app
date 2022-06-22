package com.bawp.mycardsapp.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bawp.mycardsapp.Util.Util;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = Util.DECK_TABLE, foreignKeys = @ForeignKey(entity = Topic.class,
        parentColumns = "topicId",
        childColumns = "parentTopic",
        onDelete = CASCADE))
public class Deck {
    @PrimaryKey(autoGenerate = true)
    private int deckId;
    private int deckLevel;

    private int parentTopic;
    private String deckName;

    public Deck() {
    }

    @Ignore
    public Deck(int deckLevel, int parentTopic, String deckName) {
        this.deckLevel = deckLevel;
        this.parentTopic = parentTopic;
        this.deckName = deckName;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public int getDeckLevel() {
        return deckLevel;
    }

    public void setDeckLevel(int deckLevel) {
        this.deckLevel = deckLevel;
    }

    public int getParentTopic() {
        return parentTopic;
    }

    public void setParentTopic(int parentTopic) {
        this.parentTopic = parentTopic;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }
}
