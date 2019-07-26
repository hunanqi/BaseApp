package base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.mylibrary.R;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import view.MyEmptyView;
import view.MyLoadingView;

/**
 * 普通fragment基类 带有emptyView
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected Context mContext;
    protected Unbinder mUnbinder;
    protected CompositeDisposable mCompositeDisposable;
    protected MyEmptyView mEmptyView;
    protected MyLoadingView mLoadingView;


    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_with_empty,container,false);
        FrameLayout rootView = (FrameLayout) baseView.findViewById(R.id.container);
        mEmptyView = (MyEmptyView) baseView.findViewById(R.id.my_empty_view);
        mLoadingView = (MyLoadingView) baseView.findViewById(R.id.my_loading_view);
        View.inflate(getContext(),getLayout(),rootView);
        return baseView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);
        initViewAndData();
        if (isRegistEventBus()){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegistEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null)
            mUnbinder.unbind();
        dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 是否注册EventBus
     */
    public boolean isRegistEventBus(){
        return false;
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

    protected abstract @LayoutRes
    int getLayout();
    protected abstract void initViewAndData();
}
