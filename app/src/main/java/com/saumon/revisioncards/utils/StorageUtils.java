package com.saumon.revisioncards.utils;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.JsonReader;
import android.widget.Toast;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;
import com.saumon.revisioncards.models.Grade;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Part;
import com.saumon.revisioncards.models.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
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

    private static boolean setTextInStorage(File rootDestination, String fileName, String folderName, String text) {
        File file = createOrGetFile(rootDestination, fileName, folderName);
        return writeOnFile(text, file);
    }

    private static String getTextFromStorage(File rootDestination, String fileName, String folderName) {
        File file = createOrGetFile(rootDestination, fileName, folderName);
        return readOnFile(file);
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

    private static String readOnFile(File file) {
        String result = null;
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                result = sb.toString();
            } catch (IOException ignored) {

            }
        }

        return result;
    }

    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
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

    public static void backup(Activity activity) {
        if (isExternalStorageWritable()) {
            String json = databaseToJson(activity).toString();
            if (setTextInStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_FILENAME, BACKUP_FOLDERNAME, json)) {
                Toast.makeText(activity, R.string.Backup_success, Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(activity, R.string.Backup_fail, Toast.LENGTH_LONG).show();
    }

    private static JSONObject databaseToJson(Activity activity) {
        JSONObject json = new JSONObject();

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(activity);
        CardViewModel cardViewModel = ViewModelProviders.of((FragmentActivity) activity, viewModelFactory).get(CardViewModel.class);

        List<Subject> subjects = cardViewModel.getSubjects();
        List<Lesson> lessons = cardViewModel.getLessons();
        List<Part> parts = cardViewModel.getParts();
        List<Card> cards = cardViewModel.getCards();
        List<Grade> grades = cardViewModel.getGrades();

        JSONArray subjectsJson = new JSONArray();
        for (int is = 0; is < subjects.size(); is++) {
            Subject subject = subjects.get(is);
            JSONObject subjectJson = new JSONObject();
            try {
                subjectJson.put("id", subject.getId());
                subjectJson.put("name", subject.getName());
                subjectJson.put("position", subject.getPosition());
            } catch (JSONException e) {
                Toast.makeText(activity, R.string.Get_subjects_fail, Toast.LENGTH_LONG).show();
                return json;
            }
            subjectsJson.put(subjectJson);
        }
        JSONArray lessonsJson = new JSONArray();
        for (int il = 0; il < lessons.size(); il++) {
            Lesson lesson = lessons.get(il);
            JSONObject lessonJson = new JSONObject();
            try {
                lessonJson.put("id", lesson.getId());
                lessonJson.put("name", lesson.getName());
                lessonJson.put("position", lesson.getPosition());
                lessonJson.put("subjectId", lesson.getSubjectId());
            } catch (JSONException e) {
                Toast.makeText(activity, R.string.Get_lessons_fail, Toast.LENGTH_LONG).show();
                return json;
            }
            lessonsJson.put(lessonJson);
        }
        JSONArray partsJson = new JSONArray();
        for (int ip = 0; ip < parts.size(); ip++) {
            Part part = parts.get(ip);
            JSONObject partJson = new JSONObject();
            try {
                partJson.put("id", part.getId());
                partJson.put("name", part.getName());
                partJson.put("position", part.getPosition());
                partJson.put("lessonId", part.getLessonId());
            } catch (JSONException e) {
                Toast.makeText(activity, R.string.Get_parts_fail, Toast.LENGTH_LONG).show();
                return json;
            }
            partsJson.put(partJson);
        }
        JSONArray cardsJson = new JSONArray();
        for (int ic = 0; ic < cards.size(); ic++) {
            Card card = cards.get(ic);
            JSONObject cardJson = new JSONObject();
            try {
                cardJson.put("id", card.getId());
                cardJson.put("name", card.getName());
                cardJson.put("text1", card.getText1());
                cardJson.put("text2", card.getText2());
                cardJson.put("sideToShow", card.getSideToShow());
                cardJson.put("position", card.getPosition());
                cardJson.put("partId", card.getPartId());
            } catch (JSONException e) {
                Toast.makeText(activity, R.string.Get_cards_fail, Toast.LENGTH_LONG).show();
                return json;
            }
            cardsJson.put(cardJson);
        }
        JSONArray gradesJson = new JSONArray();
        for (int ig = 0; ig < grades.size(); ig++) {
            Grade grade = grades.get(ig);
            JSONObject gradeJson = new JSONObject();
            try {
                gradeJson.put("id", grade.getId());
                gradeJson.put("value", grade.getValue());
                gradeJson.put("position", grade.getPosition());
                gradeJson.put("cardId", grade.getCardId());
            } catch (JSONException e) {
                Toast.makeText(activity, R.string.Get_grades_fail, Toast.LENGTH_LONG).show();
                return json;
            }
            gradesJson.put(gradeJson);
        }

        try {
            json.put("subject", subjectsJson);
            json.put("lesson", lessonsJson);
            json.put("part", partsJson);
            json.put("card", cardsJson);
            json.put("grade", gradesJson);
        } catch (JSONException e) {
            Toast.makeText(activity, R.string.Get_data_fail, Toast.LENGTH_LONG).show();
        }

        return json;
    }

    public static void restore(Activity activity) {
        if (isExternalStorageReadable()) {
            try {
                JSONObject json = new JSONObject(getTextFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), BACKUP_FILENAME, BACKUP_FOLDERNAME));
                if (!jsonToDatabase(activity, json)) {
                    Toast.makeText(activity, R.string.Restore_fail, Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (JSONException e) {
                Toast.makeText(activity, R.string.Restore_fail, Toast.LENGTH_LONG).show();
                return;
            }
        }
        Toast.makeText(activity, R.string.Restore_success, Toast.LENGTH_LONG).show();
    }

    private static boolean jsonToDatabase(Activity activity, JSONObject json) {
        return true;
    }
}
