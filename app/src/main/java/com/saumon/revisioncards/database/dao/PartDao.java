package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Part;

import java.util.List;

@Dao
public interface PartDao {
    @Query("SELECT * FROM Part WHERE lessonId = :lessonId ORDER BY position ASC")
    List<Part> getPartsFromLesson(long lessonId);

    @Query("SELECT * FROM Part ORDER BY position ASC")
    List<Part> getParts();

    @Insert
    long createPart(Part part);

    @Update
    int updatePart(Part part);

    @Query("DELETE FROM Part WHERE id = :partId")
    int deletePart(long partId);
}
