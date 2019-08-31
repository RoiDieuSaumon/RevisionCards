package com.saumon.revisioncards.holder.cardsManager;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.utils.CardViewModel;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;
import com.saumon.revisioncards.models.Part;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class PartHolder extends TreeNode.BaseNodeViewHolder<PartHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private CardViewModel cardViewModel;
    private TextView textView;
    private PrintView iconView;

    public PartHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, @NonNull PartHolder.IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_manager_part_node, null);

        setColorFromPosition(iconTreeItem.part.getPosition());
        configureButtonsOnClick();
        configureViewModel();

        textView = nodeView.findViewById(R.id.layout_cards_manager_part_node_text);
        textView.setText(iconTreeItem.part.getName());
        iconView = nodeView.findViewById(R.id.layout_cards_manager_part_node_icon);

        return nodeView;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.part_1 : R.color.part_2;
        nodeView.setBackgroundResource(color);
    }

    private void configureButtonsOnClick() {
        nodeView.findViewById(R.id.layout_cards_manager_part_node_add_icon).setOnClickListener(v -> addCardGetName());
        nodeView.findViewById(R.id.layout_cards_manager_part_node_edit_icon).setOnClickListener(v -> editPartGetName());
        nodeView.findViewById(R.id.layout_cards_manager_part_node_delete_icon).setOnClickListener(v -> deletePartAskConfirmation());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
    }

    private void addCardGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_card, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle(context.getString(R.string.Add_card))
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Add), this::addCard)
                .create();

        EditText editText = dialogView.findViewById(R.id.dialog_add_update_card_name_text);
        editText.setOnFocusChangeListener((v, hasFocus) -> {
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

    private void addCard(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_card_name_text)).getText().toString();
        String text1 = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_card_text1_text)).getText().toString();
        String text2 = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_card_text2_text)).getText().toString();
        if (text1.isEmpty() || text2.isEmpty()) {
            return;
        }
        if (name.isEmpty()) {
            name = null;
        }
        int position = node.getChildren().size() + 1;
        Card card = new Card(name, text1, text2, position, iconTreeItem.part.getId());
        TreeNode cardNode = new TreeNode(new CardHolder.IconTreeItem(card)).setViewHolder(new CardHolder(context));
        getTreeView().addNode(node, cardNode);
        getTreeView().expandNode(node);
        cardViewModel.createCard(card);
    }

    private void editPartGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle(context.getString(R.string.Edit_part))
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Edit), this::editPart)
                .create();

        EditText editText = dialogView.findViewById(R.id.dialog_add_update_node_name_text);
        editText.setText(iconTreeItem.part.getName());
        editText.setSelection(editText.getText().length());
        editText.setOnFocusChangeListener((v, hasFocus) -> {
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

    private void editPart(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_node_name_text)).getText().toString();
        if (name.isEmpty()) {
            return;
        }
        textView.setText(name);
        iconTreeItem.part.setName(name);
        cardViewModel.updatePart(iconTreeItem.part);
    }

    private void deletePartAskConfirmation() {
        int nbCards = node.getChildren().size();
        String message = context.getResources().getQuantityString(R.plurals.Part_deletion_confirmation, nbCards, textView.getText().toString(), nbCards);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle(context.getString(R.string.Delete_part))
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Delete), this::deletePart)
                .create()
                .show();
    }

    private void deletePart(DialogInterface dialog, int which) {
        TreeNode parentNode = node.getParent();
        getTreeView().removeNode(node);
        cardViewModel.deletePart(iconTreeItem.part);
        List<TreeNode> partNodes = parentNode.getChildren();
        int nbPartNodes = partNodes.size();
        for (int ip = 0; ip < nbPartNodes; ip++) {
            PartHolder partHolder = (PartHolder) partNodes.get(ip).getViewHolder();
            partHolder.setColorFromPosition(ip + 1);
            partHolder.getPart().setPosition(ip + 1);
            cardViewModel.updatePart(partHolder.getPart());
        }
    }

    private Part getPart() {
        return iconTreeItem.part;
    }

    public static class IconTreeItem {
        Part part;

        public IconTreeItem(Part part) {
            this.part = part;
        }
    }
}
