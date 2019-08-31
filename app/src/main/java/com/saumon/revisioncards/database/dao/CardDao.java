package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Card;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM Card ORDER BY partId ASC, position ASC")
    List<Card> getCards();

    @Insert
    long createCard(Card card);

    @Update
    void updateCard(Card card);

    @Delete
    void deleteCard(Card card);
}
