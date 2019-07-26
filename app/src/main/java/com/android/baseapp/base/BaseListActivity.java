package com.android.baseapp.base;


import com.android.baseapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import base.MyBaseAdapter;
import base.mvp.BaseView;
import butterknife.BindView;
import utils.ViewUtil;

/**
 * Date   2018/5/9
 * 基本列表页
 */

public abstract class BaseListActivity<T extends MyBaseAdapter> extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseView {

    @BindView(R.id.recyclerview)
    protected RecyclerView mRecyclerview;
    @BindView(R.id.swiprefreshlayout)
    protected SwipeRefreshLayout mSmartRefreshLayout;

    protected T mAdapter;
    protected int page;


    @Override
    protected int getLayout() {
        return R.layout.swiperefresh_layout;
    }

    @Override
    protected void initViewAndData() {
        initSomeData();//初始化一些数据
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.base_color));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        changeRecyclerView(); // 重写改变列表设置
        changeEmptyView();// 重写改变empty显示
        mEmptyView.setBottom(true);
        mAdapter = initAdapter();
        if (mAdapter != null) {
            setLoadMoreAndBindRecyclerview(mAdapter); //设置加载更多和绑定
            setShowLoading(true);
            refresh();
        }
    }




    /**
     * 是否允许上拉加载
     */
    public boolean isLoadMoreEnable() {
        return true;
    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void onRefresh() {
        page = 1;
        getListData(false);
    }


    /**
     * 加载更多回调和绑定
     */
    protected void setLoadMoreAndBindRecyclerview(T mAdapter) {
        if (mAdapter != null) {
            mAdapter.bindToRecyclerView(mRecyclerview);
            if (isLoadMoreEnable()) {
                mAdapter.setPreLoadNumber(4);
                mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        page += 1;
                        getListData(true);
                    }
                }, mRecyclerview);
            }
            mAdapter.initOnItemClickListener();

        }
    }

    /**
     * 初始化某些数据
     */
    protected void initSomeData() {

    }

    /**
     * 配置RecyclerView
     */
    protected void changeRecyclerView() {

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
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    if (mSmartRefreshLayout != null)
                        mSmartRefreshLayout.setRefreshing(isShow);
                }
            });
        }
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
            } else {
                mEmptyView.hideEmptyView();
            }
        }
    }


    /**
     * 刷新
     */
    @Override
    public void refresh() {
        page = 1;
        getListData(false);
        setShowEmpty(false);

    }

    /**
     * 设置padding Top
     */
    protected void setPaddingTop(int dp) {
        if (mRecyclerview != null) {
            mRecyclerview.setClipToPadding(false);
            mRecyclerview.setPadding(0, (int) ViewUtil.dp2Px(this, dp), 0, 0);
        }
    }

    @Override
    public void onDestroy() {
        if (mAdapter != null)
            mAdapter.dispose();
        super.onDestroy();
    }

    /**
     * 初始化Adapter
     */
    protected abstract T initAdapter();

    /**
     * 获取列表数据
     *
     * @param isLoadMore 是否加载更多
     */
    protected abstract void getListData(Boolean isLoadMore);

}


