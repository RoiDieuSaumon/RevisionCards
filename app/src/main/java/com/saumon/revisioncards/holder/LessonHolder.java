package com.saumon.revisioncards.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.unnamed.b.atv.model.TreeNode;

public class LessonHolder extends TreeNode.BaseNodeViewHolder<LessonHolder.IconTreeItem> {
    private PrintView iconView;

    public LessonHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, LessonHolder.IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_lesson_node, null, false);
        TextView textView = view.findViewById(R.id.layout_lesson_node_text);
        textView.setText(value.text);
        view.setBackgroundResource(getColorFromPosition(value.position));

        iconView = view.findViewById(R.id.layout_lesson_node_icon);
        view.findViewById(R.id.layout_lesson_node_add_icon).setOnClickListener(v -> addPart());
        view.findViewById(R.id.layout_lesson_node_edit_icon).setOnClickListener(v -> editLesson());
        view.findViewById(R.id.layout_lesson_node_delete_icon).setOnClickListener(v -> deleteLesson());

        return view;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void addPart() {
        Toast.makeText(context, "Ajoute une partie", Toast.LENGTH_LONG).show();
    }

    private void editLesson() {
        Toast.makeText(context, "Modifie le cours", Toast.LENGTH_LONG).show();
    }

    private void deleteLesson() {
        Toast.makeText(context, "Supprime le cours", Toast.LENGTH_LONG).show();
    }

    private int getColorFromPosition(long position)
    {
        return (0 != position % 2) ? R.color.lesson_1 : R.color.lesson_2;
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
