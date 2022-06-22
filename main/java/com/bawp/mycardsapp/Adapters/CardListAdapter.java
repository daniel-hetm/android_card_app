package com.bawp.mycardsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.R;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Context context;
    private List<Card> cards;

    public CardListAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.showQuestion.setText(card.getQuestion());
        holder.answerAdapter = new CardShowLineAdapter(context, card.getAnswers(), card.getHints());
        holder.showAnswers.setAdapter(holder.answerAdapter);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView showQuestion;
        private CardShowLineAdapter answerAdapter;
        private RecyclerView showAnswers;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showQuestion = itemView.findViewById(R.id.show_question);
            showAnswers = itemView.findViewById(R.id.show_answers_recyclerview);
            showAnswers.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
