package com.saumon.revisioncards.repositories;

import android.arch.lifecycle.LiveData;

import com.saumon.revisioncards.database.dao.CardDao;
import com.saumon.revisioncards.models.Card;

import java.util.List;

public class CardDataRepository {
    private final CardDao cardDao;

    public CardDataRepository(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public LiveData<List<Card>> getCardsFromPart(long partId) {
        return cardDao.getCardsFromPart(partId);
    }

    public LiveData<List<Card>> getCardsFromIds(List<Long> cardIds) {
        return cardDao.getCardsFromIds(cardIds);
    }

    public void createCard(Card card) {
        cardDao.createCard(card);
    }

    public void updateCard(Card card) {
        cardDao.updateCard(card);
    }

    public void deleteCard(long cardId) {
        cardDao.deleteCard(cardId);
    }
}
