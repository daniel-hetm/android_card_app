package com.bawp.mycardsapp.Util;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.bawp.mycardsapp.Data.CardDao;
import com.bawp.mycardsapp.Data.CardRoomDatabase;
import com.bawp.mycardsapp.Model.Card;

import java.util.List;

public class CardRepository {
    private CardDao cardDao;
    private LiveData<List<Card>> allCards;

    public CardRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getCardDatabase(application);
        cardDao = db.cardDao();
        allCards = cardDao.getAllCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return allCards;
    }

    public void insertCard(final Card card) {
        CardRoomDatabase.cardDatabaseWriteExecutor.execute(() -> {
            cardDao.insertCard(card);
        });
    }

    public void deleteAll() {
        CardRoomDatabase.cardDatabaseWriteExecutor.execute(() -> {
            cardDao.deleteAll();
        });
    }

    public void deleteCard(final Card card) {
        CardRoomDatabase.cardDatabaseWriteExecutor.execute(() -> {
            cardDao.deleteCard(card.getCardId());
        });
    }


}
