package pl.mojkrakow.mojkrakow.view.select_category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.coordinators.Coordinator;
import com.squareup.coordinators.CoordinatorProvider;
import com.squareup.coordinators.Coordinators;

import agency.tango.materialintroscreen.SlideFragment;
import pl.mojkrakow.mojkrakow.R;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class SelectCategorySlide extends SlideFragment {

    SelectCategoryCoordinator coordinator;

    @Override
    public int backgroundColor() {
        return getResources().getColor(R.color.colorAccent);
    }

    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public int buttonsColor() {
        return getResources().getColor(R.color.colorAccentDarker);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.slide_select_category, container, false);
        Coordinators.bind(view.findViewById(R.id.slide_select_category), new CoordinatorProvider() {
            @Nullable
            @Override
            public Coordinator provideCoordinator(View view) {
                coordinator = new SelectCategoryCoordinator();
                return coordinator;
            }
        });
        return view;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Wybierz kategorię!";
    }
}
