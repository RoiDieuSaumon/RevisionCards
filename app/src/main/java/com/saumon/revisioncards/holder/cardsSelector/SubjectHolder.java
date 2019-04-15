package com.saumon.revisioncards.holder.cardsSelector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.models.Subject;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class SubjectHolder extends TreeNode.BaseNodeViewHolder<SubjectHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private CheckBox checkBox;
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

        setColorFromPosition(iconTreeItem.position);

        TextView textView = nodeView.findViewById(R.id.layout_cards_selector_subject_node_text);
        textView.setText(iconTreeItem.subject.getName());
        iconView = nodeView.findViewById(R.id.layout_cards_selector_subject_node_icon);
        checkBox = nodeView.findViewById(R.id.layout_cards_selector_subject_node_check);
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
        boolean isSubjectNodeExpanded = node.isExpanded();
        if (!isSubjectNodeExpanded) {
            node.getViewHolder().getTreeView().expandNode(node);
        }
        List<TreeNode> lessonNodes = node.getChildren();
        for (int il = 0; il < lessonNodes.size(); il++) {
            TreeNode lessonNode = lessonNodes.get(il);
            ((LessonHolder) lessonNode.getViewHolder()).toggleCheckbox(iconTreeItem.isChecked);
            boolean isLessonNodeExpanded = lessonNode.isExpanded();
            if (!isLessonNodeExpanded) {
                lessonNode.getViewHolder().getTreeView().expandNode(lessonNode);
            }
            List<TreeNode> partNodes = lessonNode.getChildren();
            for (int ip = 0; ip < partNodes.size(); ip++) {
                TreeNode partNode = partNodes.get(ip);
                ((PartHolder) partNode.getViewHolder()).toggleCheckbox(iconTreeItem.isChecked);
                boolean isPartNodeExpanded = partNode.isExpanded();
                if (!isPartNodeExpanded) {
                    partNode.getViewHolder().getTreeView().expandNode(partNode);
                }
                List<TreeNode> cardNodes = partNode.getChildren();
                for (int ic = 0; ic < cardNodes.size(); ic++) {
                    TreeNode cardNode = cardNodes.get(ic);
                    ((CardHolder) cardNode.getViewHolder()).toggleCheckbox(iconTreeItem.isChecked);
                }
                if (!isPartNodeExpanded) {
                    partNode.getViewHolder().getTreeView().collapseNode(partNode);
                }
            }
            if (!isLessonNodeExpanded) {
                lessonNode.getViewHolder().getTreeView().collapseNode(lessonNode);
            }
        }
        if (!isSubjectNodeExpanded) {
            node.getViewHolder().getTreeView().collapseNode(node);
        }
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.subject_1 : R.color.subject_2;
        nodeView.setBackgroundResource(color);
    }

    public static class IconTreeItem {
        Subject subject;
        int position;
        boolean isChecked = false;

        public IconTreeItem(Subject subject, int position) {
            this.subject = subject;
            this.position = position;
        }
    }
}
