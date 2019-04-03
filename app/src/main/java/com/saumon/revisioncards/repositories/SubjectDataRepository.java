package com.saumon.revisioncards.repositories;

import android.arch.lifecycle.LiveData;

import com.saumon.revisioncards.database.dao.SubjectDao;
import com.saumon.revisioncards.models.Subject;

import java.util.List;

public class SubjectDataRepository {
    private final SubjectDao subjectDao;

    public SubjectDataRepository(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public LiveData<List<Subject>> getSubjects() {
        return subjectDao.getSubjects();
    }

    public void createSubject(Subject subject) {
        subjectDao.createSubject(subject);
    }

    public void updateSubject(Subject subject) {
        subjectDao.updateSubject(subject);
    }

    public void deleteSubject(long subjectId) {
        subjectDao.deleteSubject(subjectId);
    }
}