package pl.mojkrakow.mojkrakow;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 19.03.2017.
 */

public class CategoryItemImageView extends ImageView implements View.OnClickListener {

    @Nullable
    View.OnClickListener onClickListener;

    boolean isClicked;

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
       this.setScaleX(0.8f);
       this.setScaleY(0.8f);
    }


    public void addOnClickListener(OnClickListener l) {
        this.onClickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
        isClicked = !isClicked;

        if (isClicked) {
            animate().scaleX(1.1f)
                    .scaleY(1.1f)
                    .setDuration(300)
                    .setInterpolator(new BounceInterpolator())
                    .start();
        } else {
            animate().scaleX(0.8f)
                    .scaleY(0.8f)
                    .setDuration(300)
                    .setInterpolator(new BounceInterpolator())
                    .start();

        }
        App.getApp().clicked.onNext(true);
    }
}
