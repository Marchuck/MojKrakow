package pl.mojkrakow.mojkrakow.view.additional_details;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import pl.mojkrakow.mojkrakow.App;
import pl.mojkrakow.mojkrakow.BasePresenter;
import pl.mojkrakow.mojkrakow.KrkObserver;
import pl.mojkrakow.mojkrakow.email.ZikitEmailSender;
import pl.mojkrakow.mojkrakow.utils.PhoneCaller;
import pl.mojkrakow.mojkrakow.view.IssueCategory;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 24.03.2017.
 */

public class AdditionalDetailsPresenter extends BasePresenter {

    public static final String TAG = AdditionalDetailsPresenter.class.getSimpleName();
    boolean emailRequested = false;
    AdditionalDetailsView view;
    GeolocationRepository repository;

    volatile CharSequence currentText;
    Disposable currentCategoryDisposable;
    Disposable geolocationDisposable;
    String currentLocation = "...";
    String currentLocationNumbers = "";
    Uri imageUri;
    PhoneCaller caller;

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

//                            @LocationProvider String provider = LocationManager.NETWORK_PROVIDER;
//                            if (gpsAllowed) {
//                                provider = LocationManager.GPS_PROVIDER;
//                            }
                            view.showProgressBar();
                            return repository.requestLocationUpdates("fused");
                        }
                    }).flatMap(new Function<Location, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(Location location) throws Exception {
                    currentLocationNumbers = location.getLatitude() + ", " + location.getLongitude();
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
                            currentLocation = value;
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

            }
            view.hideProgressBar();

        }
    }

    public boolean canMoveFurther() {
        return true;
    }

    public void onAdditionalTextChanged(CharSequence sequence) {
        currentText = sequence;
    }

    public void callPhone(Activity c, RxPermissions rxPermissions) {
        if (caller != null) {
            caller.dispose();
        }
        caller = new PhoneCaller(c).call(rxPermissions);
    }

    public void sendEmail(Fragment a) {
        emailRequested = true;
        ZikitEmailSender sender = new ZikitEmailSender(a);
        sender.imageUri = imageUri;
        sender.subject = "Mój Kraków utrudnienia na drodze " + currentLocation;

        String aditionalAttachementMention = "";
        if (imageUri != null) {
            aditionalAttachementMention = "W załączniku znajduje się zdjęcie";
        }
        String preparedNumbers = "";
        if (currentLocationNumbers != null && !currentLocationNumbers.isEmpty()) {
            preparedNumbers = "[" + currentLocationNumbers + "]";
        }


        sender.text = "Witam chcę zgłosić utrudnienia na drodze w miejscu "
                + currentLocation +
                ", droga jest nieprzejezdna. "
                + aditionalAttachementMention
                + "\n"
                + preparedNumbers + "\n"
                + "Pozdrawiam";

        sender.send();

    }

    public void setImageUri(Uri uri) {
        imageUri = uri;
    }

    public void onPause() {
        if (currentCategoryDisposable != null) {
            currentCategoryDisposable.dispose();
        }

    }

    public void onResume() {

        if (currentCategoryDisposable != null) {
            currentCategoryDisposable.dispose();
        }
        App.getApp().getCurrentCategoryBus()
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        currentCategoryDisposable = disposable;
                    }
                })
                .subscribeWith(new KrkObserver<IssueCategory>() {
                    @Override
                    public void onNext(IssueCategory value) {
                        view.onReceivedNewCategoryDescription(value.name);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }
}
