package com.saumon.revisioncards.repositories;

import com.saumon.revisioncards.database.dao.PartDao;
import com.saumon.revisioncards.models.Part;

import java.util.List;

public class PartDataRepository {
    private final PartDao partDao;

    public PartDataRepository(PartDao partDao) {
        this.partDao = partDao;
    }

    public List<Part> getPartsFromLesson(long lessonId) {
        return partDao.getPartsFromLesson(lessonId);
    }

    public List<Part> getParts() {
        return partDao.getParts();
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
