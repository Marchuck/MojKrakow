package pl.mojkrakow.mojkrakow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.observable.ObservableGroupBy;
import pl.mojkrakow.mojkrakow.takephoto.CameraActivity;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static pl.mojkrakow.mojkrakow.MainActivity.REQUEST_CAMERA;

/**
 * MojKrakow
 * <p>
 * Created by lukasz
 * <p>
 * Since 19.03.2017
 */
public class PickImageFragment extends Fragment implements BackPressable {

    public static final String TAG = PickImageFragment.class.getSimpleName();

    @BindView(R.id.afterpick_image)
    ImageView imageView;

    @BindView(R.id.afterpick)
    ViewGroup afterPick;

    @BindView(R.id.reportEventLayout)
    ViewGroup reportEventLayout;

    @BindView(R.id.sentDone)
    ViewGroup sentDone;

    @DebugLog
    @OnClick(R.id.okButton)
    void onSent() {
        sentDone.setVisibility(View.GONE);
        afterPick.setVisibility(View.VISIBLE);
        reportEventLayout.setVisibility(View.VISIBLE);
        onBackButtonPressed();
    }

    @OnClick(R.id.send)
    void onSend() {
        sentDone.setVisibility(View.VISIBLE);
        afterPick.setVisibility(View.VISIBLE);
        reportEventLayout.setVisibility(View.VISIBLE);
    }

    @DebugLog
    @OnClick(R.id.camera)
    void onImagePick() {

        getMainActivity().getRxPermissions()
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

    public PickImageFragment() {
    }

    public static PickImageFragment newInstance() {
        PickImageFragment fragment = new PickImageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        getMainActivity().setBackPressedCallback(this);


        App.getApp().clicked.subscribe(new Observer<Boolean>() {
            @Override
            @DebugLog
            public void onSubscribe(Disposable d) {

            }

            @DebugLog
            @Override
            public void onNext(Boolean value) {
                onSend();
            }

            @Override
            @DebugLog
            public void onError(Throwable e) {

            }

            @Override
            @DebugLog
            public void onComplete() {

            }
        });
        return view;
    }

    @Override
    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CAMERA) {
            final Uri uri = data.getData();
            switchToAfterPicked(uri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @DebugLog
    void switchToAfterPicked(final Uri uri) {

        reportEventLayout.animate()
                .alpha(0)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    @DebugLog
                    public void run() {
                        reportEventLayout.setVisibility(View.GONE);

                        afterPick.setVisibility(View.VISIBLE);
                        afterPick.setAlpha(0);
                        afterPick.animate()
                                .alpha(1)
                                .setDuration(200)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (uri != null) {
                                            Picasso.with(getActivity())
                                                    .load(uri)
                                                    .fit()
                                                    .into(imageView);
                                        }
                                    }
                                })
                                .start();
                    }
                }).start();
    }

    @Override
    @DebugLog
    public void onBackButtonPressed() {
        afterPick.animate()
                .alpha(0)
                .setDuration(200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        afterPick.setVisibility(View.GONE);

                        reportEventLayout.setVisibility(View.VISIBLE);
                        reportEventLayout.setAlpha(0);
                        reportEventLayout.animate()
                                .alpha(1)
                                .setDuration(200)
                                .start();
                    }
                }).start();
    }

    @Override
    public boolean shouldExitApp() {
        return reportEventLayout.getVisibility() == View.VISIBLE;
    }

    MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
