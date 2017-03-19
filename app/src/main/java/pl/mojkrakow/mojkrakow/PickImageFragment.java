package pl.mojkrakow.mojkrakow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.aspectj.lang.annotation.After;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.reactivex.functions.Consumer;
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
public class PickImageFragment extends Fragment {
    public static final String TAG = PickImageFragment.class.getSimpleName();

    MainActivity getMainActivity() {
        return (MainActivity) getActivity();
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
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_CAMERA) {
            App.getApp().imageUriSubject.onNext(data.getData());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getMainActivity().switchToFragment(AfterPickedImageFragment.newInstance());
                }
            }, 200);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
