package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.android.mylibrary.R;

import androidx.appcompat.widget.AppCompatImageView;


/**
 * 带小红点的ImageView
 */

public class TipsImageView extends AppCompatImageView {

    private boolean mTipOn = false;

    private Dot mDot;


    public TipsImageView(Context context) {
        super(context);
        init();
    }

    public TipsImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TipsImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDot = new Dot();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mTipOn) {
            float cx = getWidth() - mDot.marginRight - mDot.radius;
            float cy = mDot.marginTop + mDot.radius;

            Drawable mDrawable = getDrawable();
            if (mDrawable != null) {
                int drawableWidth = mDrawable.getIntrinsicWidth();
                int drawableHeight = mDrawable.getIntrinsicHeight();
                if (drawableWidth > 0) {
                    int dotLeft = getWidth() / 2 + drawableWidth / 2;
                    int dotTop =  drawableHeight / 2;
                    cx = dotLeft - mDot.marginRight;
                    cy = dotTop + mDot.marginTop;
                }
            }

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            int tempColor = paint.getColor();

            paint.setColor(mDot.color);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, mDot.radius, paint);

            paint.setColor(tempColor);
        }
    }

    //设置显示小红点与否
    public void setTipOn(boolean tip) {
        this.mTipOn = tip;
        invalidate();
    }

    // 小红点是否显示
    public boolean isTipOn() {
        return mTipOn;
    }


    private class Dot {

        int color;
        int radius;
        int marginTop;
        int marginRight;

        Dot() {
            float density = getContext().getResources().getDisplayMetrics().density;
            radius = (int) (3 * density);
            marginTop = (int) (6 * density);
            marginRight = (int) (5 * density);

            color = getContext().getResources().getColor(R.color.red_tip_bg);
        }

    }
}