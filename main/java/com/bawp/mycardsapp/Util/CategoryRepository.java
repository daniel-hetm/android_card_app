package com.bawp.mycardsapp.Util;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Data.CategoryDao;
import com.bawp.mycardsapp.Data.CategoryRoomDatabase;
import com.bawp.mycardsapp.Model.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> categories;

    public CategoryRepository(Application application) {
        CategoryRoomDatabase db = CategoryRoomDatabase.getCategoryDatabase(application);
        categoryDao = db.categoryDao();
        categories = categoryDao.getAll();
    }

    public LiveData<List<Category>> getAll() {return categories;}
    public void deleteAll() {
        CategoryRoomDatabase.categoryDatabaseWriterExecutor.execute(() -> {
            categoryDao.deleteAll();
        });
    }

    public void insert(Category category) {
        CategoryRoomDatabase.categoryDatabaseWriterExecutor.execute(() -> {
            categoryDao.insert(category);
        });
    }

    public void delete(Category category) {
        CategoryRoomDatabase.categoryDatabaseWriterExecutor.execute(() -> {
            categoryDao.delete(category);
        });
    }

    public void update(Category category) {
        CategoryRoomDatabase.categoryDatabaseWriterExecutor.execute(() -> {
            categoryDao.update(category);
        });
    }
}
