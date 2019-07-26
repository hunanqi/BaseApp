package base.mvp;

/**
 * View 基本接口
 */

public interface BaseView {

    /**
     * 加载动画显示隐藏
     */
    public void setShowLoading(boolean isShow);

    /**
     * 显示错误
     */
    public void showError(int code,String msg);

    /**
     * 无内容布局显示隐藏
     */
    public void setShowEmpty(boolean isShow);

    /**
     *  刷新
     */
    public void refresh();
}
