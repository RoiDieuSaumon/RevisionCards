package com.saumon.revisioncards.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saumon.revisioncards.R;
import com.saumon.revisioncards.models.Card;
import com.unnamed.b.atv.model.TreeNode;

public class CardHolder extends TreeNode.BaseNodeViewHolder<CardHolder.IconTreeItem> {
    public CardHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, CardHolder.IconTreeItem value) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_card_node, null);
        TextView textView = view.findViewById(R.id.layout_card_node_text);
        textView.setText(value.text);
        view.setBackgroundResource(getColorFromPosition(value.position));

        view.findViewById(R.id.layout_card_node_edit_icon).setOnClickListener(v -> editCard());
        view.findViewById(R.id.layout_card_node_delete_icon).setOnClickListener(v -> deleteCard());

        return view;
    }

    private int getColorFromPosition(long position)
    {
        return (0 != position % 2) ? R.color.card_1 : R.color.card_2;
    }

    private void editCard() {
        Toast.makeText(context, "Modifie la fiche", Toast.LENGTH_LONG).show();
    }

    private void deleteCard() {
        Toast.makeText(context, "Supprime la fiche", Toast.LENGTH_LONG).show();
    }

    public static class IconTreeItem {
        String text;
        long position;
        Card card;

        public IconTreeItem(String text, long position) {
            this.text = text;
            this.position = position;
        }

        public IconTreeItem(Card card) {
            this.card = card;
        }
    }
}
