package com.bawp.mycardsapp.Util;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Data.TopicDao;
import com.bawp.mycardsapp.Data.TopicRoomDatabase;
import com.bawp.mycardsapp.Model.DecksInTopic;
import com.bawp.mycardsapp.Model.Topic;

import java.util.List;

public class TopicRepository {
    private TopicDao topicDao;
    private LiveData<List<Topic>> allTopics;
    private LiveData<List<DecksInTopic>> topicsWithDecks;

    public TopicRepository(Application application) {
        TopicRoomDatabase db = TopicRoomDatabase.getTopicDatabase(application);
        topicDao = db.topicDao();
        allTopics = topicDao.getAllTopics();
        topicsWithDecks = topicDao.getTopicsWithDecks();
    }

    public LiveData<List<Topic>> getAllTopics() {
        return allTopics;
    }

    public void insertTopic(Topic topic) {
        TopicRoomDatabase.topicDatabaseWriteExecutor.execute(() -> {
            topicDao.insertTopic(topic);
        });
    }

    public void deleteAll() {
        TopicRoomDatabase.topicDatabaseWriteExecutor.execute(() -> {
            topicDao.deleteAll();
        });
    }

    public void deleteTopic(Topic topic) {
        TopicRoomDatabase.topicDatabaseWriteExecutor.execute(() -> {
            topicDao.deleteTopic(topic.getTopicId());
        });
    }

    public LiveData<List<DecksInTopic>> getTopicsWithDecks() {
        return topicsWithDecks;
    }
}
