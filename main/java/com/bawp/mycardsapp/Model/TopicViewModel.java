package com.bawp.mycardsapp.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Util.TopicRepository;

import java.util.List;

public class TopicViewModel extends AndroidViewModel {

    private TopicRepository topicRepository;
    private LiveData<List<Topic>> allTopics;
    private LiveData<List<DecksInTopic>> topicsWithDecks;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        topicRepository = new TopicRepository(application);
        allTopics = topicRepository.getAllTopics();
        topicsWithDecks = topicRepository.getTopicsWithDecks();
    }

    public LiveData<List<Topic>> getAllTopics() {
        return allTopics;
    }

    public void insertTopic(Topic topic) {
        topicRepository.insertTopic(topic);
    }

    public void deleteAll() {
        topicRepository.deleteAll();
    }

    public void deleteTopic(Topic topic) {
        topicRepository.deleteTopic(topic);
    }

    public LiveData<List<DecksInTopic>> getTopicsWithDecks() { return topicsWithDecks;}
}
