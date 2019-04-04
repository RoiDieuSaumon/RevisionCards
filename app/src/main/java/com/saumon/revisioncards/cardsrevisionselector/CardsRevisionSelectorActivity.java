package com.saumon.revisioncards.cardsrevisionselector;

import android.content.Intent;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.base.BaseActivity;
import com.saumon.revisioncards.cardsrevision.CardsRevisionActivity;

import butterknife.OnClick;

public class CardsRevisionSelectorActivity extends BaseActivity {
    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_cards_revision_selector;
    }

    @OnClick(R.id.activity_cards_revision_selector_review_btn)
    public void onClickReviewButton() {
        Intent intent = new Intent(this, CardsRevisionActivity.class);
        startActivity(intent);
    }
}
