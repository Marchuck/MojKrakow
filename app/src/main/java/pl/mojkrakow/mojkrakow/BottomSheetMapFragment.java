package pl.mojkrakow.mojkrakow;

import android.Manifest;
import android.app.Dialog;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import pl.mojkrakow.mojkrakow.utils.LockableBottomSheetBehavior;

import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;

/**
 * Created by Lukasz Marczak on 16.03.17.
 * lukasz@next42.net
 */

public class BottomSheetMapFragment extends BottomSheetDialogFragment {

    public static final String TAG = BottomSheetMapFragment.class.getSimpleName();

    @Nullable
    View contentView;

    BottomSheetBehavior bottomSheetBehavior;

    final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            Log.d(TAG, "onStateChanged: " + (newState));
            if (newState == STATE_HIDDEN) {
                dismiss();
            }
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                if (bottomSheetBehavior instanceof LockableBottomSheetBehavior) {
                    ((LockableBottomSheetBehavior) bottomSheetBehavior).setLocked(true);
                }
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @BindView(R.id.bottom_sheet_test_map)
    CoordinatorLayout coordinatorLayout;

    @OnClick(R.id.bottom_sheet_test_map_close_layout)
    void onClose() {
        if (bottomSheetBehavior != null) {
            mBottomSheetBehaviorCallback.onStateChanged(null, STATE_HIDDEN);
        }
    }

    @Override
    @DebugLog
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        restoreState();
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
        setupSheetBehavior();

    }

    private void restoreState() {
        if (contentView != null) {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
        }
        try {
            contentView = View.inflate(getContext(), R.layout.bottom_sheet_test_map, null);
        } catch (InflateException x) {
            Log.e(TAG, "setupDialog: already attached");
        }
    }

    private void setupSheetBehavior() {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                ((View) contentView.getParent()).getLayoutParams();
        final CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            this.bottomSheetBehavior = ((BottomSheetBehavior) behavior);
            bottomSheetBehavior.setHideable(false);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}
