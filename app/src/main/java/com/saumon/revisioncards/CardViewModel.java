package com.saumon.revisioncards;

import android.arch.lifecycle.ViewModel;

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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
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

    public Map getCardsWithParents() {
        List<Subject> subjects = getSubjects();
        List<Lesson> lessons = getLessons();
        List<Part> parts = getParts();
        List<Card> cards = getCards();
        List<Grade> grades = getGrades();
        return new Hashtable();
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

    public List<Lesson> getLessonsFromSubject(long subjectId) {
        return lessonDataSource.getLessonsFromSubject(subjectId);
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

    public List<Part> getPartsFromLesson(long lessonId) {
        return partDataSource.getPartsFromLesson(lessonId);
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

    public List<Card> getCardsFromPart(long partId) {
        return cardDataSource.getCardsFromPart(partId);
    }

    public List<Card> getCardsFromIds(List<Long> cardIds) {
        return cardDataSource.getCardsFromIds(cardIds);
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

    public List<Grade> getGradesFromCard(long cardId) {
        return gradeDataSource.getGradesFromCard(cardId);
    }

    public List<Grade> getGrades() {
        return gradeDataSource.getGrades();
    }

    public void createGrade(Grade grade) {
        executor.execute(() -> gradeDataSource.createGrade(grade));
    }

    public void updateGrade(Grade grade) {
        executor.execute(() -> gradeDataSource.updateGrade(grade));
    }

    public void deleteGrade(long gradeId) {
        executor.execute(() -> gradeDataSource.deleteGrade(gradeId));
    }
}
