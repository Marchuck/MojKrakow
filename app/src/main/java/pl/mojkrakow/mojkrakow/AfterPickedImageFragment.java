package pl.mojkrakow.mojkrakow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * MojKrakow
 * <p>
 * Created by lukasz
 * <p>
 * Since 19.03.2017
 */
public class AfterPickedImageFragment extends Fragment {
    public static final String TAG = AfterPickedImageFragment.class.getSimpleName();

    public AfterPickedImageFragment() {
    }

    @BindView(R.id.image)
    ImageView imageView;

    public static AfterPickedImageFragment newInstance() {
        AfterPickedImageFragment fragment = new AfterPickedImageFragment();
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
        View view = inflater.inflate(R.layout.fragment_picked_image, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


    @Override
    @DebugLog
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        App.getApp().imageUriSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uri) throws Exception {
                        updateImage(uri);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: ", throwable);
                    }
                });
    }

    private void updateImage(Uri recentUri) {
        if (recentUri != null) {
            Picasso.with(getActivity())
                    .load(recentUri)
                    .fit()
                    .into(imageView);
        } else {
//            Picasso.with(this)
//                    .load(R.drawable.athlete)
//                    .fit()
//                    .into(flawImage);
        }
    }
}
