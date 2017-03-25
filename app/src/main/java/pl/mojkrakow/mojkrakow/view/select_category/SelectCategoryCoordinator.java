package pl.mojkrakow.mojkrakow.view.select_category;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.coordinators.Coordinator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pl.mojkrakow.mojkrakow.R;
import pl.mojkrakow.mojkrakow.view.IssueCategory;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class SelectCategoryCoordinator extends Coordinator implements SelectCategoryView {

    Unbinder unbinder;
    SelectCategoryPresenter presenter;

    @BindView(R.id.slide_select_category_recyclerview)
    RecyclerView recyclerView;
    SelectCategoryAdapter adapter;

    public SelectCategoryCoordinator() {

    }

    @Override
    public void attach(View view) {
        super.attach(view);
        unbinder = ButterKnife.bind(this, view);
        Context ctx = view.getContext();

        recyclerView.setLayoutManager(new GridLayoutManager(ctx, 2));
        adapter = new SelectCategoryAdapter();
        recyclerView.setAdapter(adapter);
        adapter.listener = presenter;
        presenter = new SelectCategoryPresenter(this);
        presenter.requestCategories();
    }


    @Override
    public void detach(View view) {
        super.detach(view);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void showCategories(List<IssueCategory> categoryList) {
        adapter.refreshDataset(categoryList);
    }

    @Nullable
    IssueCategory getSelectedIssueCategory() {
        return presenter.getIssueCategory();
    }
}
