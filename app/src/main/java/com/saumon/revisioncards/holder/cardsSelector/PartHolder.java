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

    IconTreeItem getIconTreeItem() {
        return iconTreeItem;
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
            ((CardHolder) cardNodes.get(ic).getViewHolder()).toggleCheckbox(iconTreeItem.isChecked);
        }
        if (!isPartNodeExpanded) {
            node.getViewHolder().getTreeView().collapseNode(node);
        }
        if (iconTreeItem.isChecked) {
            boolean areAllChecked = true;
            List<TreeNode> partNodes = node.getParent().getChildren();
            for (int ip = 0; ip < partNodes.size(); ip++) {
                if (!((PartHolder) partNodes.get(ip).getViewHolder()).iconTreeItem.isChecked) {
                    areAllChecked = false;
                    break;
                }
            }
            if (areAllChecked) {
                ((LessonHolder) node.getParent().getViewHolder()).toggleCheckbox(true);

                List<TreeNode> lessonNodes = node.getParent().getParent().getChildren();
                for (int il = 0; il < lessonNodes.size(); il++) {
                    if (!((LessonHolder) lessonNodes.get(il).getViewHolder()).getIconTreeItem().isChecked) {
                        areAllChecked = false;
                        break;
                    }
                }
                if (areAllChecked) {
                    ((SubjectHolder) node.getParent().getParent().getViewHolder()).toggleCheckbox(true);
                }
            }
        } else {
            ((LessonHolder) node.getParent().getViewHolder()).toggleCheckbox(false);
            ((SubjectHolder) node.getParent().getParent().getViewHolder()).toggleCheckbox(false);
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
