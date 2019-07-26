package view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 按钮点击动画
 */

public class TouchAnimeTextViewTwo extends AppCompatTextView {
    private boolean isClick=true;

    public TouchAnimeTextViewTwo(Context context) {
        super(context);
    }

    public TouchAnimeTextViewTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (!isEnabled())
            return true;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1, 0.9f);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1, 0.9f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
                animatorSet.setDuration(100);
                animatorSet.start();
                isClick=true;
                break;
            case MotionEvent.ACTION_CANCEL:
                ObjectAnimator objectAnimatorX3 = ObjectAnimator.ofFloat(this, View.SCALE_X, 0.9f, 1);
                ObjectAnimator objectAnimatorY3 = ObjectAnimator.ofFloat(this, View.SCALE_Y, 0.9f, 1);
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.playTogether(objectAnimatorX3, objectAnimatorY3);
                animatorSet3.setDuration(100);
                animatorSet3.start();
            case MotionEvent.ACTION_MOVE:
                if ((event.getX()<0||event.getY()<0)&&isClick){
                    ObjectAnimator objectAnimatorX2 = ObjectAnimator.ofFloat(this, View.SCALE_X, 0.9f, 1);
                    ObjectAnimator objectAnimatorY2 = ObjectAnimator.ofFloat(this, View.SCALE_Y, 0.9f, 1);
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    animatorSet2.playTogether(objectAnimatorX2, objectAnimatorY2);
                    animatorSet2.setDuration(100);
                    animatorSet2.start();
                    isClick=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isClick)
                    return true ;
                ObjectAnimator objectAnimatorX1 = ObjectAnimator.ofFloat(this, View.SCALE_X, 0.9f, 1);
                ObjectAnimator objectAnimatorY1 = ObjectAnimator.ofFloat(this, View.SCALE_Y, 0.9f, 1);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(objectAnimatorX1, objectAnimatorY1);
                animatorSet1.setDuration(100);
                animatorSet1.start();
                animatorSet1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (event.getAction() == MotionEvent.ACTION_UP)
                             performClick();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
        }
        return true;
    }
}
