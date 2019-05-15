package com.saumon.revisioncards.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.utils.StorageUtils;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    private static final int RC_STORAGE_WRITE_PERMS = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @OnClick(R.id.activity_main_cards_backup_btn)
    public void onClickCardsBackupButton() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_STORAGE_WRITE_PERMS);
        } else {
            backupCards();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (RC_STORAGE_WRITE_PERMS == requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            backupCards();
        }
    }

    private void backupCards() {
        if (StorageUtils.isExternalStorageWritable()) {
            StorageUtils.setTextInStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), this, "test.txt", "test", "test");
        }
    }
}
