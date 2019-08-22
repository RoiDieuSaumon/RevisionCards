package com.saumon.revisioncards.repositories;

import com.saumon.revisioncards.database.dao.CardDao;
import com.saumon.revisioncards.models.Card;

import java.util.List;

public class CardDataRepository {
    private final CardDao cardDao;

    public CardDataRepository(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public List<Card> getCards() {
        return cardDao.getCards();
    }

    public void createCard(Card card) {
        long cardId = cardDao.createCard(card);
        card.setId(cardId);
    }

    public void updateCard(Card card) {
        cardDao.updateCard(card);
    }

    public void deleteCard(long cardId) {
        cardDao.deleteCard(cardId);
    }

    public void deleteAll() {
        cardDao.deleteAll();
    }
}
