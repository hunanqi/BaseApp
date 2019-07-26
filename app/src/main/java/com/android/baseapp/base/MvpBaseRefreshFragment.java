package com.android.baseapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.baseapp.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import base.mvp.BasePresenter;
import base.mvp.BaseView;

/**
 * 带有下拉刷新的fragment
 */

public abstract class MvpBaseRefreshFragment<T extends BasePresenter> extends MvpBaseFragment<T> implements SwipeRefreshLayout.OnRefreshListener, BaseView {

    protected SwipeRefreshLayout mSwiprefreshlayout;
    protected View mContentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mBaseView = inflater.inflate(R.layout.fragment_refresh_layout, container, false);
        mSwiprefreshlayout =  mBaseView.findViewById(R.id.swiprefreshlayout);
        mEmptyView =  mBaseView.findViewById(R.id.my_empty_view);
        View.inflate(getContext(), getLayout(), mSwiprefreshlayout);
        mContentView = mSwiprefreshlayout.getChildAt(1);
        return mBaseView;
    }

    @Override
    protected void initSomeData() {
        mSwiprefreshlayout.setOnRefreshListener(this);
        mSwiprefreshlayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base_color));
        changeEmptyView();// 重写改变empty显示
        setShowLoading(true);
    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {
        getData();
    }

    /**
     * 重新刷新界面通过baseView
     */

    @Override
    public void refresh() {
        setShowLoading(true);
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
        mSwiprefreshlayout.post(new Runnable() {
            @Override
            public void run() {

                if (mSwiprefreshlayout != null) {
                    mSwiprefreshlayout.setRefreshing(isShow);
                }
            }
        });

    }
}
