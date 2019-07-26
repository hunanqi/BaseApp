package base.mvp;

/**
 * Presenter 基本接口
 */

public interface BasePresenter<T extends BaseView> {

    /**
     * 添加View依赖
     */
    public void attachView(T view);

    /**
     * 移除View依赖
     */
    public void dettachView();


}
