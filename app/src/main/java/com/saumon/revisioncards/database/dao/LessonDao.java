package com.saumon.revisioncards.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Lesson;

import java.util.List;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM Lesson WHERE subjectId = :subjectId ORDER BY position ASC")
    LiveData<List<Lesson>> getLessonsFromSubject(long subjectId);

    @Insert
    long createLesson(Lesson lesson);

    @Update
    int updateLesson(Lesson lesson);

    @Query("DELETE FROM Lesson WHERE id = :lessonId")
    int deleteLesson(long lessonId);
}
