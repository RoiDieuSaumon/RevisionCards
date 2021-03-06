package com.saumon.revisioncards.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.saumon.revisioncards.utils.CardViewModel;
import com.saumon.revisioncards.utils.CardsSelection;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CardsRevisionActivity extends BaseActivity {
    @BindView(R.id.activity_cards_revision_ok_btn) Button okBtn;
    @BindView(R.id.activity_cards_revision_middle_btn) Button middleBtn;
    @BindView(R.id.activity_cards_revision_ko_btn) Button koBtn;

    private CardViewModel cardViewModel;
    private List<Card> shuffledCardList;
    private int nextCardIndex = 0;
    private Card card;

    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_cards_revision;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.Revision);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureViewModel();
        showNextCard();
    }

    @Override
    protected Toolbar getToolbar() {
        return findViewById(R.id.activity_cards_revision_toolbar);
    }

    @OnClick(R.id.activity_cards_revision_text2_text)
    public void onClickText2Text(@NonNull TextView textView) {
        textView.setText(card.getTextToHide());
        textView.setBackgroundResource(R.drawable.borders);
        enableButtons();
    }

    @OnClick(R.id.activity_cards_revision_ok_btn)
    public void onClickOkButton() {
        onClickGradeButton(2);
    }

    @OnClick(R.id.activity_cards_revision_middle_btn)
    public void onClickMiddleButton() {
        onClickGradeButton(1);
    }

    @OnClick(R.id.activity_cards_revision_ko_btn)
    public void onClickKoButton() {
        onClickGradeButton(0);
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        cardViewModel = ViewModelProviders.of(this, viewModelFactory).get(CardViewModel.class);
    }

    private void onClickGradeButton(int gradeValue) {
        addGradeToCard(gradeValue);
        cardViewModel.reverseSideToShow(card);
        showNextCard();
    }

    private void showNextCard() {
        if (null == shuffledCardList) {
            shuffledCardList = CardsSelection.getInstance().cardList;
            Collections.shuffle(shuffledCardList);
        }
        if (nextCardIndex == shuffledCardList.size()) {
            Collections.shuffle(shuffledCardList);
            nextCardIndex = 0;
        }
        card = shuffledCardList.get(nextCardIndex);
        if (null == card.getName() || card.getName().isEmpty()) {
            ((TextView) findViewById(R.id.activity_cards_revision_current_card_text)).setText("");
        } else {
            ((TextView) findViewById(R.id.activity_cards_revision_current_card_text)).setText(card.getName());
        }
        ((TextView) findViewById(R.id.activity_cards_revision_text1_text)).setText(card.getTextToShow());
        findViewById(R.id.activity_cards_revision_text2_text).setBackgroundColor(Color.BLACK);
        showScore();
        disableButtons();
        nextCardIndex++;
    }

    private void disableButtons() {
        okBtn.setEnabled(false);
        middleBtn.setEnabled(false);
        koBtn.setEnabled(false);
        okBtn.setBackgroundResource(R.color.gray);
        middleBtn.setBackgroundResource(R.color.gray);
        koBtn.setBackgroundResource(R.color.gray);
        okBtn.setTextColor(getResources().getColor(android.R.color.darker_gray));
        middleBtn.setTextColor(getResources().getColor(android.R.color.darker_gray));
        koBtn.setTextColor(getResources().getColor(android.R.color.darker_gray));
    }

    private void enableButtons() {
        okBtn.setEnabled(true);
        middleBtn.setEnabled(true);
        koBtn.setEnabled(true);
        okBtn.setBackgroundResource(R.color.green);
        middleBtn.setBackgroundResource(R.color.orange);
        koBtn.setBackgroundResource(R.color.red);
        okBtn.setTextColor(getResources().getColor(android.R.color.black));
        middleBtn.setTextColor(getResources().getColor(android.R.color.black));
        koBtn.setTextColor(getResources().getColor(android.R.color.black));
    }

    private void showScore() {
        int score = cardViewModel.getCardScore(card.getId());
        TextView textView = findViewById(R.id.activity_cards_revision_score_text);
        if (-1 == score) {
            textView.setText(getString(R.string.No_score));
            textView.setBackgroundResource(android.R.color.transparent);
            return;
        }
        textView.setText(getString(R.string.Score_display, score));
        int color;
        if (score < 33) {
            color = R.color.red;
        } else if (score < 66) {
            color = R.color.orange;
        } else {
            color = R.color.green;
        }
        textView.setBackgroundResource(color);
    }

    private void addGradeToCard(int gradeValue) {
        cardViewModel.addGradeToCard(card, gradeValue);
    }
}
