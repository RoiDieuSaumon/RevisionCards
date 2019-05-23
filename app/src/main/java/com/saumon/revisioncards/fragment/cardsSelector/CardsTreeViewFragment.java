package com.saumon.revisioncards.fragment.cardsSelector;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saumon.revisioncards.utils.CardViewModel;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.holder.cardsSelector.CardHolder;
import com.saumon.revisioncards.holder.cardsSelector.LessonHolder;
import com.saumon.revisioncards.holder.cardsSelector.PartHolder;
import com.saumon.revisioncards.holder.cardsSelector.SubjectHolder;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Part;
import com.saumon.revisioncards.models.Subject;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.util.List;

public class CardsTreeViewFragment extends Fragment {
    private CardViewModel cardViewModel;
    private AndroidTreeView treeView;

    public CardsTreeViewFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        configureViewModel();

        View rootView = inflater.inflate(R.layout.fragment_cards_tree_view, null);
        ViewGroup containerView = rootView.findViewById(R.id.fragment_cards_tree_view_container);

        initTreeView();
        containerView.addView(treeView.getView());

        return rootView;
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getActivity());
        cardViewModel = ViewModelProviders.of(this, viewModelFactory).get(CardViewModel.class);
    }

    private void initTreeView() {
        Activity activity = getActivity();
        if (null == activity) {
            return;
        }

        List<Subject> subjects = cardViewModel.getSubjects();
        List<Lesson> lessons = cardViewModel.getLessons();
        List<Part> parts = cardViewModel.getParts();
        List<Card> cards = cardViewModel.getCards();

        TreeNode root = TreeNode.root();
        int subjectPosition = 1;
        for (int is = 0; is < subjects.size(); is++) {
            Subject subject = subjects.get(is);
            TreeNode subjectNode = new TreeNode(new SubjectHolder.IconTreeItem(subject, subjectPosition)).setViewHolder(new SubjectHolder(activity));
            int lessonPosition = 1;
            int nbCardsSubject = 0;
            for (int il = 0; il < lessons.size(); il++) {
                Lesson lesson = lessons.get(il);
                if (subject.getId() != lesson.getSubjectId()) {
                    continue;
                }
                TreeNode lessonNode = new TreeNode(new LessonHolder.IconTreeItem(lesson, lessonPosition)).setViewHolder(new LessonHolder(activity));
                int partPosition = 1;
                int nbCardsLesson = 0;
                for (int ip = 0; ip < parts.size(); ip++) {
                    Part part = parts.get(ip);
                    if (lesson.getId() != part.getLessonId()) {
                        continue;
                    }
                    TreeNode partNode = new TreeNode(new PartHolder.IconTreeItem(part, partPosition)).setViewHolder(new PartHolder(activity));
                    int nbCards = 0;
                    for (int ic = 0; ic < cards.size(); ic++) {
                        Card card = cards.get(ic);
                        if (part.getId() != card.getPartId()) {
                            continue;
                        }
                        TreeNode cardNode = new TreeNode(new CardHolder.IconTreeItem(card)).setViewHolder(new CardHolder(activity));
                        partNode.addChild(cardNode);
                        nbCards++;
                    }
                    nbCardsLesson += nbCards;
                    nbCardsSubject += nbCards;
                    if (0 < nbCards) {
                        lessonNode.addChild(partNode);
                        partPosition++;
                    }
                }
                if (0 < nbCardsLesson) {
                    subjectNode.addChild(lessonNode);
                    lessonPosition++;
                }
            }
            if (0 < nbCardsSubject) {
                root.addChild(subjectNode);
                subjectPosition++;
            }
        }

        treeView = new AndroidTreeView(activity, root);
    }
}
