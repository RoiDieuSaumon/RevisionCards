package com.saumon.revisioncards.holder.cardsSelector;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.saumon.revisioncards.CardViewModel;
import com.saumon.revisioncards.CardsSelection;
import com.saumon.revisioncards.R;
import com.saumon.revisioncards.injection.Injection;
import com.saumon.revisioncards.injections.ViewModelFactory;
import com.saumon.revisioncards.models.Card;
import com.unnamed.b.atv.model.TreeNode;

import java.util.List;

public class CardHolder  extends TreeNode.BaseNodeViewHolder<CardHolder.IconTreeItem> {
    private TreeNode node;
    private IconTreeItem iconTreeItem;
    private View nodeView;
    private CardViewModel cardViewModel;
    private CheckBox checkBox;

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
        configureViewModel();

        String nameToDisplay = iconTreeItem.card.getName();
        if (null == nameToDisplay || nameToDisplay.isEmpty()) {
            nameToDisplay = iconTreeItem.card.getText1() + " / " + iconTreeItem.card.getText2();
        }
        TextView textView = nodeView.findViewById(R.id.layout_cards_selector_card_node_text);
        textView.setText(nameToDisplay);
        checkBox = nodeView.findViewById(R.id.layout_cards_selector_card_node_check);
        checkBox.setOnClickListener(this::cascadeCheckBoxes);
        checkBox.setOnCheckedChangeListener(this::addRemoveCardToSelection);
        showScore();

        return nodeView;
    }

    void toggleCheckbox(boolean isChecked) {
        checkBox.setChecked(isChecked);
        iconTreeItem.isChecked = isChecked;
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(context);
        cardViewModel = ViewModelProviders.of((FragmentActivity) context, viewModelFactory).get(CardViewModel.class);
    }

    private void addRemoveCardToSelection(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            CardsSelection.getInstance().cardList.add(iconTreeItem.card);
        } else {
            CardsSelection.getInstance().cardList.remove(iconTreeItem.card);
        }
        ((TextView) ((Activity) context).findViewById(R.id.activity_cards_revision_selector_nb_selected_cards_text)).setText("Fiches sélectionnées : " + CardsSelection.getInstance().cardList.size());
        if (CardsSelection.getInstance().cardList.isEmpty()) {
            ((Activity) context).findViewById(R.id.activity_cards_revision_selector_review_btn).setEnabled(false);
        } else {
            ((Activity) context).findViewById(R.id.activity_cards_revision_selector_review_btn).setEnabled(true);
        }
    }

    private void cascadeCheckBoxes(View view) {
        iconTreeItem.isChecked = ((CheckBox) view).isChecked();
        if (iconTreeItem.isChecked) {
            boolean areAllChecked = true;
            List<TreeNode> cardNodes = node.getParent().getChildren();
            for (int ic = 0; ic < cardNodes.size(); ic++) {
                if (!((CardHolder) cardNodes.get(ic).getViewHolder()).iconTreeItem.isChecked) {
                    areAllChecked = false;
                    break;
                }
            }
            if (areAllChecked) {
                ((PartHolder) node.getParent().getViewHolder()).toggleCheckbox(true);

                List<TreeNode> partNodes = node.getParent().getParent().getChildren();
                for (int ip = 0; ip < partNodes.size(); ip++) {
                    if (!((PartHolder) partNodes.get(ip).getViewHolder()).getIconTreeItem().isChecked) {
                        areAllChecked = false;
                        break;
                    }
                }
                if (areAllChecked) {
                    ((LessonHolder) node.getParent().getParent().getViewHolder()).toggleCheckbox(true);

                    List<TreeNode> lessonNodes = node.getParent().getParent().getParent().getChildren();
                    for (int il = 0; il < lessonNodes.size(); il++) {
                        if (!((LessonHolder) lessonNodes.get(il).getViewHolder()).getIconTreeItem().isChecked) {
                            areAllChecked = false;
                            break;
                        }
                    }
                    if (areAllChecked) {
                        ((SubjectHolder) node.getParent().getParent().getParent().getViewHolder()).toggleCheckbox(true);
                    }
                }
            }
        } else {
            ((PartHolder) node.getParent().getViewHolder()).toggleCheckbox(false);
            ((LessonHolder) node.getParent().getParent().getViewHolder()).toggleCheckbox(false);
            ((SubjectHolder) node.getParent().getParent().getParent().getViewHolder()).toggleCheckbox(false);
        }
    }

    private void setColorFromPosition(int position) {
        int color = (0 != position % 2) ? R.color.card_1 : R.color.card_2;
        nodeView.setBackgroundResource(color);
    }

    private void showScore() {
        int score = cardViewModel.getCardScore(iconTreeItem.card.getId());
        if (-1 == score) {
            return;
        }
        TextView textView = nodeView.findViewById(R.id.layout_cards_selector_card_node_score_text);
        textView.setText(String.valueOf(score));
        int color;
        if (score < 33) {
            color = R.color.red;
        } else if (score < 66) {
            color = R.color.orange;
        } else {
            color = R.color.green;
        }
        textView.setBackgroundResource(color);
    }

    public static class IconTreeItem {
        Card card;
        boolean isChecked = false;

        public IconTreeItem(Card card) {
            this.card = card;
        }
    }
}
