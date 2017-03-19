package pl.mojkrakow.mojkrakow;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
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

    @BindView(R.id.afterpick_image)
    ImageView imageView;

    public static AfterPickedImageFragment newInstance() {
        AfterPickedImageFragment fragment = new AfterPickedImageFragment();

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
        View view = inflater.inflate(R.layout.fragment_picked_image, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


    @Override
    @DebugLog
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateImage(getMainActivity().data);

        getMainActivity().subject.subscribe(new Consumer<Object>() {
            @Override
            @DebugLog
            public void accept(Object uri) throws Exception {
                if (uri instanceof Uri)
                    updateImage((Uri) uri);

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

    @DebugLog
    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
