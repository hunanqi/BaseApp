package base.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 封装Rx操作的presenter
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeDisposable mCompositeDisposable;

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
    }


    @Override
    public void attachView(T view) {
        mView = view;
    }


    @Override
    public void dettachView() {
        mView = null;
        dispose();
    }

}
