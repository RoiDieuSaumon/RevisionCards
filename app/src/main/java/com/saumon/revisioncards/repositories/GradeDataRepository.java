package com.saumon.revisioncards.repositories;

import com.saumon.revisioncards.database.dao.GradeDao;
import com.saumon.revisioncards.models.Grade;

import java.util.List;

public class GradeDataRepository {
    private final GradeDao gradeDao;

    public GradeDataRepository(GradeDao gradeDao) {
        this.gradeDao = gradeDao;
    }

    public List<Grade> getGrades() {
        return gradeDao.getGrades();
    }

    public List<Grade> getGradesFromCard(long cardId) {
        return gradeDao.getGradesFromCard(cardId);
    }

    public void createGrade(Grade grade) {
        long gradeId = gradeDao.createGrade(grade);
        grade.setId(gradeId);
    }

    public void updateGrade(Grade grade) {
        gradeDao.updateGrade(grade);
    }

    public void deleteGrade(Grade grade) {
        gradeDao.deleteGrade(grade);
    }
}
