package com.bawp.mycardsapp.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Util.Util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Category.class}, version = 1, exportSchema = false)
public abstract class CategoryRoomDatabase extends RoomDatabase {
    public static volatile CategoryRoomDatabase INSTANCE;

    public abstract CategoryDao categoryDao();

    private static final int NUMBER_OF_THREADS = Util.GLOBAL_NUMBER_OF_THREADS;
    public static final ExecutorService categoryDatabaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CategoryRoomDatabase getCategoryDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CategoryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), CategoryRoomDatabase.class, Util.CATEGORY_TABLE).build();
                }
            }
        }
        return INSTANCE;
    }

}
