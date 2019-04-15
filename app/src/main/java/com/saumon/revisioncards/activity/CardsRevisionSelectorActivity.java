package com.saumon.revisioncards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.activity.BaseActivity;
import com.saumon.revisioncards.activity.CardsRevisionActivity;
import com.saumon.revisioncards.fragment.cardsSelector.CardsTreeViewFragment;

import butterknife.OnClick;

public class CardsRevisionSelectorActivity extends BaseActivity {
    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_cards_revision_selector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_cards_revision_selector_cards_tree_view_fragment, new CardsTreeViewFragment()).commit();
    }

    @OnClick(R.id.activity_cards_revision_selector_review_btn)
    public void onClickReviewButton() {
        Intent intent = new Intent(this, CardsRevisionActivity.class);
        startActivity(intent);
    }

    @Override
    protected Toolbar getToolbar() {
        return findViewById(R.id.activity_cards_revision_selector_toolbar);
    }
}
