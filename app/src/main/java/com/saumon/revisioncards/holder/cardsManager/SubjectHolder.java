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
import com.saumon.revisioncards.utils.CardViewModel;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Lesson;
import com.saumon.revisioncards.models.Subject;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class SubjectHolder extends TreeNode.BaseNodeViewHolder<SubjectHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private CardViewModel cardViewModel;
    private TextView textView;
    private PrintView iconView;

    public SubjectHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem iconTreeItem) {
        this.node = node;
        this.iconTreeItem = iconTreeItem;
        LayoutInflater inflater = LayoutInflater.from(context);
        nodeView = inflater.inflate(R.layout.layout_cards_manager_subject_node, null);

        setColorFromPosition(iconTreeItem.subject.getPosition());
        configureButtonsOnClick();
        configureViewModel();

        textView = nodeView.findViewById(R.id.layout_cards_manager_subject_node_text);
        textView.setText(iconTreeItem.subject.getName());
        iconView = nodeView.findViewById(R.id.layout_cards_manager_subject_node_icon);

        return nodeView;
    }

    @Override
    public void toggle(boolean active) {
        iconView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_down : R.string.ic_keyboard_arrow_right));
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.subject_1 : R.color.subject_2;
        nodeView.setBackgroundResource(color);
    }

    private void configureButtonsOnClick() {
        nodeView.findViewById(R.id.layout_cards_manager_subject_node_add_icon).setOnClickListener(v -> addLessonGetName());
        nodeView.findViewById(R.id.layout_cards_manager_subject_node_edit_icon).setOnClickListener(v -> editSubjectGetName());
        nodeView.findViewById(R.id.layout_cards_manager_subject_node_delete_icon).setOnClickListener(v -> deleteSubjectAskConfirmation());
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
    }

    private void addLessonGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle(context.getString(R.string.Add_lesson))
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Add), this::addLesson)
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
        Lesson lesson = new Lesson(name, position, iconTreeItem.subject.getId());
        TreeNode lessonNode = new TreeNode(new LessonHolder.IconTreeItem(lesson)).setViewHolder(new LessonHolder(context));
        getTreeView().addNode(node, lessonNode);
        getTreeView().expandNode(node);
        cardViewModel.createLesson(lesson);
    }

    private void editSubjectGetName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_update_node, null);

        AlertDialog dialog = builder
                .setView(dialogView)
                .setTitle(context.getString(R.string.Edit_subject))
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Edit), this::editSubject)
                .create();

        EditText editText = dialogView.findViewById(R.id.dialog_add_update_node_name_text);
        editText.setText(iconTreeItem.subject.getName());
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
        iconTreeItem.subject.setName(name);
        cardViewModel.updateSubject(iconTreeItem.subject);
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
        String message = context.getResources().getQuantityString(R.plurals.Subject_deletion_confirmation, nbCards, textView.getText().toString(), nbCards);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder .setTitle(context.getString(R.string.Delete_subject))
                .setMessage(message)
                .setNegativeButton(context.getString(R.string.Cancel), null)
                .setPositiveButton(context.getString(R.string.Delete), this::deleteSubject)
                .create()
                .show();
    }

    private void deleteSubject(DialogInterface dialog, int which) {
        TreeNode parentNode = node.getParent();
        getTreeView().removeNode(node);
        cardViewModel.deleteSubject(iconTreeItem.subject.getId());
        List<TreeNode> subjectNodes = parentNode.getChildren();
        int nbSubjectNodes = subjectNodes.size();
        for (int is = 0; is < nbSubjectNodes; is++) {
            SubjectHolder subjectHolder = (SubjectHolder) subjectNodes.get(is).getViewHolder();
            subjectHolder.setColorFromPosition(is + 1);
            subjectHolder.getSubject().setPosition(is + 1);
            cardViewModel.updateSubject(subjectHolder.getSubject());
        }
    }

    private Subject getSubject() {
        return iconTreeItem.subject;
    }

    public static class IconTreeItem {
        Subject subject;

        public IconTreeItem(Subject subject) {
            this.subject = subject;
        }
    }
}
