package com.saumon.revisioncards.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.unnamed.b.atv.model.TreeNode;

public class SubjectHolder extends TreeNode.BaseNodeViewHolder<SubjectHolder.IconTreeItem> {
    private PrintView iconView;

    public SubjectHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_subject_node, null, false);
        TextView textView = view.findViewById(R.id.layout_subject_node_text);
        textView.setText(value.text);
        view.setBackgroundResource(getColorFromPosition(value.position));

        iconView = view.findViewById(R.id.layout_subject_node_icon);
        view.findViewById(R.id.layout_subject_node_add_icon).setOnClickListener(v -> addLesson());
        view.findViewById(R.id.layout_subject_node_edit_icon).setOnClickListener(v -> editSubject());
        view.findViewById(R.id.layout_subject_node_delete_icon).setOnClickListener(v -> deleteSubject());

        return view;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private int getColorFromPosition(long position) {
        return (0 != position % 2) ? R.color.subject_1 : R.color.subject_2;
    }

    private void addLesson() {
        Toast.makeText(context, "Ajoute un cours", Toast.LENGTH_LONG).show();
    }

    private void editSubject() {
        Toast.makeText(context, "Modifie la matière", Toast.LENGTH_LONG).show();
    }

    private void deleteSubject() {
        Toast.makeText(context, "Supprime la matière", Toast.LENGTH_LONG).show();
    }

    public static class IconTreeItem {
        String text;
        long position;

        public IconTreeItem(String text, long position) {
            this.text = text;
            this.position = position;
        }
    }
}
