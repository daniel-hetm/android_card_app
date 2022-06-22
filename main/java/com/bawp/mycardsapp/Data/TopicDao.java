package com.bawp.mycardsapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bawp.mycardsapp.Model.DecksInTopic;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

@Dao
public interface TopicDao {

    @Insert
    void insertTopic(Topic topic);

    @Query("DELETE FROM " + Util.TOPIC_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + Util.TOPIC_TABLE + " WHERE topicId = :id")
    void deleteTopic(int id);

    @Query("SELECT * FROM " + Util.TOPIC_TABLE + " ORDER BY topicName DESC")
    LiveData<List<Topic>> getAllTopics();

    @Transaction
    @Query("SELECT * FROM " + Util.TOPIC_TABLE)
    LiveData<List<DecksInTopic>> getTopicsWithDecks();
}
