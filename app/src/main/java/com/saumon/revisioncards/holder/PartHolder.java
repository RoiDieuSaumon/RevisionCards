package com.saumon.revisioncards.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.unnamed.b.atv.model.TreeNode;

public class PartHolder extends TreeNode.BaseNodeViewHolder<PartHolder.IconTreeItem> {
    private PrintView iconView;

    public PartHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, PartHolder.IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_part_node, null);
        TextView textView = view.findViewById(R.id.layout_part_node_text);
        textView.setText(value.text);
        view.setBackgroundResource(getColorFromPosition(value.position));

        iconView = view.findViewById(R.id.layout_part_node_icon);
        view.findViewById(R.id.layout_part_node_add_icon).setOnClickListener(v -> addCard());
        view.findViewById(R.id.layout_part_node_edit_icon).setOnClickListener(v -> editPart());
        view.findViewById(R.id.layout_part_node_delete_icon).setOnClickListener(v -> deletePart());

        return view;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void addCard() {
        Toast.makeText(context, "Ajoute une fiche", Toast.LENGTH_LONG).show();
    }

    private void editPart() {
        Toast.makeText(context, "Modifie la partie", Toast.LENGTH_LONG).show();
    }

    private void deletePart() {
        Toast.makeText(context, "Supprime la partie", Toast.LENGTH_LONG).show();
    }

    private int getColorFromPosition(long position)
    {
        return (0 != position % 2) ? R.color.part_1 : R.color.part_2;
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
