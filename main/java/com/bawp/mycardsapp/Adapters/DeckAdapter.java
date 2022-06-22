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
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.Activities.CardPage;
import com.bawp.mycardsapp.Activities.DeckCardList;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.Util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class DeckAdapter extends RecyclerView.Adapter<DeckAdapter.ViewHolder> {
    private List<CardsInDeck> deckList;
    // private final LayoutInflater deckInflater;
    private Context context;
    private Topic topic;
    private ArrayList<Integer> categoryIds;

    public DeckAdapter(Context context) {
        this.context = context;
        // deckInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deck_row, parent, false);
        return new ViewHolder(view, context);
    }

    public void setDecks(List<CardsInDeck> decks) {
        deckList = decks;
        notifyDataSetChanged();
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setCategoryIds(ArrayList<Integer> categoryIds) {this.categoryIds = categoryIds;}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (deckList != null) {
            CardsInDeck deck = deckList.get(position);
            holder.deckName.setText(deck.getDeck().getDeckName());
            if (deck.getCards() != null) {
                holder.numberCards.setText(MessageFormat.format("{0} cards", deck.getCards().size()));
            } else {
                holder.numberCards.setText(MessageFormat.format("{0} cards", 0));
            }
            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CardPage.class);
                    intent.putExtra(Util.DECK_ID_KEY, deck.getDeck().getDeckId());
                    intent.putExtra(Util.TOPIC_ID_KEY, topic.getTopicId());
                    intent.putExtra(Util.CORRECT_ANSWERS_KEY, topic.getNumber_answers_needed());
                    intent.putExtra(Util.MAX_LEVEL_KEY, topic.getNumber_levels());

                    intent.putIntegerArrayListExtra(Util.CATEGORY_ID_KEY, categoryIds);

                    context.startActivity(intent);
                }
            });
            holder.showDeckAsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DeckCardList.class);
                    intent.putExtra(Util.DECK_ID_KEY, deck.getDeck().getDeckId());
                    context.startActivity(intent);

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (deckList != null)
            return deckList.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView deckName;
        public TextView numberCards;
        public LinearLayout row;
        public ImageButton showDeckAsList;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            deckName = itemView.findViewById(R.id.deck_name);
            numberCards = itemView.findViewById(R.id.deck_count);
            row = itemView.findViewById(R.id.deck_row);
            showDeckAsList = itemView.findViewById(R.id.show_card_list);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
