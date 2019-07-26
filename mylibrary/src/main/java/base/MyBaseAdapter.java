package base;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import base.mvp.BaseView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 列表adapter基类
 */

public abstract class MyBaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    protected CompositeDisposable mCompositeDisposable;

    public MyBaseAdapter(List<T> data) {
        super(data);
    }

    public MyBaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected int getDefItemViewType(int position) {  //这里的position 是真实数据的
        return getItemViewByType(position);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return OnCreateViewByHolder(parent, viewType);
    }

    protected abstract int getItemViewByType(int position);  //设置不同类型

    protected abstract BaseViewHolder OnCreateViewByHolder(ViewGroup parent, int viewType); //设置ViewHolder

    public abstract void initOnItemClickListener();// 设置点击事件监听

    /**
     * 处理上拉 下拉的数据
     */
    public void dealLoadData(BaseView mBaseView, boolean isLoadMore, List<T> data) {
        if (!isLoadMore) //下拉刷新
        {
            if (data != null && data.size() > 0) {
                mBaseView.setShowEmpty(false);
                setNewData(data);
                disableLoadMoreIfNotFullPage();
            } else if(isZero)
                mBaseView.setShowEmpty(true);
            mBaseView.setShowLoading(false);
        } else  // 上拉加载
        {
            if (data != null && data.size() > 0) {
                addData(data);
                loadMoreComplete();
            } else
                loadMoreEnd();
        }
    }
    /**
     * 处理上拉 下拉的数据
     */
    public void dealLoadData(BaseView mBaseView, boolean isLoadMore, List<T> data,int current,int pages) {
        if (!isLoadMore) //下拉刷新
        {
            if (current<=pages){
                if (data != null && data.size() > 0) {
                    mBaseView.setShowEmpty(false);
                    setNewData(data);
                    //disableLoadMoreIfNotFullPage();
                    if (current==pages)
                        loadMoreEnd();
                } else
                    mBaseView.setShowEmpty(true);
            }else {
                mBaseView.setShowEmpty(true);
            }
            mBaseView.setShowLoading(false);
        } else  // 上拉加载
        {
            if (current<=pages) {
                if (data != null && data.size() > 0) {
                    addData(data);
                }
                if (current==pages)
                   loadMoreEnd();
                else
                   loadMoreComplete();
            }
              else
                loadMoreEnd();
        }
    }

    /**
     * 处理加载错误情况
     */
    public void dealLoadError(BaseView mBaseView, int code, String msg, boolean isLoadMore) {
        if (!isLoadMore) {
            if (mData.size() == 0&&isZero)
                mBaseView.showError(code, msg);
            mBaseView.setShowLoading(false);
        } else
            loadMoreFail();
    }
    private boolean isZero=true;
    public void setZero(boolean isZero){
        this.isZero=isZero;
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
    public void dispose() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
        mCompositeDisposable = null;
    }
}