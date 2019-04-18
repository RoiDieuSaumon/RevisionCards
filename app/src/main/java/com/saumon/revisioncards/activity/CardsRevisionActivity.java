package com.saumon.revisioncards.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.saumon.revisioncards.R;

public class CardsRevisionActivity extends BaseActivity {
    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_cards_revision;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView) findViewById(R.id.activity_cards_revision_current_card_text)).setText("Fiche 1");
    }

    @Override
    protected Toolbar getToolbar() {
        return findViewById(R.id.activity_cards_revision_toolbar);
    }
}
