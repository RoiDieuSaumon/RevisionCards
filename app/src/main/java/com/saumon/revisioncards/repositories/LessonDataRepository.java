package com.saumon.revisioncards.repositories;

import com.saumon.revisioncards.database.dao.LessonDao;
import com.saumon.revisioncards.models.Lesson;

import java.util.List;

public class LessonDataRepository {
    private final LessonDao lessonDao;

    public LessonDataRepository(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    public List<Lesson> getLessonsFromSubject(long subjectId) {
        return lessonDao.getLessonsFromSubject(subjectId);
    }

    public List<Lesson> getLessons() {
        return lessonDao.getLessons();
    }

    public void createLesson(Lesson lesson) {
        long lessonId = lessonDao.createLesson(lesson);
        lesson.setId(lessonId);
    }

    public void updateLesson(Lesson lesson) {
        lessonDao.updateLesson(lesson);
    }

    public void deleteLesson(long lessonId) {
        lessonDao.deleteLesson(lessonId);
    }
}
