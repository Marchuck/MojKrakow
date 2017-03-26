package pl.mojkrakow.mojkrakow;

import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.VisibleForTesting;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import pl.mojkrakow.mojkrakow.view.additional_details.AdditionalDetailsPresenter;
import pl.mojkrakow.mojkrakow.view.additional_details.AdditionalDetailsView;
import pl.mojkrakow.mojkrakow.view.additional_details.GeolocationRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 25.03.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class AdditionalDetailsSlideTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    AdditionalDetailsView view;

    @Mock
    GeolocationRepository repository;

    AdditionalDetailsPresenter presenter;

    @Before
    public void setUp() {
        presenter = new AdditionalDetailsPresenter(view, repository);
    }

    @Test
    public void shouldSwitchLocationOff() {

        presenter.enableTestMode();
        presenter.onSwitchChanged(false);

        verify(view).hideProgressBar();
    }

    @Test
    public void shouldSwitchLocationOn() {

        //when
        Location location = new Location("network");
        location.setLatitude(50);
        location.setLongitude(19);
        Mockito.when(repository.requestLocationUpdates())
                .thenReturn(Observable.just(location));

        Mockito.when(repository.readableAddress(any(Location.class)))
                .thenReturn(Observable.just("POLEN"));

        Mockito.when(view.requestGeolocationPermission()).thenReturn(Observable.just(false));

        //given
        presenter.enableTestMode();
        presenter.onSwitchChanged(true);

        //then
        InOrder inOrder = inOrder(view, view, view, view);
        inOrder.verify(view).requestGeolocationPermission();
        inOrder.verify(view).showProgressBar();
        inOrder.verify(view).onReceiveLocation("POLEN");
        inOrder.verify(view).hideProgressBar();
    }

    @Test
    public void shouldFailGetLastLocation() {

        //when
        Location location = new Location("network");
        location.setLatitude(50);
        location.setLongitude(19);
        Mockito.when(repository.requestLocationUpdates())
                .thenReturn(Observable.<Location>error(new Throwable("error")));

        Mockito.when(repository.readableAddress(any(Location.class)))
                .thenReturn(Observable.just("POLEN"));

        Mockito.when(view.requestGeolocationPermission()).thenReturn(Observable.just(false));

        //given
        presenter.enableTestMode();
        presenter.onSwitchChanged(true);

        //then
        InOrder inOrder = inOrder(view, view, view, view);
        inOrder.verify(view).requestGeolocationPermission();
        inOrder.verify(view).showProgressBar();
        inOrder.verify(view).onErrorGetLocation();
        inOrder.verify(view).hideProgressBar();
    }
}