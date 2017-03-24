package pl.mojkrakow.mojkrakow.view.additional_details;

import io.reactivex.Observable;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 24.03.2017.
 */

public interface AdditionalDetailsView {


    Observable<Boolean> requestGeolocationPermission();

    void onReceiveLocation(String value);

    void hideProgressBar();
    void showProgressBar();

    void onErrorGetLocation();
}
