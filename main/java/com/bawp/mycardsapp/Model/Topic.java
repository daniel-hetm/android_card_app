package com.bawp.mycardsapp.Model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bawp.mycardsapp.Util.Util;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Util.TOPIC_TABLE)
public class Topic {
    @PrimaryKey(autoGenerate = true)
    private int topicId;
    private String topicName;
    private int number_answers_needed;
    private int number_levels;

    public Topic() {
    }

    @Ignore
    public Topic(String topicName, int number_answers_needed, int number_levels) {
        this.topicName = topicName;
        this.number_answers_needed = number_answers_needed;
        this.number_levels = number_levels;
    }


    public int getNumber_answers_needed() {
        return number_answers_needed;
    }

    public void setNumber_answers_needed(int number_answers_needed) {
        this.number_answers_needed = number_answers_needed;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getNumber_levels() {
        return number_levels;
    }

    public void setNumber_levels(int number_levels) {
        this.number_levels = number_levels;
    }
}
