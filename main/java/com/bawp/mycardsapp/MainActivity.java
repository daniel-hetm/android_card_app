package com.bawp.mycardsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bawp.mycardsapp.Activities.TopicPage;
import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Model.CategoryViewModel;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.MasterViewModel;
import com.bawp.mycardsapp.Model.Topic;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);

        Button goto_topics = findViewById(R.id.goTo_button);
        goto_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopicPage.class));
            }
        });

        TextView textView = findViewById(R.id.main_text);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        MasterViewModel masterViewModel = new ViewModelProvider(this).get(MasterViewModel.class);
        masterViewModel.getAllTopics().observe(this, new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> topics) {
                masterViewModel.getAllDecks().observe(MainActivity.this, new Observer<List<Deck>>() {
                    @Override
                    public void onChanged(List<Deck> decks) {
                        masterViewModel.getAllCards().observe(MainActivity.this, new Observer<List<Card>>() {
                            @Override
                            public void onChanged(List<Card> cards) {

                                categoryViewModel.getAll().observe(MainActivity.this, new Observer<List<Category>>() {
                                    @Override
                                    public void onChanged(List<Category> categories) {
                                        String text = "";
                                        for (Topic topic : topics) {
                                            text += "Topic id:" + topic.getTopicId() + " " + topic.getTopicName() + "\n";
                                        }
                                        text += "\n\n";
                                        for (Deck deck : decks) {
                                            text += "Deck id: " + deck.getDeckId() + " " + deck.getDeckName() + "   " + deck.getParentTopic() + "\n";
                                        }
                                        text += "\n\n";
                                        for (Card card : cards) {
                                            text += "Card id: " + card.getCardId() + " " + card.getQuestion() + "   " + card.getParentDeck() + "\n";
                                        }
                                        text += "\n\n";
                                        for (Category category : categories)
                                            text += "Category: " + category.getCategoryName() + "\n";
                                        textView.setText(text);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        Button clearButton = findViewById(R.id.clear_content);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masterViewModel.deleteAllCards();
                masterViewModel.deleteAllDecks();
                masterViewModel.deleteAllTopics();
                categoryViewModel.deleteAllCategories();
            }
        });
    }
}