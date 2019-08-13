package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Grade;

import java.util.List;

@Dao
public interface GradeDao {
    @Query("SELECT * FROM Grade ORDER BY cardId ASC, position ASC")
    List<Grade> getGrades();

    @Query("SELECT * FROM Grade WHERE cardId = :cardId ORDER BY position ASC")
    List<Grade> getGradesFromCard(long cardId);

    @Insert
    long createGrade(Grade grade);

    @Update
    void updateGrade(Grade grade);

    @Query("DELETE FROM Grade WHERE id = :gradeId")
    void deleteGrade(long gradeId);
}
