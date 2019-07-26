package com.android.baseapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.baseapp.R;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import view.MyEmptyView;
import view.MyLoadingView;
import view.ToolBarTitleView;

/**
 * 普通Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ToolBarTitleView mToobarTitleView;

    protected MyEmptyView mEmptyView;
    protected MyLoadingView mLoadingView;

    protected Activity mContext;
    protected Unbinder mUnbinder;
    protected CompositeDisposable mCompositeDisposable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        mToobarTitleView = findViewById(R.id.toobar_title_view);
        initToolBar();
        initViewAndData();
        if (isRegistEventBus()){
            EventBus.getDefault().register(this);
        }
    }


    /**
     * 初始化ToolBar 可重写修改属性
     * 传0 代表没有标题栏
     */
    protected void initToolBar() {
        if (getTitleText() != 0)
            mToobarTitleView.setTitleText(getString(getTitleText()));
        else
            mToobarTitleView.setVisibility(View.GONE);

        mToobarTitleView.setTitleBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onResume() {
        //MobclickAgent.onResume(this);  //友盟统计
        super.onResume();

    }
    public void onPause() {
       // MobclickAgent.onPause(this); //友盟统计
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegistEventBus()){
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null)
            mUnbinder.unbind();
        dispose();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_title_base_layout);
        FrameLayout baseLayout = findViewById(R.id.base_layout);
        mEmptyView =  findViewById(R.id.base_empty);
        mLoadingView = findViewById(R.id.base_loading);
        View realLayout = LayoutInflater.from(this).inflate(layoutResID,baseLayout,false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        baseLayout.addView(realLayout,0,params);
    }

    /**
     * 添加订阅到集合
     */
    protected void addDisposable(Disposable disposable) {

        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    /**
     * 解除所有订阅
     */
    protected void dispose() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        mCompositeDisposable = null;
    }

    /**
     * 是否注册EventBus
     */
    public boolean isRegistEventBus(){
        return false;
    }

    /**
     * 获取布局Id
     */
    protected abstract @LayoutRes int getLayout();

    /**
     * 获取标题title
     */
    protected abstract @StringRes
    int getTitleText();

    /**
     * 初始化布局和数据
     */
    protected abstract void initViewAndData();
}
