package com.saumon.revisioncards.repositories;

import com.saumon.revisioncards.database.dao.GradeDao;
import com.saumon.revisioncards.models.Grade;

import java.util.List;

public class GradeDataRepository {
    private final GradeDao gradeDao;

    public GradeDataRepository(GradeDao gradeDao) {
        this.gradeDao = gradeDao;
    }

    public List<Grade> getGradesFromCard(long cardId) {
        return gradeDao.getGradesFromCard(cardId);
    }

    public List<Grade> getGrades() {
        return gradeDao.getGrades();
    }

    public void createGrade(Grade grade) {
        gradeDao.createGrade(grade);
    }

    public void updateGrade(Grade grade) {
        gradeDao.updateGrade(grade);
    }

    public void deleteGrade(long gradeId) {
        gradeDao.deleteGrade(gradeId);
    }
}
