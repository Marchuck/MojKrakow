package pl.mojkrakow.mojkrakow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
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
import pl.mojkrakow.mojkrakow.takephoto.CameraActivity;
import pl.mojkrakow.mojkrakow.utils.LockableBottomSheetBehavior;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CAMERA = 2137;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    RxPermissions rxPermissions;

    RxPermissions getRxPermissions() {
        if (rxPermissions == null) rxPermissions = new RxPermissions(this);
        return rxPermissions;
    }


    @BindView(R.id.bottom_sheet)
    NestedScrollView nestedScrollView;

    BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.image)
    ImageView flawImage;

    @DebugLog
    @OnClick(R.id.pickImage)
    void onImagePick() {

        getRxPermissions()
                .request(CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    @DebugLog
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.e(TAG, "accept: ");
                        Intent startCustomCameraIntent = new Intent(MainActivity.this, CameraActivity.class);
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

    @DebugLog
    void setupBottomSheetView() {
        bottomSheetBehavior = BottomSheetBehavior.from(nestedScrollView);
        bottomSheetBehavior.setPeekHeight(0);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Log.d(TAG, "onStateChanged: " + (newState));
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (bottomSheetBehavior instanceof LockableBottomSheetBehavior) {
                        ((LockableBottomSheetBehavior) bottomSheetBehavior).setLocked(true);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupBottomSheetView();
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    @DebugLog
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CAMERA) {
            onFlawImageUpdated(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @DebugLog
    void onFlawImageUpdated(Intent data) {
        if (data == null) return;
        Uri photoUri = data.getData();
        Log.d(TAG, "onActivityResult: loading photo");
        updateImage(photoUri);
    }


    private void updateImage(Uri recentUri) {
        if (recentUri != null) {
            Picasso.with(this)
                    .load(recentUri)
                    .fit()
                    .into(flawImage);
        } else {
//            Picasso.with(this)
//                    .load(R.drawable.athlete)
//                    .fit()
//                    .into(flawImage);
        }
    }


    void showTagsToPick(){
        BottomSheetMapFragment bottomSheetDialogFragment = new BottomSheetMapFragment();
        bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
    }
}
