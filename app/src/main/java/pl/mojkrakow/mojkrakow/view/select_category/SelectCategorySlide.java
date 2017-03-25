package pl.mojkrakow.mojkrakow.view.select_category;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;

import java.util.List;

import agency.tango.materialintroscreen.SlideFragment;
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

public class SelectCategorySlide extends SlideFragment implements SelectCategoryView{
    Unbinder unbinder;
    SelectCategoryPresenter presenter;

    @BindView(R.id.slide_select_category_recyclerview)
    RecyclerView recyclerView;
    SelectCategoryAdapter adapter;

    @Override
    public int backgroundColor() {
        return (R.color.colorAccent);
    }

    @Override
    public int buttonsColor() {
        return (R.color.colorAccentDarker);
    }

    @Override
    public boolean canMoveFurther() {
        return presenter.getIssueCategory() != null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle $) {

        View view = inflater.inflate(R.layout.slide_select_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new SelectCategoryPresenter(this);

        Context ctx = view.getContext();
        recyclerView.setLayoutManager(new GridLayoutManager(ctx, 2));
        adapter = new SelectCategoryAdapter();
        recyclerView.setAdapter(adapter);
        adapter.listener = presenter;
        presenter.requestCategories();
//        Coordinators.bind(view.findViewById(R.id.slide_select_category), new CoordinatorProvider() {
//            @Nullable
//            @Override
//            public Coordinator provideCoordinator(View view) {
//                coordinator = new SelectCategoryCoordinator();
//                return coordinator;
//            }
//        });
        return view;
    }

    @Override
    public void showCategories(List<IssueCategory> categoryList) {
        adapter.refreshDataset(categoryList);
    }
    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Wybierz kategorię!";
    }
}
