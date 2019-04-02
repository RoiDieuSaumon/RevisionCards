package com.saumon.revisioncards.repositories;

import android.arch.lifecycle.LiveData;

import com.saumon.revisioncards.database.dao.LessonDao;
import com.saumon.revisioncards.models.Lesson;

import java.util.List;

public class LessonDataRepository {
    private final LessonDao lessonDao;

    public LessonDataRepository(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public LiveData<List<Lesson>> getLessonsFromSubject(long subjectId) {
        return lessonDao.getLessonsFromSubject(subjectId);
    }

    public void createLesson(Lesson lesson) {
        lessonDao.createLesson(lesson);
    }

    public void updateLesson(Lesson lesson) {
        lessonDao.updateLesson(lesson);
    }

    public void deleteLesson(long lessonId) {
        lessonDao.deleteLesson(lessonId);
    }
}
