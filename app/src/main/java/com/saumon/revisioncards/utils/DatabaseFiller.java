package com.saumon.revisioncards.utils;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Part;
import com.saumon.revisioncards.models.Subject;

public class DatabaseFiller {
    public static void fillDatabase(Context context) {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        CardViewModel cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
        cardViewModel.deleteAll();
        for (int is = 1; is < 6; is++) {
            Subject subject = new Subject("Matière " + is, is);
            cardViewModel.createSubjectSync(subject);
            for (int il = 1; il < 6; il++) {
                Lesson lesson = new Lesson("Cours " + is + "::" + il, il, subject.getId());
                cardViewModel.createLessonSync(lesson);
                for (int ip = 1; ip < 6; ip++) {
                    Part part = new Part("Partie " + is + "::" + il + "::" + ip, ip, lesson.getId());
                    cardViewModel.createPartSync(part);
                    for (int ic = 1; ic < 6; ic++) {
                        Card card = new Card("Fiche " + is + "::" + il + "::" + ip + "::" + ic, "Texte " + is + "::" + il + "::" + ip + "::" + ic + "::1", "Texte " + is + "::" + il + "::" + ip + "::" + ic + "::2", ic, part.getId());
                        cardViewModel.createCardSync(card);
                    }
                }
            }
        }
    }
}
