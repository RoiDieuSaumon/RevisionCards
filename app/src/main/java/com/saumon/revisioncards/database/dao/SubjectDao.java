package com.saumon.revisioncards.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM Subject ORDER BY position ASC")
    LiveData<List<Subject>> getSubjects();

    @Insert
    long createSubject(Subject subject);

    @Update
    int updateSubject(Subject subject);

    @Query("DELETE FROM Subject WHERE id = :subjectId")
    int deleteSubject(long subjectId);
}
