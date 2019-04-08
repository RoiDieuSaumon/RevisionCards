package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Card;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM Card WHERE id IN (:cardIdList) ORDER BY position ASC")
    List<Card> getCardsFromIds(List<Long> cardIdList);

    @Query("SELECT * FROM Card WHERE partId = :partId ORDER BY position ASC")
    List<Card> getCardsFromPart(long partId);

    @Query("SELECT * FROM Card ORDER BY position ASC")
    List<Card> getCards();

    @Insert
    long createCard(Card card);

    @Update
    int updateCard(Card card);

    @Query("DELETE FROM Card WHERE id = :cardId")
    int deleteCard(long cardId);
}
