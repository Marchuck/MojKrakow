package pl.mojkrakow.mojkrakow.view.additional_details;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;

import agency.tango.materialintroscreen.SlideFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import pl.mojkrakow.mojkrakow.PickImageFragment;
import pl.mojkrakow.mojkrakow.R;
import pl.mojkrakow.mojkrakow.takephoto.CameraActivity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static pl.mojkrakow.mojkrakow.MainActivity.REQUEST_CAMERA;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class AdditionalDetailsSlide extends SlideFragment implements AdditionalDetailsView {

    public static final String TAG = AdditionalDetailsSlide.class.getSimpleName();


    GeolocationRepository repository;
    RxPermissions permissions;
    AdditionalDetailsPresenter presenter;

    @OnCheckedChanged(R.id.geolocation)
    void onSwitchChecked(boolean newValue) {

        presenter.onSwitchChanged(newValue);
    }

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.address_summary)
    TextView addressSummary;

    @OnTextChanged(R.id.additional_text)
    void additionalTextChanged(CharSequence sequence) {
        if (presenter != null)
            presenter.onAdditionalTextChanged(sequence);
    }

    @BindView(R.id.pick_image)
    Button pickImageButton;

    @BindView(R.id.image)
    ImageView image;

    @OnClick(R.id.pick_image)
    void onImagePick() {
        getRxPermissions()
                .request(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    @DebugLog
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "accept: ");
                        Intent startCustomCameraIntent = new Intent(getActivity(), CameraActivity.class);
                        startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    @DebugLog
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ");
                    }
                });
    }

    @Override
    public int backgroundColor() {
        return getResources().getColor(R.color.colorAccent);
    }

    @Override
    public boolean canMoveFurther() {
        return presenter.canMoveFurther();
    }

    @Override
    public int buttonsColor() {
        return getResources().getColor(R.color.colorAccentDarker);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_additional_details, container, false);
        ButterKnife.bind(this, view);
        repository = new GeolocationRepository(getActivity());
        presenter = new AdditionalDetailsPresenter(this, repository);
        return view;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Uzupełnij brakujące informacje";
    }

    @Override
    public Observable<Boolean> requestGeolocationPermission() {
        return getRxPermissions().request(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public void onReceiveLocation(String value) {
        addressSummary.setText(value);
    }

    @Override
    public void hideProgressBar() {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onErrorGetLocation() {
        progressBar.setVisibility(View.VISIBLE);
        if (getView() != null)
            Snackbar.make(getView(), "Ups! Spróbuj ponownie później", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_CAMERA) {
            final Uri uri = data.getData();
            if (uri != null) {
                image.setScaleY(0.0f);
                image.setVisibility(View.VISIBLE);

                pickImageButton.animate()
                        .alpha(0)
                        .setDuration(100)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                pickImageButton.setVisibility(View.GONE);
                                pickImageButton.setAlpha(1);
                            }
                        })
                        .start();

                image.animate().setDuration(400)
                        .scaleY(1)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.with(getActivity())
                                        .load(uri)
                                        .into(image);
                            }
                        }).start();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private RxPermissions getRxPermissions() {
        if (permissions == null) permissions = new RxPermissions(getActivity());
        return permissions;
    }
}
