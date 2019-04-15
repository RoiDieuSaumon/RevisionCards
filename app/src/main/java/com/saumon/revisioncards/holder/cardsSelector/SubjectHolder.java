package com.saumon.revisioncards.holder.cardsSelector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.models.Subject;
import com.unnamed.b.atv.model.TreeNode;

public class SubjectHolder extends TreeNode.BaseNodeViewHolder<SubjectHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private TextView textView;
    private PrintView iconView;

    public SubjectHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_selector_subject_node, null);

        setColorFromPosition(iconTreeItem.subject.getPosition());

        textView = nodeView.findViewById(R.id.layout_cards_selector_subject_node_text);
        textView.setText(iconTreeItem.subject.getName());
        iconView = nodeView.findViewById(R.id.layout_cards_selector_subject_node_icon);

        return nodeView;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.subject_1 : R.color.subject_2;
        nodeView.setBackgroundResource(color);
    }

    public static class IconTreeItem {
        Subject subject;

        public IconTreeItem(Subject subject) {
            this.subject = subject;
        }
    }
}
