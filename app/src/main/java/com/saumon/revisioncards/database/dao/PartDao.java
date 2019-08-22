package com.saumon.revisioncards.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.saumon.revisioncards.models.Part;

import java.util.List;

@Dao
public interface PartDao {
    @Query("SELECT * FROM Part ORDER BY lessonId ASC, position ASC")
    List<Part> getParts();

    @Insert
    long createPart(Part part);

    @Update
    void updatePart(Part part);

    @Query("DELETE FROM Part WHERE id = :partId")
    void deletePart(long partId);

    @Query("DELETE FROM Part")
    void deleteAll();
}
