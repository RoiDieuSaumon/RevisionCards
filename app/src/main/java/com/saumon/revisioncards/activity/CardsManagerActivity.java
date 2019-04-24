package com.saumon.revisioncards.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.fragment.cardsManager.CardsTreeViewFragment;

public class CardsManagerActivity extends BaseActivity {
    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_cards_manager;
    }

    @Override
    protected String getToolbarTitle() {
        return "Fiches";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_cards_manager_cards_tree_view_fragment, new CardsTreeViewFragment()).commit();
    }

    @Override
    protected Toolbar getToolbar() {
        return findViewById(R.id.activity_cards_manager_toolbar);
    }
}
