package com.android.baseapp.base;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.android.baseapp.R;
import com.android.mylibrary.R2;
import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import base.BaseFragment;
import base.MyBaseAdapter;
import base.mvp.BaseView;
import butterknife.BindView;
import utils.ViewUtil;
import view.MyEmptyView;

/**
 * 列表界面简单基类
 */

public abstract class BaseListFragment<T extends MyBaseAdapter> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseView {

    @BindView(R2.id.recyclerview)
    protected RecyclerView mRecyclerview;
    @BindView(R2.id.swiprefreshlayout)
    protected SwipeRefreshLayout mSwiprefreshlayout;
    @BindView(R2.id.my_empty_view)
    protected MyEmptyView mEmptyView;

    protected T mAdapter;
    protected int page;
    private boolean mAutoRefreshing = false;
    private ImageView mDefaultImg;

    @Override
    protected int getLayout() {
        return R.layout.swiperefresh_layout;
    }

    @Override
    protected void initViewAndData() {
        initSomeData();//初始化一些数据
        mSwiprefreshlayout.setOnRefreshListener(this);
        mSwiprefreshlayout.setEnabled(isDownRefresh());
        mSwiprefreshlayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.base_color));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(linearLayoutManager);
        changeRecyclerView(); // 重写改变列表设置
        changeEmptyView();// 重写改变empty显示
        mEmptyView.setBottom(true);
        mAdapter = initAdapter();
        if (mAdapter != null) {
            setLoadMoreAndBindRecyclerview(mAdapter); //设置加载更多和绑定
            setShowLoading(true);
            onRefresh();
        }
    }

    /**
     * 是否允许下拉刷新
     * @return
     */
    protected boolean isDownRefresh(){
        return true;
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
     * 自动刷新
     */
    public void autoRefresh() {
        if (!mAutoRefreshing && mSwiprefreshlayout != null && mAdapter != null && mRecyclerview != null) {
            mAutoRefreshing = true;
            mSwiprefreshlayout.post(new Runnable() {
                @Override
                public void run() {
                    setShowLoading(true);
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerview.smoothScrollToPosition(0);
                    onRefresh();
                    mAutoRefreshing = false;
                }
            }, 800);
        }
    }

    public void getData(){

        setShowLoading(true);
        onRefresh();
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

        if (mSwiprefreshlayout != null) {
            mSwiprefreshlayout.post(new Runnable() {
                @Override
                public void run() {
                    if (mSwiprefreshlayout != null)
                        mSwiprefreshlayout.setRefreshing(isShow);
                }
            });
        }
    }

    /**
     * 显示错误
     */
    @Override
    public void showError(int code, String msg) {

        mEmptyView.showErrorOnNetError(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setShowEmpty(false);
                    setShowLoading(true);
                    refresh();
                }
            });

    }

    /**
     * 无内容布局显示隐藏
     */
    @Override
    public void setShowEmpty(boolean isShow) {
        if (mEmptyView != null) {
            if (isShow) {
                mEmptyView.showEmptyOnNoData(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setShowEmpty(false);
                        setShowLoading(true);
                        refresh();
                    }
                });
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
        setShowLoading(true);
    }

    /**
     * 设置padding Top
     */
    protected void setPaddingTop(int dp) {
        if (mRecyclerview != null) {
            mRecyclerview.setClipToPadding(false);
            mRecyclerview.setPadding(0, (int) ViewUtil.dp2Px(getContext(), dp), 0, 0);
        }
    }
    /**
     * 设置padding Top
     */
    protected void setPaddingBottom(int dp) {
        if (mRecyclerview != null) {
            mRecyclerview.setClipToPadding(false);
            mRecyclerview.setPadding(0, 0, 0, (int) ViewUtil.dp2Px(getContext(), dp));
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
