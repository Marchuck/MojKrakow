package pl.mojkrakow.mojkrakow.view;

import android.os.Bundle;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import pl.mojkrakow.mojkrakow.view.select_category.SelectCategorySlide;
import pl.mojkrakow.mojkrakow.view.send_event.SendEventSlide;

public class MojKrakowActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SelectCategorySlide());
        addSlide(new SelectCategorySlide());
        addSlide(new SendEventSlide());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
