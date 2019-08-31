package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Lesson;

import java.util.List;

@Dao
public interface LessonDao {
    @Query("SELECT * FROM Lesson ORDER BY subjectId ASC, position ASC")
    List<Lesson> getLessons();

    @Insert
    long createLesson(Lesson lesson);

    @Update
    void updateLesson(Lesson lesson);

    @Delete
    void deleteLesson(Lesson lesson);
}
