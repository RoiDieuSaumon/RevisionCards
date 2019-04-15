package com.saumon.revisioncards.holder.cardsSelector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.models.Part;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class PartHolder extends TreeNode.BaseNodeViewHolder<PartHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private CheckBox checkBox;
    private PrintView iconView;

    public PartHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_selector_part_node, null);

        setColorFromPosition(iconTreeItem.position);

        TextView textView = nodeView.findViewById(R.id.layout_cards_selector_part_node_text);
        textView.setText(iconTreeItem.part.getName());
        iconView = nodeView.findViewById(R.id.layout_cards_selector_part_node_icon);
        checkBox = nodeView.findViewById(R.id.layout_cards_selector_part_node_check);
        checkBox.setOnClickListener(this::cascadeCheckBoxes);

        return nodeView;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    void toggleCheckbox(boolean isChecked) {
        checkBox.setChecked(isChecked);
        iconTreeItem.isChecked = isChecked;
    }

    private void cascadeCheckBoxes(View view) {
        iconTreeItem.isChecked = ((CheckBox) view).isChecked();
        boolean isPartNodeExpanded = node.isExpanded();
        if (!isPartNodeExpanded) {
            node.getViewHolder().getTreeView().expandNode(node);
        }
        List<TreeNode> cardNodes = node.getChildren();
        for (int ic = 0; ic < cardNodes.size(); ic++) {
            TreeNode cardNode = cardNodes.get(ic);
            ((CardHolder) cardNode.getViewHolder()).toggleCheckbox(iconTreeItem.isChecked);
        }
        if (!isPartNodeExpanded) {
            node.getViewHolder().getTreeView().collapseNode(node);
        }
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.part_1 : R.color.part_2;
        nodeView.setBackgroundResource(color);
    }

    public static class IconTreeItem {
        Part part;
        int position;
        boolean isChecked = false;

        public IconTreeItem(Part part, int position) {
            this.part = part;
            this.position = position;
        }
    }
}
