package com.bawp.mycardsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.bawp.mycardsapp.Adapters.CardListAdapter;
import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.MasterViewModel;
import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.Get;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

public class DeckCardList extends AppCompatActivity {

    private CardsInDeck cardsInDeck;
    private List<Card> cards;

    private TextView showDeckName;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_card_list);

        showDeckName = findViewById(R.id.show_deck_name);
        recyclerView = findViewById(R.id.deck_card_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int deckId = getIntent().getIntExtra(Util.DECK_ID_KEY, -1);
        MasterViewModel masterViewModel = new ViewModelProvider(this).get(MasterViewModel.class);
        masterViewModel.getDecksWithCards().observe(this, new Observer<List<CardsInDeck>>() {
            @Override
            public void onChanged(List<CardsInDeck> cardsInDecks) {
                cardsInDeck = Get.deckFromId(cardsInDecks, deckId);
                showDeckName.setText(cardsInDeck.getDeck().getDeckName());
                cards = cardsInDeck.getCards();
                CardListAdapter cardListAdapter = new CardListAdapter(DeckCardList.this, cards);
                recyclerView.setAdapter(cardListAdapter);
            }
        });
    }
}