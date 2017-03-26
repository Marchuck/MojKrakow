package pl.mojkrakow.mojkrakow;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import pl.mojkrakow.mojkrakow.view.IssueCategory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 18.03.2017.
 */

public class App extends Application {

    private static Context CONTEXT;

    public Subject<Uri> imageUriSubject = BehaviorSubject.create();
    public Subject<Boolean> clicked = BehaviorSubject.create();
    public Subject<IssueCategory> issueCategorySubject = BehaviorSubject.create();
    public Uri data;

    public static App getApp() {
        return (App) CONTEXT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this.getApplicationContext();
        installCalligraphy();
    }

    void installCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("caviar_dreams.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public Subject<IssueCategory> getCurrentCategoryBus() {
        return issueCategorySubject;
    }
}
