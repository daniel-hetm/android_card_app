package com.bawp.mycardsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawp.mycardsapp.Adapters.CardEnterLineWithAnswerAdapter;
import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.CardsInDeck;
import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Model.CategoryViewModel;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.MasterViewModel;
import com.bawp.mycardsapp.Popups.CreateCardPopupDialog;
import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.CardFun;
import com.bawp.mycardsapp.Util.Get;
import com.bawp.mycardsapp.Util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class CardPage extends AppCompatActivity implements View.OnClickListener {

    private TextView showQuestion;
    private TextView counter;
    private RecyclerView answersRecyclerView;
    private Button checkButton, nextButton;
    private ImageButton correctButton, wrongButton, editButton;
    private Card card;
    private Card cardToUpdate;
    private CardEnterLineWithAnswerAdapter cardEnterLineWithAnswerAdapter;
    private MasterViewModel masterViewModel;
    private CategoryViewModel categoryViewModel;
    private List<CardsInDeck> cardsInDeckList;
    private List<Card> cardList;
    private int currentCard = 0;
    private int deckLevel;
    private int correctNeeded;
    private int maxLevel;
    private int topicId;
    private List<Integer> categoryIds;
    private List<Category> categories;
    private CardFun cardFun;
    private String cardToCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_page);

        showQuestion = findViewById(R.id.show_question);
        counter = findViewById(R.id.counter);
        answersRecyclerView = findViewById(R.id.show_answers_recyclerview);
        checkButton = findViewById(R.id.check_button);
        checkButton.setOnClickListener(this);
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(this);
        correctButton = findViewById(R.id.correct_button);
        correctButton.setOnClickListener(this);
        wrongButton = findViewById(R.id.wrong_button);
        wrongButton.setOnClickListener(this);
        editButton = findViewById(R.id.editCardButton);
        editButton.setOnClickListener(this);
        answersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int deckId = getIntent().getIntExtra(Util.DECK_ID_KEY, -1);
        topicId = getIntent().getIntExtra(Util.TOPIC_ID_KEY, -1);
        correctNeeded = getIntent().getIntExtra(Util.CORRECT_ANSWERS_KEY, -1);
        maxLevel = getIntent().getIntExtra(Util.MAX_LEVEL_KEY, -1);
        categoryIds = getIntent().getIntegerArrayListExtra(Util.CATEGORY_ID_KEY);


        masterViewModel = new ViewModelProvider(this).get(MasterViewModel.class);
        masterViewModel.getDecksWithCards().observe(this, new Observer<List<CardsInDeck>>() {
            @Override
            public void onChanged(List<CardsInDeck> cardsInDecks) {
                cardsInDeckList = cardsInDecks;
                CardsInDeck cardsInDeck = Get.deckFromId(cardsInDecks, deckId);
                List<Deck> decks = new ArrayList<>();
                for (CardsInDeck deck : cardsInDecks)
                    decks.add(deck.getDeck());

                if (cardsInDeck != null) {
                    deckLevel = cardsInDeck.getDeck().getDeckLevel();
                    cardList = cardsInDeck.getCards();

                    if (categoryIds != null )
                        if (!categoryIds.isEmpty())
                            cardList = Get.filterCardsForCategory(cardList,categoryIds);


                    if (Get.deckIdForLevel(decks, deckLevel + 1) < 0 && deckLevel < maxLevel) {
                        masterViewModel.insertDeck(new Deck(deckLevel + 1, topicId, "Level " + (deckLevel + 1)));
                    }
                    if (Get.deckIdForLevel(decks, deckLevel - 1) < 0 && deckLevel > 1) {
                        masterViewModel.insertDeck(new Deck(deckLevel - 1, topicId, "Level " + (deckLevel - 1)));
                    }
                    Collections.shuffle(cardList);
                    if ((Get.deckIdForLevel(decks, deckLevel + 1) > -1 || deckLevel == maxLevel)
                            && Get.deckIdForLevel(decks, deckLevel - 1) > -1 || deckLevel == 1)
                    displayCard();
                }
                cardFun = new CardFun(correctNeeded, deckLevel, maxLevel, decks) {
                    @Override
                    public void updateCard(Card card, boolean lowerNumberCards) {
                        masterViewModel.updateCard(card);
                        if (lowerNumberCards)
                            currentCard--;
                    }
                };
            }
        });
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categoryList) {
                categories = new ArrayList<>();
                for (Category category :categoryList)
                    if (category.getForTopicId() == topicId)
                        categories.add(category);

                categoryIds = new ArrayList<>();
                categoryIds.add(-1);
                for (Category category : categories) {
                    if (category.isChecked())
                        categoryIds.add(category.getCategoryId());
                    if (cardToUpdate != null)
                        if (cardToCategory.equals(category.getCategoryName())) {
                            cardToUpdate.setCategoryId(category.getCategoryId());
                            masterViewModel.updateCard(cardToUpdate);
                            cardToUpdate = null;
                            cardToCategory = null;
                        }
                }

            }
        });
    }


    private void displayCard() {
        if (currentCard == cardList.size()) {
            card = null;
            showQuestion.setText(R.string.no_questions_left);
            counter.setText("");
            cardEnterLineWithAnswerAdapter = null;
        } else {
            card = cardList.get(currentCard);
            String showQuestionText = card.getQuestion() + " " + card.getAnsweredCorrect() + "/" + correctNeeded;

            //-----------------------------------------
            LinearLayout progressBar = findViewById(R.id.segmentedProgressBar);
            progressBar.removeAllViews();
            int w = (int) (progressBar.getWidth()/(2 * (correctNeeded-1)) * 0.95);
            Log.d("abcd", "displayCard: " + w);
            // LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) progressBar.getLayoutParams();
            // params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            // params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            View[] views = new View[correctNeeded + 1];
            for (int i = 0; i < correctNeeded+1; i++){
                views[i] = new ImageView(this);
                if (i < Math.abs(card.getAnsweredCorrect()))
                    if (card.getAnsweredCorrect() > 0)
                        views[i].setBackgroundColor(getColor(R.color.correct));
                    else
                        views[i].setBackgroundColor(getColor(R.color.wrong));
                else
                    views[i].setBackgroundColor(getColor(R.color.smallButtonPressed));
                views[i].setLayoutParams(params);
                views[i].setPadding(w,2,w,2);
                progressBar.addView(views[i]);
            }
            //-----------------------------------------

            showQuestion.setText(showQuestionText);
            String counterText = "(" + (currentCard + 1) + "/" + cardList.size() + ")";
            counter.setText(counterText);
            cardEnterLineWithAnswerAdapter = new CardEnterLineWithAnswerAdapter(CardPage.this, card.getAnswers(), card.getHints());
        }
        answersRecyclerView.setAdapter(cardEnterLineWithAnswerAdapter);
    }

    private void displayNewCard() {
        currentCard = (currentCard + 1) % (cardList.size() + 1);
        displayCard();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_button:
                if (cardEnterLineWithAnswerAdapter != null)
                    cardEnterLineWithAnswerAdapter.changeAnswerVisibility();
                break;

            case R.id.next_button:
                displayNewCard();
                break;

            case R.id.wrong_button:
                cardFun.processAnswer(card, false);
                displayNewCard();
                break;

            case R.id.correct_button:
                cardFun.processAnswer(card, true);
                displayNewCard();
                break;

            case R.id.editCardButton:
                new CreateCardPopupDialog(CardPage.this, categories, card, topicId) {
                    @Override
                    public void saveCard(Card newCard, Category category, int safeMode) {

                        if (safeMode <= 0) {
                            Category oldCategory = Get.categoryFromId(categories, card.getCategoryId());
                            if (oldCategory != null){
                                if (safeMode == 0){
                                    masterViewModel.deleteCard(card);
                                    oldCategory.subtractNum();
                                    categoryViewModel.update(oldCategory);
                                } else if (!oldCategory.getCategoryName().equals(category.getCategoryName())){
                                    oldCategory.subtractNum();
                                    categoryViewModel.update(oldCategory);
                                }
                            }
                        }
                        else if (abs(safeMode) == 1)
                            masterViewModel.updateCard(newCard);
                        else if (abs(safeMode) == 2){
                            categoryViewModel.update(category);
                            masterViewModel.updateCard(newCard);
                        } else if (abs(safeMode) == 3){
                            categoryViewModel.insert(category);
                            cardToUpdate = newCard;
                            cardToCategory = category.getCategoryName();
                        }
                    }
                };
        }
    }
}