package com.bawp.mycardsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.R;

import java.util.List;

public class CardShowLineAdapter extends RecyclerView.Adapter<CardShowLineAdapter.ViewHolder> {

    private Context context;
    private List<String> answers;
    private List<String> hints;

    public CardShowLineAdapter(Context context, List<String> answers, List<String> hints) {
        this.context = context;
        this.answers = answers;
        this.hints = hints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_answer_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String hint = hints.get(position);
        String line;
        if (hint.length() > 0)
            line = String.format("%s: %s", hint, answers.get(position));
        else
            line = answers.get(position);
        holder.showAnswer.setText(line);
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView showAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showAnswer = itemView.findViewById(R.id.show_answer);
        }
    }
}
