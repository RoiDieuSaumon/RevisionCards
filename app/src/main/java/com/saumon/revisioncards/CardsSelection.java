package com.saumon.revisioncards;

import com.saumon.revisioncards.models.Card;

import java.util.List;

public class CardsSelection {
    private static final CardsSelection INSTANCE = new CardsSelection();
    public List<Card> cardList;

    private CardsSelection() {}

    public static CardsSelection getInstance() {
        return INSTANCE;
    }
}
