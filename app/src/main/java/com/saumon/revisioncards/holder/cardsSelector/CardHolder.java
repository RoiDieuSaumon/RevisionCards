package com.saumon.revisioncards.holder.cardsSelector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.models.Card;
import com.unnamed.b.atv.model.TreeNode;

public class CardHolder  extends TreeNode.BaseNodeViewHolder<CardHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private TextView textView;

    public CardHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_selector_card_node, null);

        setColorFromPosition(iconTreeItem.card.getPosition());

        textView = nodeView.findViewById(R.id.layout_cards_selector_card_node_text);
        textView.setText(iconTreeItem.card.getName());

        return nodeView;
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.card_1 : R.color.card_2;
        nodeView.setBackgroundResource(color);
    }

    public static class IconTreeItem {
        Card card;

        public IconTreeItem(Card card) {
            this.card = card;
        }
    }
}
