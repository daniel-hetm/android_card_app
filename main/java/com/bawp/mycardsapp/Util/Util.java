package com.bawp.mycardsapp.Util;

import com.bawp.mycardsapp.Model.CardsInDeck;

import java.text.DateFormat;
import java.util.List;

public class Util {
    public static final String TOPIC_TABLE = "topic_table";
    public static final String DECK_TABLE = "deck_table";
    public static final String CARD_TABLE = "card_table";
    public static final String MASTER_TABLE = "master_table";
    public static final String CATEGORY_TABLE = "category_table";

    public static final String TOPIC_ID_KEY = "topic_id";
    public static final String DECK_ID_KEY = "deck_id";
    public static final String MAX_LEVEL_KEY = "max_level";
    public static final String CATEGORY_ID_KEY = "category_ids";
    public static final String CORRECT_ANSWERS_KEY = "correct_needed";


    public static final String ANSWER_HINT = "answer";
    public static final String HINT_HINT = "hint";

    public static final int GLOBAL_NUMBER_OF_THREADS = 4;

    public static String getTime() {
        return DateFormat.getDateInstance().format(java.lang.System.currentTimeMillis());
    }
}
