package com.bawp.mycardsapp.Popups;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class CreateDeleteTopicPopupDialog {
    public CreateDeleteTopicPopupDialog(Context context, final Topic topic){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.delete_topic_popup, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        EditText confirmTopicName = view.findViewById(R.id.confirmTopicName);
        if (topic != null)
            confirmTopicName.setHint(topic.getTopicName());
        Button confirmDeleteTopic = view.findViewById(R.id.confirmDeleteTopic);
        confirmDeleteTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topic != null)
                    if (confirmTopicName.getText().toString().equals(topic.getTopicName())){

                        Snackbar.make(view, "topic deleted", Snackbar.LENGTH_SHORT).show();

                        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                deleteTopic(topic);
                                dialog.dismiss();
                            }
                        };
                        worker.schedule(runnable, 1200, TimeUnit.MILLISECONDS);
                    } else {
                        Snackbar.make(view, "Type topic name", Snackbar.LENGTH_SHORT).show();
                    }
            }
        });

        Button cancelDeleteTopic = view.findViewById(R.id.cancelDeleteTopic);
        cancelDeleteTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public abstract void deleteTopic(Topic topic);
}
