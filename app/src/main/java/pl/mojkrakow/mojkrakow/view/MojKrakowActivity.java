package pl.mojkrakow.mojkrakow.view;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.util.Log;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import pl.mojkrakow.mojkrakow.view.additional_details.AdditionalDetailsSlide;
import pl.mojkrakow.mojkrakow.view.select_category.SelectCategorySlide;
import pl.mojkrakow.mojkrakow.view.send_event.SendEventSlide;

public class MojKrakowActivity extends MaterialIntroActivity {

    public static final String TAG = MojKrakowActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });
        addSlide(new SelectCategorySlide());
        addSlide(new AdditionalDetailsSlide());
        addSlide(new SendEventSlide());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
