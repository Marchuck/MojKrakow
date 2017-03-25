package pl.mojkrakow.mojkrakow.view.additional_details;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import hugo.weaving.DebugLog;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import static io.reactivex.Maybe.error;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 24.03.2017.
 */

public class GeolocationRepository {

    Geocoder geocoder;
    LocationManager locationManager;
    Handler handler;

    AtomicBoolean requested = new AtomicBoolean(false);

    public GeolocationRepository(Context c) {

        geocoder = new Geocoder(c, Locale.getDefault());
        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        handler = new Handler(Looper.getMainLooper());
    }

    @DebugLog
    Observable<Address> getAddress(final Location location) {
        if (location == null)
            return Observable.error(new NullPointerException("location cannot be null!!!"));

        return Observable.fromCallable(new Callable<Address>() {
            @Override
            public Address call() throws Exception {

                double lat = location.getLatitude();
                double lon = location.getLongitude();

                try {
                    List<Address> addr = geocoder.getFromLocation(lat, lon, 1);
                    if (addr == null || addr.isEmpty()) {
                        throw new NoSuchFieldException("null");
                    }
                    return addr.get(0);
                } catch (IOException x) {
                    throw new IOException(x);
                }
            }
        });
    }

    @DebugLog
    public Observable<String> readableAddress(@NonNull final Location location) {
        return getAddress(location).map(new Function<Address, String>() {
            @Override
            public String apply(@NonNull Address address) throws Exception {

                StringBuilder s = new StringBuilder();
                int maLines = address.getMaxAddressLineIndex();
                if (maLines > 2) {
                    return s.append(address.getAddressLine(2))
                            .append(" ")
                            .append(address.getAddressLine(1))
                            .append(" ")
                            .append(address.getAddressLine(0))
                            .toString();
                } else if (maLines > 1) {
                    return s.append(address.getAddressLine(1))
                            .append(" ")
                            .append(address.getAddressLine(0))
                            .toString();
                } else if (maLines == 1) {
                    return s.append(address.getAddressLine(1))
                            .append(" ")
                            .append(address.getAddressLine(0))
                            .toString();
                }
                throw new NoSuchFieldException("null");
            }
        }).onErrorReturn(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) throws Exception {
                if (throwable instanceof NoSuchFieldException) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    return "(" + lat + ", " + lon + ")";
                } else return "?";
            }
        });
    }

    public Observable<Location> requestLocationUpdates(final  String provider) {


        return Observable.create(new ObservableOnSubscribe<Location>() {

            AtomicBoolean disposed = new AtomicBoolean(false);

            @Override
            public void subscribe(final ObservableEmitter<Location> e) throws Exception {

                final LocationListener ll = new LocationListener() {
                    @Override
                    @DebugLog
                    public void onLocationChanged(Location location) {
                        e.onNext(location);

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
                };

                e.setDisposable(new Disposable() {
                    @Override
                    @DebugLog
                    public void dispose() {
                        disposed.set(true);
                        try {
                            locationManager.removeUpdates(ll);
                            requested.set(false);
                        } catch (SecurityException x) {
                            e.onError(x);
                        }
                    }

                    @Override
                    @DebugLog
                    public boolean isDisposed() {
                        return disposed.get();
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!requested.get()) {
                                locationManager.requestLocationUpdates(provider, 0, 0, ll);
                                requested.set(true);
                            }
                        } catch (SecurityException x) {
                            e.onError(x);
                        }
                    }
                });
            }
        });
    }

}
