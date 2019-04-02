package com.saumon.revisioncards.repositories;

import android.arch.lifecycle.LiveData;

import com.saumon.revisioncards.database.dao.PartDao;
import com.saumon.revisioncards.models.Part;

import java.util.List;

public class PartDataRepository {
    private final PartDao partDao;

    public PartDataRepository(PartDao partDao) {
        this.partDao = partDao;
    }

    public LiveData<List<Part>> getPartsFromLesson(long lessonId) {
        return partDao.getPartsFromLesson(lessonId);
    }

    public void createPart(Part part) {
        partDao.createPart(part);
    }

    public void updatePart(Part part) {
        partDao.updatePart(part);
    }

    public void deletePart(long partId) {
        partDao.deletePart(partId);
    }
}
