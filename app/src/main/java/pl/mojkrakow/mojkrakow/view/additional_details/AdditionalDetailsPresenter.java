package pl.mojkrakow.mojkrakow.view.additional_details;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import pl.mojkrakow.mojkrakow.BasePresenter;
import pl.mojkrakow.mojkrakow.KrkObserver;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 24.03.2017.
 */

public class AdditionalDetailsPresenter extends BasePresenter {

    public static final String TAG = AdditionalDetailsPresenter.class.getSimpleName();

    AdditionalDetailsView view;
    GeolocationRepository repository;

    volatile CharSequence currentText;

    Disposable geolocationDisposable;

    public AdditionalDetailsPresenter(AdditionalDetailsView view, GeolocationRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void onSwitchChanged(boolean newValue) {
        if (newValue) {
            view.requestGeolocationPermission()
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            geolocationDisposable = disposable;
                        }
                    })
                    .flatMap(new Function<Boolean, ObservableSource<Location>>() {
                        @Override
                        public ObservableSource<Location> apply(Boolean gpsAllowed) throws Exception {

                            @LocationProvider String provider = LocationManager.NETWORK_PROVIDER;
                            if (gpsAllowed) {
                                provider = LocationManager.GPS_PROVIDER;
                            }
                            view.showProgressBar();
                            return repository.requestLocationUpdates(provider).take(1);
                        }
                    }).flatMap(new Function<Location, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(Location location) throws Exception {
                    return repository.readableAddress(location);
                }
            }).compose(this.<String>applySchedulers())
                    .doFinally(new Action() {
                        @Override
                        public void run() throws Exception {
                            view.hideProgressBar();
                        }
                    })
                    .subscribeWith(new KrkObserver<String>() {
                        @Override
                        public void onNext(String value) {
                            view.onReceiveLocation(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.onErrorGetLocation();
                            Log.e(TAG, "onError: ", e);
                        }
                    });
        } else {
            if (geolocationDisposable != null) {
                geolocationDisposable.dispose();

            } view.hideProgressBar();

        }
    }

    public boolean canMoveFurther() {
        return false;
    }

    public void onAdditionalTextChanged(CharSequence sequence) {
        currentText = sequence;
    }
}
