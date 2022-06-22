package com.bawp.mycardsapp.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bawp.mycardsapp.Util.Util;

@Entity(tableName = Util.CATEGORY_TABLE)
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int categoryId;
    private int forTopicId;
    private String categoryName;
    private int numCards;
    private boolean isChecked;

    public Category(@NonNull String categoryName, int forTopicId) {
        this.categoryName = categoryName;
        this.forTopicId = forTopicId;
        this.numCards = 1;
        this.isChecked = true;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getForTopicId() {
        return forTopicId;
    }

    public void setForTopicId(int forTopicId) {
        this.forTopicId = forTopicId;
    }

    public int getNumCards() {
        return numCards;
    }

    public void setNumCards(int numCards) {
        this.numCards = numCards;
    }

    public void addNum(){
        this.numCards++;
    }

    public void subtractNum(){
        this.numCards--;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
