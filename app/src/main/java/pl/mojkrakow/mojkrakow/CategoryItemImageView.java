package pl.mojkrakow.mojkrakow;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import hugo.weaving.DebugLog;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 19.03.2017.
 */

public class CategoryItemImageView extends ImageView implements View.OnClickListener {

    public static final float BIG = 1.1f;
    public static final float SMALL = 0.8f;

    @Nullable
    View.OnClickListener onClickListener;

    public volatile boolean isClicked;

    public CategoryItemImageView(Context context) {
        super(context);
        init();
    }

    public CategoryItemImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CategoryItemImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = 21)
    public CategoryItemImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setScaleX(SMALL);
        setScaleY(SMALL);

    }

    @DebugLog
    public void scaleBoth(float scale) {
        animate().setDuration(300)
                .scaleY(scale).scaleX(scale)
                .setInterpolator(new BounceInterpolator())
                .start();
    }

    public void addOnClickListener(OnClickListener l) {
        this.onClickListener = l;
    }

    @Override
    public void onClick(View v) {

        isClicked = !isClicked;

        if (isClicked) {
            animate().scaleX(BIG)
                    .scaleY(BIG)
                    .setDuration(300)
                    .setInterpolator(new BounceInterpolator())
                    .start();
        } else {
            animate().scaleX(SMALL)
                    .scaleY(SMALL)
                    .setDuration(300)
                    .setInterpolator(new BounceInterpolator())
                    .start();

        }
//        App.getApp().clicked.onNext(true);
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }
}
