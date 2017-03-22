package pl.mojkrakow.mojkrakow.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agency.tango.materialintroscreen.SlideFragment;
import pl.mojkrakow.mojkrakow.R;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class AdditionalDetailsSlide extends SlideFragment {

    @Override
    public int backgroundColor() {
        return getResources().getColor(R.color.colorAccent);
    }

    @Override
    public boolean canMoveFurther() {
        return false;
    }

    @Override
    public int buttonsColor() {
        return getResources().getColor(R.color.colorAccentDarker);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_additional_details, container, false);
        return view;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return super.cantMoveFurtherErrorMessage();
    }
}
