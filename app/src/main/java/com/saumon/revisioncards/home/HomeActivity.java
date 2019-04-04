package com.saumon.revisioncards.home;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.base.BaseActivity;
import com.saumon.revisioncards.cardsmanager.CardsManagerActivity;
import com.saumon.revisioncards.cardsrevisionselector.CardsRevisionSelectorActivity;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_home;
    }

    @Override
    protected void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.activity_main_review_btn)
    public void onClickReviewButton() {
        Intent intent = new Intent(this, CardsRevisionSelectorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.activity_main_cards_manage_btn)
    public void onClickCardsManageButton() {
        Intent intent = new Intent(this, CardsManagerActivity.class);
        startActivity(intent);
    }
}
