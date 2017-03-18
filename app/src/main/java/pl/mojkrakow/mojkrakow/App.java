package pl.mojkrakow.mojkrakow;

import android.app.Application;
import android.content.Context;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 18.03.2017.
 */

public class App extends Application {

    private static Context CONTEXT;

    public static App getApp() {
        return (App) CONTEXT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this.getApplicationContext();
    }
}
