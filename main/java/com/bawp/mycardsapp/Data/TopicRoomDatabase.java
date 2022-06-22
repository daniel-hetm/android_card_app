package com.bawp.mycardsapp.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bawp.mycardsapp.Model.Deck;
import com.bawp.mycardsapp.Model.Topic;
import com.bawp.mycardsapp.Util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Topic.class, Deck.class}, version = 1)
public abstract class TopicRoomDatabase extends RoomDatabase {

    public static volatile TopicRoomDatabase INSTANCE;
    public abstract TopicDao topicDao();

    private static final int NUMBER_OF_THREADS = Util.GLOBAL_NUMBER_OF_THREADS;
    public static final ExecutorService topicDatabaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TopicRoomDatabase getTopicDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TopicRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TopicRoomDatabase.class, Util.TOPIC_TABLE).build();
                }
            }
        }
        return INSTANCE;
    }

}
