package com.bawp.mycardsapp.Popups;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.bawp.mycardsapp.Activities.TopicPage;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class CreateTopicPopupDialog {


    public CreateTopicPopupDialog(Context context, final Topic topic) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_topic_popup, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText topicName = view.findViewById(R.id.topic_name);
        EditText numberAnswers = view.findViewById(R.id.number_answers);
        EditText numberLevels = view.findViewById(R.id.number_levels);
        if (topic != null) {
            topicName.setText(topic.getTopicName());
            numberAnswers.setText(String.valueOf(topic.getNumber_answers_needed()));
            numberLevels.setText(String.valueOf(topic.getNumber_levels()));
        }

        Button save_topic_button = view.findViewById(R.id.save_topic);
        save_topic_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(topicName.getText().toString().trim()) && !TextUtils.isEmpty(numberAnswers.getText().toString())) {
                    String name = topicName.getText().toString().trim();
                    int number = Integer.parseInt(numberAnswers.getText().toString());
                    int levels = Integer.parseInt(numberLevels.getText().toString());
                    if (topic != null) {
                        topic.setTopicName(name);
                        topic.setNumber_answers_needed(number);
                        topic.setNumber_levels(levels);
                        saveTopic(topic);
                    } else{
                        Topic newTopic = new Topic(name, number, levels);
                        saveTopic(newTopic);
                    }

                    Snackbar.make(view, "topic saved", Snackbar.LENGTH_SHORT).show();

                    ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    };
                    worker.schedule(runnable, 1200, TimeUnit.MILLISECONDS);
                } else {
                    Snackbar.make(view, "fill in all fields", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public abstract void saveTopic(Topic topic);
}
