package com.saumon.revisioncards.repositories;

import com.saumon.revisioncards.database.dao.PartDao;
import com.saumon.revisioncards.models.Part;

import java.util.List;

public class PartDataRepository {
    private final PartDao partDao;

    public PartDataRepository(PartDao partDao) {
        this.partDao = partDao;
    }

    public List<Part> getParts() {
        return partDao.getParts();
    }

    public void createPart(Part part) {
        long partId = partDao.createPart(part);
        part.setId(partId);
    }

    public void updatePart(Part part) {
        partDao.updatePart(part);
    }

    public void deletePart(Part part) {
        partDao.deletePart(part);
    }
}
