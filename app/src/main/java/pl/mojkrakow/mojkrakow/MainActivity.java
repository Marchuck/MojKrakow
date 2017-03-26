package pl.mojkrakow.mojkrakow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import pl.mojkrakow.mojkrakow.takephoto.CameraActivity;
import pl.mojkrakow.mojkrakow.utils.LockableBottomSheetBehavior;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CAMERA = 2137;
    public static final int EMAIL_SEND = 3000;
    public Uri data;

    public Subject<Object> subject = ReplaySubject.create();
    private BackPressable backPressedCallback;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_real);
//        ButterKnife.bind(this);
//        setupBottomSheetView();
//        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        switchToFragment(PickImageFragment.newInstance());
    }

    public void switchToFragment(Fragment f) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.placeholder, f)
                .commitAllowingStateLoss();
    }

    public RxPermissions getRxPermissions() {
        return new RxPermissions(this);
    }


//
//    void showTagsToPick() {
//        BottomSheetFragment bottomSheetDialogFragment = new BottomSheetFragment();
//        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//    }

    @DebugLog
    @Override
    public void onBackPressed() {
        if (backPressedCallback != null) {
            if (!backPressedCallback.shouldExitApp()) {
                backPressedCallback.onBackButtonPressed();
                return;
            }
        }
        super.onBackPressed();

    }

    public void setBackPressedCallback(PickImageFragment backPressedCallback) {
        this.backPressedCallback = backPressedCallback;
    }

}
