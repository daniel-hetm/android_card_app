package com.bawp.mycardsapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bawp.mycardsapp.Model.Card;
import com.bawp.mycardsapp.Util.Util;

import java.util.List;

@Dao
public interface CardDao {

    @Insert
    void insertCard(Card card);

    @Query("DELETE FROM " + Util.CARD_TABLE)
    void deleteAll();

    @Query("DELETE FROM " + Util.CARD_TABLE + " WHERE cardId = :id")
    void deleteCard(int id);

    @Query("SELECT * FROM " + Util.CARD_TABLE + " ORDER BY question DESC")
    LiveData<List<Card>> getAllCards();
}
