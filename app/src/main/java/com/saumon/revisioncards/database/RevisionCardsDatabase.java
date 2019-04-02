package com.saumon.revisioncards.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.saumon.revisioncards.database.dao.CardDao;
import com.saumon.revisioncards.database.dao.GradeDao;
import com.saumon.revisioncards.database.dao.LessonDao;
import com.saumon.revisioncards.database.dao.PartDao;
import com.saumon.revisioncards.database.dao.SubjectDao;

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
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
