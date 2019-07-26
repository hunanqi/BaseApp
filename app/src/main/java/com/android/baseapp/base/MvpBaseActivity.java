package com.android.baseapp.base;


import base.mvp.BasePresenter;
import base.mvp.BaseView;

/**
 * Mvp acivtiy 基类
 */

public abstract class MvpBaseActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    public T mPresenter;
    protected boolean isloading;

    @Override
    protected void initViewAndData() {
        mPresenter = bindPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this); // 绑定View
        initSomeData();
        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.dettachView();
    }

    /**
     * 加载动画显示隐藏
     */
    @Override
    public void setShowLoading(boolean isShow) {

        mLoadingView.setShowLoading(isShow);
    }

    /**
     * 显示错误
     */
    @Override
    public void showError(int code, String msg) {


    }

    /**
     * 无内容布局显示隐藏
     */
    @Override
    public void setShowEmpty(boolean isShow) {
        if (isShow)
            mEmptyView.showEmptyOnNoData();
        else
            mEmptyView.hideEmptyView();
    }

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        setShowEmpty(false);
        setShowLoading(true);
        getData();
    }


    /**
     * 初始化Presenter
     */
    protected abstract T bindPresenter();

    /**
     * 从presenter拉取数据
     */
    protected abstract void getData();

    /**
     * 初始化某些数据
     */
    protected abstract void initSomeData();

}
