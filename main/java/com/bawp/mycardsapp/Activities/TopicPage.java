package com.bawp.mycardsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bawp.mycardsapp.Adapters.TopicAdapter;
import com.bawp.mycardsapp.Popups.CreateTopicPopupDialog;
import com.bawp.mycardsapp.Model.MasterViewModel;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TopicPage extends AppCompatActivity {
    private FloatingActionButton addTopicFab;
    private RecyclerView topicRecyclerView;
    private MasterViewModel masterViewModel;
    private TopicAdapter topicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_page);

        masterViewModel = new ViewModelProvider(this).get(MasterViewModel.class);
        topicAdapter = new TopicAdapter(this) {
            @Override
            public void updateTopic(Topic topic) {
                masterViewModel.updateTopic(topic);
            }
        };

        masterViewModel.getAllTopics().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> topics) {
                topicAdapter.setTopics(topics);
            }
        });

        topicRecyclerView = findViewById(R.id.topic_recyclerView);
        topicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topicRecyclerView.setAdapter(topicAdapter);

        addTopicFab = findViewById(R.id.topic_fab);
        addTopicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateTopicPopupDialog(TopicPage.this, null) {
                    @Override
                    public void saveTopic(Topic topic) {
                        masterViewModel.insertTopic(topic);
                    }
                };
            }
        });
    }


}