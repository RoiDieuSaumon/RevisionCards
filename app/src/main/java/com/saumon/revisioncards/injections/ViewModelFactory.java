package com.saumon.revisioncards.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.saumon.revisioncards.CardViewModel;
import com.saumon.revisioncards.repositories.CardDataRepository;
import com.saumon.revisioncards.repositories.GradeDataRepository;
import com.saumon.revisioncards.repositories.LessonDataRepository;
import com.saumon.revisioncards.repositories.PartDataRepository;
import com.saumon.revisioncards.repositories.SubjectDataRepository;

import java.util.concurrent.Executor;

@SuppressWarnings("unchecked cast")
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final SubjectDataRepository subjectDataSource;
    private final LessonDataRepository lessonDataSource;
    private final PartDataRepository partDataSource;
    private final CardDataRepository cardDataSource;
    private final GradeDataRepository gradeDataSource;
    private final Executor executor;

    public ViewModelFactory(
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

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CardViewModel.class)) {
            return (T) new CardViewModel(subjectDataSource, lessonDataSource, partDataSource, cardDataSource, gradeDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
