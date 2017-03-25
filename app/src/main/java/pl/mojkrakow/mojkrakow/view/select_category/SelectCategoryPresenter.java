package pl.mojkrakow.mojkrakow.view.select_category;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;
import pl.mojkrakow.mojkrakow.R;
import pl.mojkrakow.mojkrakow.view.IssueCategory;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class SelectCategoryPresenter implements SelectCategoryAdapter.OnIssueChosenListener {

    SelectCategoryView view;

    @Nullable
    volatile IssueCategory currentCategoryInUse;

    public SelectCategoryPresenter(SelectCategoryView view) {
        this.view = view;
    }

    public void requestCategories() {
        List<IssueCategory> categories = new ArrayList<>();

        categories.add(new IssueCategory("utrudnienia na drodze", R.drawable.category_tree));
        categories.add(new IssueCategory("alarm smogowy", R.drawable.category_smog));
        categories.add(new IssueCategory("nieprawidłowe parkowanie", R.drawable.category_car));
        categories.add(new IssueCategory("uszkodzenie mienia", R.drawable.category_bench));

        view.showCategories(categories);
    }

    @Override
    @DebugLog
    public void onChosen(IssueCategory category) {
        currentCategoryInUse = category;
    }

    @Nullable
    IssueCategory getIssueCategory() {
        return currentCategoryInUse;
    }
}
