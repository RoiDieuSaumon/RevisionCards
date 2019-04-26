package com.saumon.revisioncards;

import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import com.saumon.revisioncards.models.Card;
import com.saumon.revisioncards.models.Grade;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Part;
import com.saumon.revisioncards.models.Subject;
import com.saumon.revisioncards.repositories.CardDataRepository;
import com.saumon.revisioncards.repositories.GradeDataRepository;
import com.saumon.revisioncards.repositories.LessonDataRepository;
import com.saumon.revisioncards.repositories.PartDataRepository;
import com.saumon.revisioncards.repositories.SubjectDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class CardViewModel extends ViewModel {
    private final SubjectDataRepository subjectDataSource;
    private final LessonDataRepository lessonDataSource;
    private final PartDataRepository partDataSource;
    private final CardDataRepository cardDataSource;
    private final GradeDataRepository gradeDataSource;
    private final Executor executor;

    public CardViewModel(
            SubjectDataRepository subjectDataSource,
            LessonDataRepository lessonDataSource,
            PartDataRepository partDataSource,
            CardDataRepository cardDataSource,
            GradeDataRepository gradeDataSource,
            Executor executor
    ) {
        this.subjectDataSource = subjectDataSource;
        this.lessonDataSource = lessonDataSource;
        this.partDataSource = partDataSource;
        this.cardDataSource = cardDataSource;
        this.gradeDataSource = gradeDataSource;
        this.executor = executor;
    }

    public List<Subject> getSubjects() {
        return subjectDataSource.getSubjects();
    }

    public void createSubject(Subject subject) {
        executor.execute(() -> subjectDataSource.createSubject(subject));
    }

    public void updateSubject(Subject subject) {
        executor.execute(() -> subjectDataSource.updateSubject(subject));
    }

    public void deleteSubject(long subjectId) {
        executor.execute(() -> subjectDataSource.deleteSubject(subjectId));
    }

    public List<Lesson> getLessons() {
        return lessonDataSource.getLessons();
    }

    public void createLesson(Lesson lesson) {
        executor.execute(() -> lessonDataSource.createLesson(lesson));
    }

    public void updateLesson(Lesson lesson) {
        executor.execute(() -> lessonDataSource.updateLesson(lesson));
    }

    public void deleteLesson(long lessonId) {
        executor.execute(() -> lessonDataSource.deleteLesson(lessonId));
    }

    public List<Part> getParts() {
        return partDataSource.getParts();
    }

    public void createPart(Part part) {
        executor.execute(() -> partDataSource.createPart(part));
    }

    public void updatePart(Part part) {
        executor.execute(() -> partDataSource.updatePart(part));
    }

    public void deletePart(long partId) {
        executor.execute(() -> partDataSource.deletePart(partId));
    }

    public List<Card> getCards() {
        return cardDataSource.getCards();
    }

    public void createCard(Card card) {
        executor.execute(() -> cardDataSource.createCard(card));
    }

    public void updateCard(Card card) {
        executor.execute(() -> cardDataSource.updateCard(card));
    }

    public void deleteCard(long cardId) {
        executor.execute(() -> cardDataSource.deleteCard(cardId));
    }

    private List<Grade> getGradesFromCard(long cardId) {
        return gradeDataSource.getGradesFromCard(cardId);
    }

    private void createGrade(Grade grade) {
        executor.execute(() -> gradeDataSource.createGrade(grade));
    }

    private void updateGrade(Grade grade) {
        executor.execute(() -> gradeDataSource.updateGrade(grade));
    }

    private void deleteGrade(long gradeId) {
        executor.execute(() -> gradeDataSource.deleteGrade(gradeId));
    }

    public void addGradeToCard(Card card, int gradeValue) {
        List<Grade> gradeList = getGradesFromCard(card.getId());
        int position;
        if (10 == gradeList.size()) {
            for (int i = 0; i < 10; i++) {
                if (0 == i) {
                    deleteGrade(gradeList.get(0).getId());
                } else {
                    gradeList.get(i).setPosition(i);
                    updateGrade(gradeList.get(i));
                }
            }
            position = 10;
        } else {
            position = gradeList.size() + 1;
        }
        Grade grade = new Grade(gradeValue, position, card.getId());
        createGrade(grade);
        Handler handler = new Handler();
        do {
            handler.postDelayed(() -> {}, 200);
        } while (0 == grade.getId());
    }

    public int getCardScore(long cardId) {
        List<Grade> gradeList = getGradesFromCard(cardId);
        if (0 == gradeList.size()) {
            return -1;
        }
        int score = 0;
        for (int i = 0; i < gradeList.size(); i++) {
            score += gradeList.get(i).getValue();
        }
        score *= 100;
        score /= 2;
        score /= gradeList.size();
        return score;
    }

    public void reverseSideToShow(Card card) {
        card.reverseSideToShow();
        updateCard(card);
    }
}
