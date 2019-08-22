package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Query("SELECT * FROM Subject ORDER BY position ASC")
    List<Subject> getSubjects();

    @Insert
    long createSubject(Subject subject);

    @Update
    void updateSubject(Subject subject);

    @Query("DELETE FROM Subject WHERE id = :subjectId")
    void deleteSubject(long subjectId);

    @Query("DELETE FROM Subject")
    void deleteAll();
}
