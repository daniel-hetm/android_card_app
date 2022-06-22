package com.bawp.mycardsapp.Popups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.Adapters.CardEnterLineAdapter;
import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Model.CategoryViewModel;
import com.bawp.mycardsapp.R;
import com.bawp.mycardsapp.Util.Get;
import com.bawp.mycardsapp.Util.Util;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class CreateCardPopupDialog implements View.OnClickListener {
    private Context context;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private AlertDialog dialog;
    private CardEnterLineAdapter cardEnterLineAdapter;
    private CardEnterLineAdapter hintAdapter;
    private RecyclerView cardRecyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private View view;
    private EditText enterQuestion;
    private ImageButton addLine;
    private Button switch_hint_answer, deleteButton;
    private AutoCompleteTextView chooseCategory;
    private ArrayAdapter<String> adapter;
    private Category category;
    private List<Category> categories;
    private List<String> categoryNames;
    private Card newCard;
    private int topicId;


    public CreateCardPopupDialog(Context context, List<Category> categories, Card card, int topicId) {
        this.context = context;
        this.categories = categories;
        this.newCard = card;
        this.topicId = topicId;

        builder = new AlertDialog.Builder(context);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.create_card, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        enterQuestion = view.findViewById(R.id.enter_question);
        cardEnterLineAdapter = new CardEnterLineAdapter(context);
        hintAdapter = new CardEnterLineAdapter(context);
        if (card != null){
            enterQuestion.setText(card.getQuestion());
            cardEnterLineAdapter.setLines(new ArrayList<>(card.getAnswers()));
            hintAdapter.setLines(new ArrayList<>(card.getHints()));
        }
        layoutManager = new LinearLayoutManager(context);
        cardRecyclerview = view.findViewById(R.id.create_card_recyclerview);
        cardRecyclerview.setLayoutManager(layoutManager);
        cardRecyclerview.setAdapter(cardEnterLineAdapter);

        addLine = view.findViewById(R.id.add_line);
        addLine.setOnClickListener(this);
        ImageButton deleteLine = view.findViewById(R.id.delete_line);
        deleteLine.setOnClickListener(this);

        switch_hint_answer = view.findViewById(R.id.switch_hint_answer);
        switch_hint_answer.setOnClickListener(this);

        Button saveCard = view.findViewById(R.id.save_card);
        saveCard.setOnClickListener(this);

        if (card != null){
            deleteButton = view.findViewById(R.id.deleteCardButton);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(this);
        }

        chooseCategory = view.findViewById(R.id.choose_category);
        if (card != null){
            category = Get.categoryFromId(categories, card.getCategoryId());
            if (category != null)
                chooseCategory.setText(category.getCategoryName());
        }

        categoryNames = new ArrayList<>();
        for (Category cat : categories)
            categoryNames.add(cat.getCategoryName());
        adapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_item, categoryNames);
        chooseCategory.setAdapter(adapter);
    }



    public abstract void saveCard(Card card, Category cat, int safeMode);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_hint_answer:
                if (Objects.equals(cardRecyclerview.getAdapter(), cardEnterLineAdapter)) {
                    cardEnterLineAdapter.notifyDataSetChanged();
                    cardRecyclerview.setAdapter(hintAdapter);
                } else {
                    hintAdapter.notifyDataSetChanged();
                    cardRecyclerview.setAdapter(cardEnterLineAdapter);
                }
                break;

            case R.id.add_line:
                cardEnterLineAdapter.addLine();
                hintAdapter.addLine();
                break;

            case R.id.delete_line:
                cardEnterLineAdapter.deleteLine();
                hintAdapter.deleteLine();
                break;

            case R.id.deleteCardButton:
                new CreateConfirmDeleteCardPopup(context) {
                    @Override
                    public void confirm() {
                        saveCard(newCard, category, 0);
                        Snackbar.make(view, "card deleted", Snackbar.LENGTH_SHORT).show();
                        ScheduledExecutorService workerDel = Executors.newSingleThreadScheduledExecutor();
                        Runnable runnableDel = new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        };
                        workerDel.schedule(runnableDel, 1200, TimeUnit.MILLISECONDS);

                    }
                };
                break;

            case R.id.save_card:
                boolean otherCategoryName = false;
                if (newCard != null){
                    if (!category.getCategoryName().equals(chooseCategory.getText().toString().trim()))
                        otherCategoryName = true;
                    newCard.setQuestion(enterQuestion.getText().toString().trim());
                    newCard.setAnswers(cardEnterLineAdapter.getLines());
                    newCard.setHints(hintAdapter.getLines());
                }else
                newCard = new Card(0,
                        enterQuestion.getText().toString().trim(),
                        cardEnterLineAdapter.getLines(),
                        hintAdapter.getLines());
                int safeMode;
                String categoryName = chooseCategory.getText().toString().trim();
                category = null;
                if (categoryName.equals("")) {
                    safeMode = 1;
                }
                else if (categoryNames.contains(categoryName)){
                    for (Category cat : categories)
                        if (cat.getCategoryName().equals(categoryName)) {
                            cat.addNum();
                            newCard.setCategoryId(cat.getCategoryId());
                            category = cat;
                            break;
                        }
                    safeMode = 2;
                } else {
                    category = new Category(categoryName, topicId);
                    safeMode = 3;
                }

                if (otherCategoryName)
                    safeMode *= -1;

                saveCard(newCard, category, safeMode);
                Snackbar.make(view, "card saved", Snackbar.LENGTH_SHORT).show();
                ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                };
                worker.schedule(runnable, 1200, TimeUnit.MILLISECONDS);
                break;


        }

    }

}
