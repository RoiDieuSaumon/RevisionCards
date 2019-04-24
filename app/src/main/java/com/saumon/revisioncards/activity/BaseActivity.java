package com.saumon.revisioncards.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.saumon.revisioncards.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutContentViewID());
        ButterKnife.bind(this);
        this.configureToolbar();
    }

    public abstract int getLayoutContentViewID();

    protected void configureToolbar() {
        Toolbar toolbar = getToolbar();
        toolbar.setTitle(getToolbarTitle());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    protected abstract String getToolbarTitle();
}
