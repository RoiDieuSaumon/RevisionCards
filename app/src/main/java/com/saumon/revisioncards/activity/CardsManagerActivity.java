package com.saumon.revisioncards.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.activity.BaseActivity;
import com.saumon.revisioncards.fragment.CardsTreeViewFragment;

public class CardsManagerActivity extends BaseActivity {
    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_cards_manager;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_cards_manager_placeholder, new CardsTreeViewFragment()).commit();
    }
}
