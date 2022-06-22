package com.bawp.mycardsapp.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Util.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        categories = categoryRepository.getAll();
    }

    public LiveData<List<Category>> getAll() {return categories;}
    public void deleteAllCategories() { categoryRepository.deleteAll();}

    public void insert(Category category) {
        categoryRepository.insert(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public void update(Category category) {
        if (category.getNumCards() < 1)
            categoryRepository.delete(category);
        else
            categoryRepository.update(category);
    }
}
