package pl.mojkrakow.mojkrakow.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import hugo.weaving.DebugLog;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 26.03.2017.
 */

public class PhoneCaller {

    public static final String TAG = PhoneCaller.class.getSimpleName();

    public static final String ZIKIT_EMAIL = "sekretariat@zikit.krakow.pl";
    //    http://krakow.onet.pl/masz-dosc-dziurawych-drog-w-krakowie-dzwon-do-zikit/y939h
    public static final String ZIKIT_NUMBER = "126167555";

    private Activity c;
    private String phoneNumber = ZIKIT_NUMBER;
    private Disposable disposable;

    public PhoneCaller(Activity c) {
        this.c = c;
    }

    public PhoneCaller setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        c = null;
        phoneNumber = null;
    }

    public PhoneCaller call(RxPermissions rxPermissions) {

        rxPermissions.request(Manifest.permission.CALL_PHONE)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable d) throws Exception {
                        disposable = d;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) call(phoneNumber);
                        else {
                            Toast.makeText(c, "Nie można wykonać połączenia - Nie nadano uprawnień", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        return this;
    }

    @DebugLog
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));
        try {
            c.startActivity(intent);
        } catch (SecurityException x) {
            Log.e(TAG, "call: ", x);
        }
    }

}
