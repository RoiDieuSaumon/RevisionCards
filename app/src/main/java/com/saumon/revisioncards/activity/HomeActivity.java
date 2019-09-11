package com.saumon.revisioncards.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.utils.FillDatabaseTask;
import com.saumon.revisioncards.utils.StorageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, FillDatabaseTask.Listeners {
    @BindView(R.id.activity_main_fill_database_btn) Button fillDatabaseButton;
    @BindView(R.id.activity_main_backup_btn) Button backupButton;
    @BindView(R.id.activity_main_restore_btn) Button restoreButton;
    @BindView(R.id.activity_main_message_txt) TextView messageTextView;

    private boolean hasClickBackupButton = false;
    private boolean hasClickRestoreButton = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        if (!res.getBoolean(R.bool.DEBUG)) {
            fillDatabaseButton.setVisibility(View.GONE);
        }
        if (!EasyPermissions.hasPermissions(this, WRITE_EXTERNAL_STORAGE)) {
            StorageUtils.requestBackupPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (hasClickBackupButton) {
            hasClickBackupButton = false;
            StorageUtils.backup(this);
        }
        if (hasClickRestoreButton) {
            hasClickRestoreButton = false;
            StorageUtils.restore(this);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (StorageUtils.isBackupPermissionsPermanentlyDenied(this)) {
            backupButton.setVisibility(View.GONE);
            restoreButton.setVisibility(View.GONE);
        }
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

    @OnClick(R.id.activity_main_backup_btn)
    public void onClickBackupButton() {
        if (!EasyPermissions.hasPermissions(this, WRITE_EXTERNAL_STORAGE)) {
            hasClickBackupButton = true;
            StorageUtils.requestBackupPermissions(this);
            return;
        }
        StorageUtils.backup(this);
    }

    @OnClick(R.id.activity_main_restore_btn)
    public void onClickRestoreButton() {
        if (!EasyPermissions.hasPermissions(this, WRITE_EXTERNAL_STORAGE)) {
            hasClickRestoreButton = true;
            StorageUtils.requestBackupPermissions(this);
            return;
        }
        StorageUtils.restore(this);
    }

    @OnClick(R.id.activity_main_fill_database_btn)
    public void onClickFillDatabaseButton() {
        new FillDatabaseTask(this).execute(this);
    }

    @Override
    public void onPreExecute() {
        fillDatabaseButton.setEnabled(false);
        messageTextView.setText(getString(R.string.Filling_database));
    }

    @Override
    public void doInBackground() {}

    @Override
    public void onPostExecute(Void aVoid) {
        messageTextView.setText("");
        fillDatabaseButton.setEnabled(true);
        Toast.makeText(this, R.string.Fill_database_end, Toast.LENGTH_LONG).show();
    }
}
