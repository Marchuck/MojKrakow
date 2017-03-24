package pl.mojkrakow.mojkrakow.view;

import android.support.annotation.DrawableRes;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

public class IssueCategory {

    public final String name;

    @DrawableRes
    public final int res;

    public volatile boolean selected;

    public IssueCategory(String name, int res) {
        this.name = name;
        this.res = res;
    }

    @Override
    public String toString() {
        return name;

    }
}
