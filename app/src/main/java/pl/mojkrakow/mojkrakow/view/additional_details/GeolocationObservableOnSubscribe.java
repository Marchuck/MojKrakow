package pl.mojkrakow.mojkrakow.view.additional_details;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

import hugo.weaving.DebugLog;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 26.03.2017.
 */

public class GeolocationObservableOnSubscribe implements ObservableOnSubscribe<Location>,
        LocationListener, Disposable, Cancellable, Runnable {

    public static final String TAG = GeolocationObservableOnSubscribe.class.getSimpleName();
    AtomicBoolean disposed = new AtomicBoolean(false);
    ObservableEmitter<Location> e;
    Handler handler;
    LocationManager locationManager;

    public GeolocationObservableOnSubscribe(LocationManager mgr) {
        locationManager = mgr;
    }

    @Override
    public void subscribe(ObservableEmitter<Location> _e) throws Exception {
        this.e = _e;
        this.e.setDisposable(this);
        this.e.setCancellable(this);
        handler = new Handler(Looper.getMainLooper());
        handler.post(this);
    }

    @Override
    @DebugLog
    public void onLocationChanged(Location location) {

        if (!e.isDisposed() && location != null) {
            e.onNext(location);
        }
    }

    @Override
    @DebugLog
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    @DebugLog
    public void onProviderEnabled(String provider) {
    }

    @Override
    @DebugLog
    public void onProviderDisabled(String provider) {
    }

    @Override
    @DebugLog
    public void dispose() {
        if (isDisposed()) {
            return;
        }

        disposed.set(true);
        handler.removeCallbacks(this);
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException x) {
            Log.e(TAG, "dispose: error", x);
        }

    }

    @Override
    @DebugLog
    public boolean isDisposed() {
        return disposed.get();
    }

    @Override
    @DebugLog
    public void cancel() throws Exception {
        dispose();
    }

    @Override
    public void run() {
        try {
            if (!e.isDisposed())
                locationManager.requestLocationUpdates("fused", 0, 0, GeolocationObservableOnSubscribe.this);
        } catch (SecurityException calledWhenPermsiionsNotGranted) {
            GeolocationObservableOnSubscribe.this.e.onError(calledWhenPermsiionsNotGranted);
        }
    }
}