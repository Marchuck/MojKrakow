package pl.mojkrakow.mojkrakow;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 24.03.2017.
 */

public abstract class KrkObserver<KRAK> implements Observer<KRAK> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
