package com.saumon.revisioncards.utils;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Subject;

public class DatabaseFiller {
    public static boolean fillDatabase(Context context) {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        CardViewModel cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
        for (int is = 0; is < 5; is++) {
            Subject subject = new Subject("Matière " + is, is);
            cardViewModel.createSubject(subject);
        }

        return true;
    }
}
