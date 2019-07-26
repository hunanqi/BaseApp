package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.mylibrary.R;
import com.android.mylibrary.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import view.loding.LoadingView;

/**
 * 加载View
 */

public class MyLoadingView extends LinearLayout {


    @BindView(R2.id.loading_view)
    LoadingView mLoadingView;

    public MyLoadingView(Context context) {
        super(context);
        initView();
    }

    public MyLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.loading_layout, this);
        ButterKnife.bind(this, this);
        setOrientation(VERTICAL);
    }

    /**
     * 显示隐藏加载页
     */
    public void setShowLoading(boolean isShow) {
        if (isShow){
            setVisibility(VISIBLE);
        }else {

            setVisibility(GONE);
        }
    }
}
