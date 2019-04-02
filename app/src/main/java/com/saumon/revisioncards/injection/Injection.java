package com.saumon.revisioncards.injection;

import android.content.Context;

import com.saumon.revisioncards.database.RevisionCardsDatabase;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.repositories.CardDataRepository;
import com.saumon.revisioncards.repositories.GradeDataRepository;
import com.saumon.revisioncards.repositories.LessonDataRepository;
import com.saumon.revisioncards.repositories.PartDataRepository;
import com.saumon.revisioncards.repositories.SubjectDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
    public static SubjectDataRepository provideSubjectDataSource(Context context) {
        RevisionCardsDatabase database = RevisionCardsDatabase.getInstance(context);
        return new SubjectDataRepository(database.subjectDao());
    }

    public static LessonDataRepository provideLessonDataSource(Context context) {
        RevisionCardsDatabase database = RevisionCardsDatabase.getInstance(context);
        return new LessonDataRepository(database.lessonDao());
    }

    public static PartDataRepository providePartDataSource(Context context) {
        RevisionCardsDatabase database = RevisionCardsDatabase.getInstance(context);
        return new PartDataRepository(database.partDao());
    }

    public static CardDataRepository provideCardDataSource(Context context) {
        RevisionCardsDatabase database = RevisionCardsDatabase.getInstance(context);
        return new CardDataRepository(database.cardDao());
    }

    public static GradeDataRepository provideGradeDataSource(Context context) {
        RevisionCardsDatabase database = RevisionCardsDatabase.getInstance(context);
        return new GradeDataRepository(database.gradeDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        SubjectDataRepository dataSourceSubject = provideSubjectDataSource(context);
        LessonDataRepository dataSourceLesson = provideLessonDataSource(context);
        PartDataRepository dataSourcePart = providePartDataSource(context);
        CardDataRepository dataSourceCard = provideCardDataSource(context);
        GradeDataRepository dataSourceGrade = provideGradeDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceSubject, dataSourceLesson, dataSourcePart, dataSourceCard, dataSourceGrade, executor);
    }
}
