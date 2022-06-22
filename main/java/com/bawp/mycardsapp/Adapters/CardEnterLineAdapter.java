package com.bawp.mycardsapp.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class CardEnterLineAdapter extends RecyclerView.Adapter<CardEnterLineAdapter.ViewHolder> {
    private List<String> lines;
    private Context context;
    private String hint;

    public CardEnterLineAdapter(Context context) {
        this.lines = new ArrayList<>();
        lines.add("");
        this.context = context;
        this.hint = Util.ANSWER_HINT;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public void addLine() {
        lines.add("");
        notifyDataSetChanged();
    }

    public void deleteLine() {
        lines.remove(lines.size() - 1);
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
        holder.enterLine.setText(lines.get(position));
        holder.textWatcher = new CardTextWatcher(holder, position);
        holder.enterLine.addTextChangedListener(holder.textWatcher);
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public EditText enterLine;
        public CardTextWatcher textWatcher;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            enterLine = itemView.findViewById(R.id.enter_answer);
            enterLine.setHint(hint);
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
            if (position < lines.size()) {
                lines.set(position, holder.enterLine.getText().toString().trim());
                StringBuilder text = new StringBuilder();
                for (String line : lines)
                    text.append(line);
                Toast.makeText(context, ": " + text, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
