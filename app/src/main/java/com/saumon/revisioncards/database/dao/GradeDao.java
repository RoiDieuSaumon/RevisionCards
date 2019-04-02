package com.saumon.revisioncards.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Grade;

import java.util.List;

@Dao
public interface GradeDao {
    @Query("SELECT * FROM Grade WHERE cardId = :cardId ORDER BY position ASC")
    LiveData<List<Grade>> getGradesFromCard(long cardId);

    @Insert
    long createGrade(Grade grade);

    @Update
    int updateGrade(Grade grade);

    @Query("DELETE FROM Grade WHERE id = :gradeId")
    int deleteGrade(long gradeId);
}
