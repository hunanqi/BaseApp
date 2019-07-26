package com.android.baseapp.base;


import base.BaseFragment;
import base.mvp.BasePresenter;
import base.mvp.BaseView;

/**

 * mvp fragment 基类
 */

public abstract class MvpBaseFragment<T extends BasePresenter> extends BaseFragment implements BaseView {


    protected T mPresenter;

    @Override
    protected void initViewAndData() {
        mPresenter = bindPresenter();
        if (mPresenter != null)
            mPresenter.attachView(this); // 绑定View
        initSomeData();
        getData();
    }


    @Override
    public void onDestroy() {
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
        if (mEmptyView != null) {
            if (isShow) {
                mEmptyView.showEmptyOnNoData();
            }else
            {
                mEmptyView.hideEmptyView();
            }
        }
    }


    /**
     * 刷新
     */
    @Override
    public void refresh() {
        getData();
    }


    /**
     * 初始化Presenter
     */
    protected abstract T bindPresenter();

    /**
     * 从Presenter获取数据
     */
    protected abstract void getData();


    /**
     * 初始化某些数据
     */
    protected abstract void initSomeData();

}
