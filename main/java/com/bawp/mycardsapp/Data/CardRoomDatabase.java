package com.bawp.mycardsapp.Data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Card.class, Deck.class, Topic.class}, version = 1)
public abstract class CardRoomDatabase extends RoomDatabase {

    public static volatile CardRoomDatabase INSTANCE;
    public abstract CardDao cardDao();

    private static final int NUMBER_OF_THREADS = Util.GLOBAL_NUMBER_OF_THREADS;
    public static final ExecutorService cardDatabaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CardRoomDatabase getCardDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CardRoomDatabase.class, Util.CARD_TABLE).addCallback(cardRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback cardRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            cardDatabaseWriteExecutor.execute(() -> {
                /*
                CardDao dao = INSTANCE.cardDao();
                dao.deleteAll();
                dao.insertCard(new Card(0, "test1", "a1", 1));
                dao.insertCard(new Card(0, "test2", "a2", 2));*/
            });
        }
    };

}
