package com.saumon.revisioncards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.utils.DatabaseFiller;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.activity_main_fill_database_btn) Button fillDatabaseButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillDatabaseButton.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_home;
    }

    @Override
    protected void configureToolbar() {
        Toolbar toolbar = getToolbar();
        toolbar.setTitle(getToolbarTitle());
        setSupportActionBar(toolbar);
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.Home);
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

    @OnClick(R.id.activity_main_fill_database_btn)
    public void onClickFillDatabaseButton() {
        DatabaseFiller.fillDatabase(this);
        Toast.makeText(this, "Base de données remplit", Toast.LENGTH_LONG).show();
    }
}
