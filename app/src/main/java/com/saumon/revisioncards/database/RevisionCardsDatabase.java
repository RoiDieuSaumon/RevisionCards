package com.saumon.revisioncards.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.saumon.revisioncards.database.dao.CardDao;
import com.saumon.revisioncards.database.dao.GradeDao;
import com.saumon.revisioncards.database.dao.LessonDao;
import com.saumon.revisioncards.database.dao.PartDao;
import com.saumon.revisioncards.database.dao.SubjectDao;
import com.saumon.revisioncards.models.Card;
import com.saumon.revisioncards.models.Grade;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Part;
import com.saumon.revisioncards.models.Subject;

@Database(entities = {Subject.class, Lesson.class, Part.class, Card.class, Grade.class}, version = 2, exportSchema = false)
public abstract class RevisionCardsDatabase extends RoomDatabase {
    private static volatile RevisionCardsDatabase INSTANCE;

    public abstract SubjectDao subjectDao();
    public abstract LessonDao lessonDao();
    public abstract PartDao partDao();
    public abstract CardDao cardDao();
    public abstract GradeDao gradeDao();

    public static RevisionCardsDatabase getInstance(Context context) {
        if (null == INSTANCE) {
            synchronized (RevisionCardsDatabase.class) {
                if (null == INSTANCE) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RevisionCardsDatabase.class,
                            "RevisionCardsDatabase.db"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
