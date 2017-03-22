package pl.mojkrakow.mojkrakow;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 19.03.2017.
 */

public interface BackPressable {

    void onBackButtonPressed();

    boolean shouldExitApp();
}
