package com.github.yuang.kt.android_mvvm.utils;

import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class AnimUtils {
    public final static int DURATION = 250;

    /**
     * 箭头旋转动画
     *
     * @param arrow
     * @param isFlag
     */
    public static void rotateArrow(ImageView arrow, boolean isFlag) {
        float srcValue, targetValue;
        if (isFlag) {
            srcValue = 0F;
            targetValue = 270F;
        } else {
            srcValue = 360;
            targetValue = 0F;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(arrow, "rotation", srcValue, targetValue);
        objectAnimator.setDuration(DURATION);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

}
