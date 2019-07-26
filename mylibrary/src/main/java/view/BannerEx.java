package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 扩展ex 解决滑动冲突
 */

public class BannerEx extends BGABanner {


    public BannerEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    SwipeRefreshLayout swipeRefreshLayout;

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    float lastX = 0;
    float lastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (swipeRefreshLayout != null) {

            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = ev.getX();
                    lastY = ev.getY();
                    swipeRefreshLayout.setEnabled(false);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(lastY - ev.getY()) - Math.abs(lastX - ev.getX()) > 50)
                        swipeRefreshLayout.setEnabled(true);
                    else swipeRefreshLayout.setEnabled(false);
                    break;
                case MotionEvent.ACTION_UP:
                    swipeRefreshLayout.setEnabled(true);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    swipeRefreshLayout.setEnabled(true);
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
