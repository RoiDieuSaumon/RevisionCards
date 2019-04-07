package com.saumon.revisioncards.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.holder.CardHolder;
import com.saumon.revisioncards.holder.LessonHolder;
import com.saumon.revisioncards.holder.PartHolder;
import com.saumon.revisioncards.holder.SubjectHolder;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

public class CardsTreeViewFragment extends Fragment {
    public CardsTreeViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cards_tree_view, null, false);
        ViewGroup containerView = rootView.findViewById(R.id.fragment_cards_tree_view_container);

        TreeNode root = TreeNode.root();
        TreeNode subject1 = new TreeNode(new SubjectHolder.IconTreeItem("Matière 1", 1)).setViewHolder(new SubjectHolder(getActivity()));
        TreeNode subject2 = new TreeNode(new SubjectHolder.IconTreeItem( "Matière 2", 2)).setViewHolder(new SubjectHolder(getActivity()));
        TreeNode subject3 = new TreeNode(new SubjectHolder.IconTreeItem( "Matière 3", 3)).setViewHolder(new SubjectHolder(getActivity()));
        TreeNode subject4 = new TreeNode(new SubjectHolder.IconTreeItem( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", 4)).setViewHolder(new SubjectHolder(getActivity()));
        TreeNode lesson1 = new TreeNode(new LessonHolder.IconTreeItem("Cours 1", 1)).setViewHolder(new LessonHolder(getActivity()));
        TreeNode lesson2 = new TreeNode(new LessonHolder.IconTreeItem("Cours 2", 2)).setViewHolder(new LessonHolder(getActivity()));
        TreeNode part1 = new TreeNode(new PartHolder.IconTreeItem("Partie 1", 1)).setViewHolder(new PartHolder(getActivity()));
        TreeNode part2 = new TreeNode(new PartHolder.IconTreeItem("Partie 2", 2)).setViewHolder(new PartHolder(getActivity()));
        TreeNode card1 = new TreeNode(new CardHolder.IconTreeItem("Fiche 1", 1)).setViewHolder(new CardHolder(getActivity()));
        TreeNode card2 = new TreeNode(new CardHolder.IconTreeItem("Fiche 2", 2)).setViewHolder(new CardHolder(getActivity()));
        part1.addChildren(card1, card2);
        lesson1.addChildren(part1, part2);
        subject1.addChildren(lesson1, lesson2);
        root.addChildren(subject1, subject2, subject3, subject4);

        AndroidTreeView treeView = new AndroidTreeView(getActivity(), root);
        containerView.addView(treeView.getView());

        return rootView;
    }
}
