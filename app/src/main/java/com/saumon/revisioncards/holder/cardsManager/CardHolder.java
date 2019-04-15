package com.saumon.revisioncards.holder.cardsManager;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.saumon.revisioncards.CardViewModel;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class CardHolder extends TreeNode.BaseNodeViewHolder<CardHolder.IconTreeItem> {
    private TreeNode node;
    private CardHolder.IconTreeItem iconTreeItem;
    private View nodeView;
    private CardViewModel cardViewModel;
    private TextView textView;

    public CardHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, CardHolder.IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_manager_card_node, null);

        setColorFromPosition(iconTreeItem.card.getPosition());
        configureButtonsOnClick();
        configureViewModel();

        textView = nodeView.findViewById(R.id.layout_cards_manager_card_node_text);
        textView.setText(iconTreeItem.card.getName());

        return nodeView;
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.card_1 : R.color.card_2;
        nodeView.setBackgroundResource(color);
    }

    private void configureButtonsOnClick() {
        nodeView.findViewById(R.id.layout_cards_manager_card_node_edit_icon).setOnClickListener(v -> editCardGetTexts());
        nodeView.findViewById(R.id.layout_cards_manager_card_node_delete_icon).setOnClickListener(v -> deleteCardAskConfirmation());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
    }

    private void editCardGetTexts() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_card, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle("Modifier une fiche")
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Modifier", this::editCard)
                .create();

        EditText editTextName = dialogView.findViewById(R.id.dialog_add_update_card_name_text);
        EditText editTextText1 = dialogView.findViewById(R.id.dialog_add_update_card_text1_text);
        EditText editTextText2 = dialogView.findViewById(R.id.dialog_add_update_card_text2_text);

        editTextName.setText(iconTreeItem.card.getName());
        editTextText1.setText(iconTreeItem.card.getText1());
        editTextText2.setText(iconTreeItem.card.getText2());

        editTextName.setSelection(editTextName.getText().length());
        editTextName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Window window = dialog.getWindow();
                if (null == window) {
                    return;
                }
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        dialog.show();
    }

    private void editCard(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_card_name_text)).getText().toString();
        String text1 = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_card_text1_text)).getText().toString();
        String text2 = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_card_text2_text)).getText().toString();
        if (text1.isEmpty() || text2.isEmpty()) {
            return;
        }
        if (name.isEmpty()) {
            name = text1 + " / " + text2;
        }
        textView.setText(name);
        iconTreeItem.card.setName(name);
        iconTreeItem.card.setText1(text1);
        iconTreeItem.card.setText2(text2);
        cardViewModel.updateCard(iconTreeItem.card);
    }

    private void deleteCardAskConfirmation() {
        String message = "Êtes-vous sûr de supprimer la fiche " + textView.getText().toString() + " ?";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle("Supprimer une fiche")
                .setMessage(message)
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Supprimer", this::deleteCard)
                .create()
                .show();
    }

    private void deleteCard(DialogInterface dialog, int which) {
        TreeNode parentNode = node.getParent();
        getTreeView().removeNode(node);
        cardViewModel.deleteCard(iconTreeItem.card.getId());
        List<TreeNode> cardNodes = parentNode.getChildren();
        int nbCardNodes = cardNodes.size();
        for (int ic = 0; ic < nbCardNodes; ic++) {
            CardHolder cardHolder = (CardHolder) cardNodes.get(ic).getViewHolder();
            cardHolder.setColorFromPosition(ic + 1);
            cardHolder.getCard().setPosition(ic + 1);
            cardViewModel.updateCard(cardHolder.getCard());
        }
    }

    private Card getCard() {
        return iconTreeItem.card;
    }

    public static class IconTreeItem {
        Card card;

        public IconTreeItem(Card card) {
            this.card = card;
        }
    }
}
