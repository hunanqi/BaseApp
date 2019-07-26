package com.android.baseapp.base;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.baseapp.R;

import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import base.mvp.BasePresenter;
import base.mvp.BaseView;

/**
 * Date   2017/8/1
 * Editor  Misuzu
 * 带有下拉刷新的fragment
 */

public abstract class MvpBaseRefreshActivity<T extends BasePresenter> extends MvpBaseActivity<T> implements SwipeRefreshLayout.OnRefreshListener, BaseView {

    protected FrameLayout mContentView;
    protected SwipeRefreshLayout mSmartRefreshLayout;


    @Override
    protected void initSomeData() {
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.base_color));
        changeEmptyView();// 重写改变empty显示
        setShowLoading(true);
    }



    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_refresh_base_layout);
        mContentView = (FrameLayout) findViewById(R.id.container_layout);
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        View realLayout = LayoutInflater.from(this).inflate(layoutResID, mContentView, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.addView(realLayout, 0, params);
    }


    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {
        getData();
    }

    /**
     * 配置Empty显示
     */
    protected void changeEmptyView() {

    }

    /**
     * 加载动画显示隐藏
     */
    @Override
    public void setShowLoading(final boolean isShow) {
        mContentView.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mSmartRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if (mSmartRefreshLayout != null) {
                    mSmartRefreshLayout.setRefreshing(isShow);
                }
            }
        });
    }
}
