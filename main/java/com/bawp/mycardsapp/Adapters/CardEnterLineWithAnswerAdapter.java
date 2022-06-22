package com.bawp.mycardsapp.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.R;

import java.util.ArrayList;
import java.util.List;

public class CardEnterLineWithAnswerAdapter extends RecyclerView.Adapter<CardEnterLineWithAnswerAdapter.ViewHolder> {

    private Context context;
    private List<String> answers;
    private List<String> hints;
    private String[] typedAnswers;
    private boolean answersVisible = false;

    public CardEnterLineWithAnswerAdapter(Context context, List<String> answers, List<String> hints) {
        this.context = context;
        this.answers = answers;
        this.hints = hints;
        typedAnswers = new String[answers.size()];
        for (int i = 0; i < answers.size(); i++)
            typedAnswers[i] = "";
    }

    public String[] getTypedAnswers() {
        return typedAnswers;
    }

    public void changeAnswerVisibility() {
        answersVisible = !answersVisible;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enter_answer_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.textWatcher != null) {
            holder.enterLine.removeTextChangedListener(holder.textWatcher);
            holder.textWatcher = null;
        }
        holder.enterLine.setText((typedAnswers[position] == null) ? "" : typedAnswers[position]);
        holder.enterLine.setHint(hints.get(position));
        holder.textWatcher = new CardTextWatcher(holder, position);
        holder.enterLine.addTextChangedListener(holder.textWatcher);
        if (answersVisible) {
            holder.showAnswer.setText(answers.get(position));
            holder.showAnswer.setTextSize(holder.textSize);
            if (typedAnswers[position].equals(answers.get(position))) {
                holder.enterLine.setTextColor(ContextCompat.getColor(context, R.color.correct));
            } else {
                holder.enterLine.setTextColor(ContextCompat.getColor(context, R.color.wrong));
            }
        } else {
            // holder.enterLine.setTextColor(holder.colors);
            holder.enterLine.setTextColor(ContextCompat.getColor(context, R.color.correct));
            holder.showAnswer.setText("");
            holder.showAnswer.setTextSize(0);
        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText enterLine;
        public TextView showAnswer;
        public CardTextWatcher textWatcher;
        public float textSize;
        public ColorStateList colors;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            enterLine = itemView.findViewById(R.id.enter_answer);
            showAnswer = itemView.findViewById(R.id.show_right_answer);
            this.textSize = showAnswer.getTextSize() / 2.5f;
            colors = enterLine.getTextColors();
        }
    }


    public class CardTextWatcher implements TextWatcher {
        private int position;
        private ViewHolder holder;

        public CardTextWatcher(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            typedAnswers[position] = holder.enterLine.getText().toString().trim();
        }
    }
}
