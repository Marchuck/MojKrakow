package pl.mojkrakow.mojkrakow.utils;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lukasz Marczak on 16.03.17.
 */

public class LockableBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {

    public static final String BEHAVIOR = "evalu.com.evalu.utils.behaviours.LockableBottomSheetBehavior";


    private boolean mLocked = false;

    public LockableBottomSheetBehavior() {
    }

    public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLocked(boolean locked) {
        mLocked = locked;
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
//        boolean handled = false;
//
//        if (!mLocked) {
//            handled = super.onInterceptTouchEvent(parent, child, event);
//        }
//
//        return handled;
//    }
//
//    @Override
//    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
//        boolean handled = false;
//
//        if (!mLocked) {
//            handled = super.onTouchEvent(parent, child, event);
//        }
//
//        return handled;
//    }
//
//    @Override
//    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
//        boolean handled = false;
//
//        if (!mLocked) {
//            handled = super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
//        }
//
//        return handled;
//    }
//
//    @Override
//    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {
//        if (!mLocked) {
//            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
//        }
//    }
//
//    @Override
//    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
//        if (!mLocked) {
//            super.onStopNestedScroll(coordinatorLayout, child, target);
//        }
//    }
//
//    @Override
//    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
//        boolean handled = false;
//
//        if (!mLocked) {
//            handled = super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
//        }
//
//        return handled;
//
//    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child, View directTargetChild, View target, int nestedScrollAxes) {
        return false;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed) {}

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {}

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return false;
    }
}
