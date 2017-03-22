package pl.mojkrakow.mojkrakow.view.send_event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agency.tango.materialintroscreen.SlideFragment;
import butterknife.BindView;
import butterknife.OnClick;
import pl.mojkrakow.mojkrakow.R;
import pl.mojkrakow.mojkrakow.view.MojKrakowActivity;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class SendEventSlide extends SlideFragment {

    @OnClick(R.id.okButton)
    void onClicked() {
        ((MojKrakowActivity) getActivity()).onBackPressed();
    }

    @Override
    public int backgroundColor() {
        return super.backgroundColor();
    }

    @Override
    public boolean canMoveFurther() {
        return super.canMoveFurther();
    }

    @Override
    public int buttonsColor() {
        return super.buttonsColor();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_event_slide, container, false);
        return view;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return super.cantMoveFurtherErrorMessage();
    }
}
