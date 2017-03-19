package pl.mojkrakow.mojkrakow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //ButterKnife.bind(this,view);
        return view;
    }

}
