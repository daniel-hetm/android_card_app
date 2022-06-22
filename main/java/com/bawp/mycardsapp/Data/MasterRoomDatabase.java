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

@Database(entities = {Topic.class, Deck.class, Card.class}, version = 1, exportSchema = false)
public abstract class MasterRoomDatabase extends RoomDatabase {

    public static volatile MasterRoomDatabase INSTANCE;
    public abstract MasterDao masterDao();
    private static final int NUMBER_OF_THREADS = Util.GLOBAL_NUMBER_OF_THREADS;
    public static final ExecutorService masterDatabaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MasterRoomDatabase getMasterDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MasterRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MasterRoomDatabase.class, Util.MASTER_TABLE).build();
                }
            }
        }
        return INSTANCE;
    }


}
