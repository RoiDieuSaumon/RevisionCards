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

import com.github.johnkil.print.PrintView;
import com.saumon.revisioncards.CardViewModel;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Part;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class LessonHolder extends TreeNode.BaseNodeViewHolder<LessonHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private CardViewModel cardViewModel;
    private TextView textView;
    private PrintView iconView;

    public LessonHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, LessonHolder.IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_manager_lesson_node, null);

        setColorFromPosition(iconTreeItem.lesson.getPosition());
        configureButtonsOnClick();
        configureViewModel();

        textView = nodeView.findViewById(R.id.layout_cards_manager_lesson_node_text);
        textView.setText(iconTreeItem.lesson.getName());
        iconView = nodeView.findViewById(R.id.layout_cards_manager_lesson_node_icon);

        return nodeView;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.lesson_1 : R.color.lesson_2;
        nodeView.setBackgroundResource(color);
    }

    private void configureButtonsOnClick() {
        nodeView.findViewById(R.id.layout_cards_manager_lesson_node_add_icon).setOnClickListener(v -> addPartGetName());
        nodeView.findViewById(R.id.layout_cards_manager_lesson_node_edit_icon).setOnClickListener(v -> editLessonGetName());
        nodeView.findViewById(R.id.layout_cards_manager_lesson_node_delete_icon).setOnClickListener(v -> deleteLessonAskConfirmation());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
    }

    private void addPartGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle(context.getString(R.string.Add_part))
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Add), this::addPart)
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

    private void addPart(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_node_name_text)).getText().toString();
        if (name.isEmpty()) {
            return;
        }
        int position = node.getChildren().size() + 1;
        Part part = new Part(name, position, iconTreeItem.lesson.getId());
        TreeNode partNode = new TreeNode(new PartHolder.IconTreeItem(part)).setViewHolder(new PartHolder(context));
        getTreeView().addNode(node, partNode);
        getTreeView().expandNode(node);
        cardViewModel.createPart(part);
    }

    private void editLessonGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle(context.getString(R.string.Edit_lesson))
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Edit), this::editLesson)
                .create();

        EditText editText = dialogView.findViewById(R.id.dialog_add_update_node_name_text);
        editText.setText(iconTreeItem.lesson.getName());
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

    private void editLesson(DialogInterface dialog, int which) {
        String name = ((EditText) ((AlertDialog) dialog).findViewById(R.id.dialog_add_update_node_name_text)).getText().toString();
        if (name.isEmpty()) {
            return;
        }
        textView.setText(name);
        iconTreeItem.lesson.setName(name);
        cardViewModel.updateLesson(iconTreeItem.lesson);
    }

    private void deleteLessonAskConfirmation() {
        List<TreeNode> parts = node.getChildren();
        int nbParts = parts.size();
        int nbCards = 0;
        for (int ip = 0; ip < nbParts; ip++) {
            nbCards += parts.get(ip).getChildren().size();
        }
        String message = context.getResources().getQuantityString(R.plurals.Lesson_deletion_confirmation, nbCards, textView.getText().toString(), nbCards);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle(context.getString(R.string.Delete_lesson))
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Delete), this::deleteLesson)
                .create()
                .show();
    }

    private void deleteLesson(DialogInterface dialog, int which) {
        TreeNode parentNode = node.getParent();
        getTreeView().removeNode(node);
        cardViewModel.deleteLesson(iconTreeItem.lesson.getId());
        List<TreeNode> lessonNodes = parentNode.getChildren();
        int nbLessonNodes = lessonNodes.size();
        for (int il = 0; il < nbLessonNodes; il++) {
            LessonHolder lessonHolder = (LessonHolder) lessonNodes.get(il).getViewHolder();
            lessonHolder.setColorFromPosition(il + 1);
            lessonHolder.getLesson().setPosition(il + 1);
            cardViewModel.updateLesson(lessonHolder.getLesson());
        }
    }

    private Lesson getLesson() {
        return iconTreeItem.lesson;
    }

    public static class IconTreeItem {
        Lesson lesson;

        public IconTreeItem(Lesson lesson) {
            this.lesson = lesson;
        }
    }
}
