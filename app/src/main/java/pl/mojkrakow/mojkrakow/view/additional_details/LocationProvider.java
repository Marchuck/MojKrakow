package pl.mojkrakow.mojkrakow.view.additional_details;

import android.location.LocationManager;
import android.support.annotation.StringDef;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 24.03.2017.
 */

@StringDef(value = {
        LocationManager.GPS_PROVIDER,
        LocationManager.NETWORK_PROVIDER,
        LocationManager.PASSIVE_PROVIDER
})
public @interface LocationProvider {
}
