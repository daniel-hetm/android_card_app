package com.bawp.mycardsapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Delete
    void delete(Category category);

    @Update
    void update(Category category);

    @Query("DELETE FROM " + Util.CATEGORY_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + Util.CATEGORY_TABLE)
    LiveData<List<Category>> getAll();
}
