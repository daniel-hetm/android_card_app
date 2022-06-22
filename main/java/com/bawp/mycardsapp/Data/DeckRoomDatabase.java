package com.bawp.mycardsapp.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Deck.class, Topic.class, Card.class}, version = 1)
public abstract class DeckRoomDatabase extends RoomDatabase {

    public static volatile DeckRoomDatabase INSTANCE;
    public abstract DeckDao deckDao();

    private static final int NUMBER_OF_THREADS = Util.GLOBAL_NUMBER_OF_THREADS;
    public static final ExecutorService deckDatabaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static DeckRoomDatabase getDeckDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DeckRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeckRoomDatabase.class, Util.DECK_TABLE).build();
                }
            }
        }
        return INSTANCE;
    }
}
