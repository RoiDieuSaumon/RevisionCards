package com.saumon.revisioncards.holder;

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
import android.widget.Toast;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.CardViewModel;
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
    public View createNodeView(TreeNode node, PartHolder.IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_part_node, null);

        setColorFromPosition(iconTreeItem.part.getPosition());
        configureButtonsOnClick();
        configureViewModel();

        textView = nodeView.findViewById(R.id.layout_part_node_text);
        textView.setText(iconTreeItem.part.getName());
        iconView = nodeView.findViewById(R.id.layout_part_node_icon);

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
        nodeView.findViewById(R.id.layout_part_node_add_icon).setOnClickListener(v -> addCardGetName());
        nodeView.findViewById(R.id.layout_part_node_edit_icon).setOnClickListener(v -> editPartGetName());
        nodeView.findViewById(R.id.layout_part_node_delete_icon).setOnClickListener(v -> deletePartAskConfirmation());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
    }

    // TODO : créer vue dialog pour récupérer textes
    private void addCardGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle("Ajouter une fiche")
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Ajouter", this::addCard)
                .create();

        EditText editText = dialogView.findViewById(R.id.dialog_add_update_node_name_text);
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

    // TODO : récupérer les textes
    private void addCard(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_node_name_text)).getText().toString();
        if (name.isEmpty()) {
            return;
        }
        int position = node.getChildren().size() + 1;
        Card card = new Card(name, "", "", position, iconTreeItem.part.getId());
        TreeNode cardNode = new TreeNode(new CardHolder.IconTreeItem(card)).setViewHolder(new CardHolder(context));
        getTreeView().addNode(node, cardNode);
        cardViewModel.createCard(card);
    }

    private void editPartGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle("Modifier une partie")
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Modifier", this::editPart)
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
        String message = "La partie " + textView.getText().toString() + " contient " + nbCards + " fiche";
        if (1 != nbCards) {
            message += "s";
        }
        message += ".\nÊtes-vous sûr de vouloir la supprimer ?";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle("Supprimer une partie")
                .setMessage(message)
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Supprimer", this::deletePart)
                .create()
                .show();
    }

    private void deletePart(DialogInterface dialog, int which) {
        TreeNode parentNode = node.getParent();
        getTreeView().removeNode(node);
        cardViewModel.deletePart(iconTreeItem.part.getId());
        List<TreeNode> partNodes = parentNode.getChildren();
        int nbPartNodes = partNodes.size();
        for (int il = 0; il < nbPartNodes; il++) {
            PartHolder partHolder = (PartHolder) partNodes.get(il).getViewHolder();
            partHolder.setColorFromPosition(il + 1);
            partHolder.getPart().setPosition(il + 1);
            cardViewModel.updatePart(partHolder.getPart());
        }
    }

    private Part getPart() {
        return iconTreeItem.part;
    }

    public static class IconTreeItem {
        String text;
        long position;
        Part part;

        public IconTreeItem(String text, long position) {
            this.text = text;
            this.position = position;
        }

        public IconTreeItem(Part part) {
            this.part = part;
        }
    }
}
