package com.bawp.mycardsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bawp.mycardsapp.Adapters.CategoryAdapter;
import com.bawp.mycardsapp.Adapters.DeckAdapter;
import com.bawp.mycardsapp.Popups.CreateCardPopupDialog;
import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Model.CategoryViewModel;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.MasterViewModel;
import com.bawp.mycardsapp.Model.TopicWithDeckAndCards;
import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.Get;
import com.bawp.mycardsapp.Util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DeckPage extends AppCompatActivity {

    private TextView topicTitle;
    private RecyclerView deckRecyclerView;
    private FloatingActionButton addCardFab;
    private MasterViewModel masterViewModel;
    private CategoryViewModel categoryViewModel;
    private DeckAdapter deckAdapter;
    private TopicWithDeckAndCards topicWithDeckAndCards;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    private List<CardsInDeck> decks;
    private List<CardsInDeck> displayDecks;
    private List<Deck> onlyDecks;
    private List<Category> categoryList;
    private Card cardToSafe = null;
    private String cardToCategory = null;

    private int number_decks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_page);

        topicTitle = findViewById(R.id.show_topic_name);
        deckAdapter = new DeckAdapter(DeckPage.this);
        deckRecyclerView = findViewById(R.id.deck_recyclerview);
        deckRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deckRecyclerView.setAdapter(deckAdapter);
        int topicId = getIntent().getIntExtra(Util.DECK_ID_KEY, -1);
        masterViewModel = new ViewModelProvider(this).get(MasterViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        categoryAdapter = new CategoryAdapter(DeckPage.this) {
            @Override
            public void updateChecked(Category category, boolean isChecked) {
                categoryViewModel.update(category);
            }
        };
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRecyclerView.setAdapter(categoryAdapter);

        masterViewModel.getTopicsWithDecksAndCards().observe(this, new Observer<List<TopicWithDeckAndCards>>() {
            @Override
            public void onChanged(List<TopicWithDeckAndCards> topicWithDeckAndCardsList) {
                topicWithDeckAndCards = Get.topicFromId(topicWithDeckAndCardsList, topicId);
                if (topicWithDeckAndCards != null) {
                    topicTitle.setText(topicWithDeckAndCards.getTopic().getTopicName());
                    decks = topicWithDeckAndCards.getCardsInDecks();
                    onlyDecks = new ArrayList<>();
                    for (CardsInDeck deck : decks)
                        onlyDecks.add(deck.getDeck());
                    if (decks.isEmpty()) {
                        Deck deck = new Deck(++number_decks, topicId, "Level " + number_decks);
                        masterViewModel.insertDeck(deck);
                    } else {
                        displayDecks = new ArrayList<>();
                        for (CardsInDeck deck : decks)
                            if (deck.getCards().isEmpty()) {
                                if (deck.getDeck().getDeckLevel() != 1)
                                    masterViewModel.deleteDeck(deck.getDeck());
                            } else {
                                displayDecks.add(deck);
                            }

                        number_decks = displayDecks.size();
                        deckAdapter.setDecks(displayDecks);
                        deckAdapter.setTopic(topicWithDeckAndCards.getTopic());
                    }
                }
            }
        });

        categoryViewModel.getAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setCategories(categories);
                categoryRecyclerView.setAdapter(categoryAdapter);
                categoryList = categories;

                ArrayList<Integer> categoryIds = new ArrayList<>();
                categoryIds.add(-1);
                for (Category category : categories) {
                    if (category.isChecked())
                        categoryIds.add(category.getCategoryId());
                    if (cardToSafe != null)
                        if (cardToCategory.equals(category.getCategoryName())) {
                            cardToSafe.setCategoryId(category.getCategoryId());
                            masterViewModel.insertCard(cardToSafe);
                            cardToSafe = null;
                            cardToCategory = null;
                        }
                }
                deckAdapter.setCategoryIds(categoryIds);
            }
        });

        ImageButton addDeck = findViewById(R.id.add_deck);
        addDeck.setVisibility(View.INVISIBLE);/*
        addDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deck deck = new Deck(++number_decks, topicId, "Level " + number_decks);
                masterViewModel.insertDeck(deck);
            }
        });*/

        addCardFab = findViewById(R.id.deck_fab);
        addCardFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardToSafe == null) {
                    CreateCardPopupDialog popupDialog = new CreateCardPopupDialog(DeckPage.this, categoryList, null, topicId) {
                        @Override
                        public void saveCard(Card card, Category category, int safeMode) {
                            card.setParentDeck(Get.deckIdForLevel(onlyDecks, 1));
                            if (safeMode == 1)
                                masterViewModel.insertCard(card);
                            else if (safeMode == 2){
                                categoryViewModel.update(category);
                                masterViewModel.insertCard(card);
                            } else if (safeMode == 3){
                                categoryViewModel.insert(category);
                                cardToSafe = card;
                                cardToCategory = category.getCategoryName();
                            }
                        }
                    };
                }
            }
        });
    }
}