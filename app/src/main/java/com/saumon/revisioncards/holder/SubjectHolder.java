package com.saumon.revisioncards.holder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.R;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class SubjectHolder extends TreeNode.BaseNodeViewHolder<SubjectHolder.IconTreeItem> {
    private TreeNode node;
    private View nodeView;
    private TextView textView;
    private PrintView iconView;

    public SubjectHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem iconTreeItem) {
        this.node = node;
        LayoutInflater inflater = LayoutInflater.from(context);

        nodeView = inflater.inflate(R.layout.layout_subject_node, null);
        setColorFromPosition(iconTreeItem.position);
        configureButtonsOnClick();

        textView = nodeView.findViewById(R.id.layout_subject_node_text);
        textView.setText(iconTreeItem.text);

        iconView = nodeView.findViewById(R.id.layout_subject_node_icon);

        return nodeView;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void setColorFromPosition(long position) {
        int color = (0 != position % 2) ? R.color.subject_1 : R.color.subject_2;
        nodeView.setBackgroundResource(color);
    }

    private void configureButtonsOnClick() {
        nodeView.findViewById(R.id.layout_subject_node_add_icon).setOnClickListener(v -> addLessonGetName());
        nodeView.findViewById(R.id.layout_subject_node_edit_icon).setOnClickListener(v -> editSubjectGetName());
        nodeView.findViewById(R.id.layout_subject_node_delete_icon).setOnClickListener(v -> deleteSubjectAskConfirmation());
    }

    private void addLessonGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle("Ajouter un cours")
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Ajouter", this::addLesson)
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

    private void addLesson(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_node_name_text)).getText().toString();
        if (name.isEmpty()) {
            return;
        }
        int position = node.getChildren().size() + 1;
        TreeNode lesson = new TreeNode(new LessonHolder.IconTreeItem(name, position)).setViewHolder(new LessonHolder(context));
        getTreeView().addNode(node, lesson);
    }

    private void editSubjectGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle("Modifier une matière")
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Modifier", this::editSubject)
                .create();

        EditText editText = dialogView.findViewById(R.id.dialog_add_update_node_name_text);
        editText.setText(textView.getText().toString());
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

    private void editSubject(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_node_name_text)).getText().toString();
        if (name.isEmpty()) {
            return;
        }
        textView.setText(name);
    }

    private void deleteSubjectAskConfirmation() {
        List<TreeNode> lessons = node.getChildren();
        int nbLessons = lessons.size();
        int nbParts;
        int nbCards = 0;
        for (int il = 0; il < nbLessons; il++) {
            List<TreeNode> parts = lessons.get(il).getChildren();
            nbParts = parts.size();
            for (int ip = 0; ip < nbParts; ip++) {
                nbCards += parts.get(ip).getChildren().size();
            }
        }
        String message = "Êtes-vous sûr de vouloir supprimer la matière " + textView.getText().toString() + " ?";
        if (0 < nbCards) {
            message += "\nElle contient " + nbCards + " fiches.";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle("Supprimer une matière")
                .setMessage(message)
                .setNegativeButton("Annuler", null)
                .setPositiveButton("Supprimer", this::deleteSubject)
                .create()
                .show();
    }

    private void deleteSubject(DialogInterface dialog, int which) {
        TreeNode parentNode = node.getParent();
        getTreeView().removeNode(node);
        List<TreeNode> subjectNodes = parentNode.getChildren();
        int nbSubjectNodes = subjectNodes.size();
        for (int is = 0; is < nbSubjectNodes; is++) {
            ((SubjectHolder) subjectNodes.get(is).getViewHolder()).setColorFromPosition(is + 1);
        }
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
