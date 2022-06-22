package com.bawp.mycardsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.Activities.DeckPage;
import com.bawp.mycardsapp.Popups.CreateDeleteTopicPopupDialog;
import com.bawp.mycardsapp.Popups.CreateTopicPopupDialog;
import com.bawp.mycardsapp.Model.MasterViewModel;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

public abstract class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<Topic> topicList;
    private Context context;

    public TopicAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topic_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (topicList != null) {
            Topic topic = topicList.get(position);
            holder.topicName.setText(topic.getTopicName());
            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DeckPage.class);
                    intent.putExtra(Util.DECK_ID_KEY, topic.getTopicId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void setTopics(List<Topic> topics) {
        topicList = topics;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (topicList != null)
            return topicList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView topicName;
        public ImageButton delete, update;
        public LinearLayout row;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            topicName = itemView.findViewById(R.id.topic_name);
            delete = itemView.findViewById(R.id.topic_delete);
            update = itemView.findViewById(R.id.topic_update);
            row = itemView.findViewById(R.id.topic_row);
            delete.setOnClickListener(this);
            update.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topic_delete:
                    MasterViewModel masterViewModel = new ViewModelProvider(
                            (FragmentActivity) context)
                            .get(MasterViewModel.class);
                    Topic topicToDelete = topicList.get(getAdapterPosition());
                    new CreateDeleteTopicPopupDialog(context, topicToDelete){
                        @Override
                        public void deleteTopic(Topic topic) {
                            masterViewModel.deleteTopic(topic);
                        }
                    };



                    break;

                case R.id.topic_update:
                    Topic topicToUpdate = topicList.get(getAdapterPosition());
                    new CreateTopicPopupDialog(context, topicToUpdate) {
                        @Override
                        public void saveTopic(Topic topic) {
                            updateTopic(topic);
                        }
                    };
                    break;
            }
        }
    }

    public abstract void updateTopic(Topic topic);
}
