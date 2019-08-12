package com.saumon.revisioncards.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.saumon.revisioncards.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class StorageUtils {
    private static final int RC_STORAGE_WRITE_PERMS = 100;
    private static final String BACKUP_FILENAME = "backup.json";
    private static final String BACKUP_FOLDERNAME = "revisionCards";

    private static File createOrGetFile(File destination, String fileName, String folderName) {
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }

    public static boolean setTextInStorage(File rootDestination, String fileName, String folderName, String text) {
        File file = createOrGetFile(rootDestination, fileName, folderName);
        return writeOnFile(text, file);
    }

    private static boolean writeOnFile(String text, File file) {
        try {
            file.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(file);

            try (Writer w = new BufferedWriter(new OutputStreamWriter(fos))) {
                w.write(text);
                w.flush();
                fos.getFD().sync();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static void requestBackupPermissions(Activity activity) {
        PermissionRequest request = new PermissionRequest.Builder(activity, RC_STORAGE_WRITE_PERMS, WRITE_EXTERNAL_STORAGE)
                .setRationale(R.string.Write_permissions_request_message)
                .setPositiveButtonText(R.string.Write_permissions_request_positive_btn)
                .setNegativeButtonText(R.string.Write_permissions_request_negative_btn)
                .build();
        EasyPermissions.requestPermissions(request);
    }

    public static boolean isBackupPermissionsPermanentlyDenied(Activity activity) {
        List<String> perms = new ArrayList<>();
        perms.add(WRITE_EXTERNAL_STORAGE);
        return EasyPermissions.somePermissionPermanentlyDenied(activity, perms);
    }

    public static void backup(Context context) {
        if (isExternalStorageWritable()) {
            if (setTextInStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_FILENAME, BACKUP_FOLDERNAME, "{}")) {
                Toast.makeText(context, R.string.Backup_success, Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(context, R.string.Backup_fail, Toast.LENGTH_LONG).show();
    }
}
