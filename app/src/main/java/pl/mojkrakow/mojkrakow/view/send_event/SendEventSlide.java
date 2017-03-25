package pl.mojkrakow.mojkrakow.view.send_event;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agency.tango.materialintroscreen.SlideFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
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
        (getActivity()).onBackPressed();
        (getActivity()).onBackPressed();
    }

    @Override
    public int backgroundColor() {
        return R.color.colorAccent;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorAccentDarker;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.send_event_slide, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
