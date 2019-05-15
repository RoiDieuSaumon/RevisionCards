package com.saumon.revisioncards.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.saumon.revisioncards.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class StorageUtils {
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    public static String getTextFromStorage(File rootDestination, Context context, String fileName, String folderName) {
        File file = createOrGetFile(rootDestination, fileName, folderName);
        return readOnFile(context, file);
    }

    public static void setTextInStorage(File rootDestination, Context context, String fileName, String folderName, String text) {
        File file = createOrGetFile(rootDestination, fileName, folderName);
        writeOnFile(context, text, file);
    }

    private static File createOrGetFile(File destination, String fileName, String folderName) {
        File folder = new File(destination, folderName);
        return new File(folder, fileName);
    }

    private static String readOnFile(Context context, File file) {
        String result = null;
        if (file.exists()) {
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();
                    while (null != line) {
                        sb.append(line);
                        sb.append("\n");
                        line = br.readLine();
                    }
                    result = sb.toString();
                }
            } catch (IOException e) {
                Toast.makeText(context, context.getString(R.string.Error_happened), Toast.LENGTH_LONG).show();
            }
        }

        return result;
    }

    private static void writeOnFile(Context context, String text, File file) {
        try {
            // TODO : créer les répertoires seulement s'ils existent pas encore
            if (file.getParentFile().mkdirs()) {
                FileOutputStream fos = new FileOutputStream(file);

                try (Writer w = new BufferedWriter(new OutputStreamWriter(fos))) {
                    w.write(text);
                    w.flush();
                    fos.getFD().sync();
                } finally {
                    Toast.makeText(context, context.getString(R.string.Data_saved), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, context.getString(R.string.Error_happened), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            Toast.makeText(context, context.getString(R.string.Error_happened), Toast.LENGTH_LONG).show();
        }
    }
}
