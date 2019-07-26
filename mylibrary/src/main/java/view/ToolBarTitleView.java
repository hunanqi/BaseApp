package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mylibrary.R;
import com.android.mylibrary.R2;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class ToolBarTitleView extends LinearLayout {

    @BindView(R2.id.title_back)
    ImageView mTitleBack;
    @BindView(R2.id.title_right_img)
    TipsImageView mTitleRightImg;
    @BindView(R2.id.title_text_right)
    TextView mTitleTextRight;
    @BindView(R2.id.title_text)
    TextView mTitleText;
    @BindView(R2.id.title_toolbar)
    Toolbar mTitleToolbar;
    @BindView(R2.id.toolbar_line)
    View mToolbarLine;

    public ToolBarTitleView(Context context) {
        super(context);
        initView();
    }

    public ToolBarTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ToolBarTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.toolbar_title_layout, this);
        ButterKnife.bind(this, this);
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitleText(String title) {
        mTitleText.setText(title);
    }

    /**
     * 设置标题2
     * @param title 标题
     */
    public void setTitleText(@StringRes int title) {
        mTitleText.setText(getContext().getText(title));
    }

    /**
     * 设置右边图标
     *
     * @param imgRes   图片资源
     * @param listener 点击监听
     */
    public void setTitleRightImg(@DrawableRes int imgRes, @Nullable OnClickListener listener) {
        mTitleRightImg.setVisibility(VISIBLE);
        mTitleRightImg.setImageResource(imgRes);
        mTitleRightImg.setOnClickListener(listener);
    }

    /**
     * 设置右边文字
     *
     * @param text     文字
     * @param color    文字颜色
     * @param listener 点击监听
     */
    public void setTitleTextRight(String text, @ColorRes int color, @Nullable OnClickListener listener) {
        mTitleTextRight.setVisibility(VISIBLE);
        mTitleTextRight.setText(text);
        mTitleTextRight.setOnClickListener(listener);
        mTitleTextRight.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    /**
     * 设置左边图片
     *
     * @param imgRes      图标
     * @param listener 点击监听
     */
    public void setTitleLeftImg(@DrawableRes int imgRes, @Nullable OnClickListener listener) {
        mTitleBack.setImageResource(imgRes);
        mTitleBack.setOnClickListener(listener);
    }

    /**
     * back 返回监听
     *
     * @param listener 点击监听
     */
    public void setTitleBackListener(@Nullable OnClickListener listener) {
        mTitleBack.setOnClickListener(listener);
    }

    /**
     *  隐藏返回键
     */
    public void hideBackButton()
    {
        mTitleBack.setVisibility(GONE);
    }

    /**
     * 返回右边图片 方便调整
     */
    public ImageView getRightImage() {
        return mTitleRightImg;
    }

    /**
     * 返回中间TextView 方便调整
     */
    public TextView getCenterTextView() {
        return mTitleText;
    }

    /**
     * 返回下划线 方便调整
     */
    public View getToolbarLine() {
        return mToolbarLine;
    }

    /**
     * 设置标题栏透明
     */
    public void setTranslucentForBg()
    {
        mTitleToolbar.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.transparent));
    }

    /**
     * 设置标题栏颜色
     */
    public void setColorForBg(@ColorRes int colorForBg)
    {
        mTitleToolbar.setBackgroundColor(ContextCompat.getColor(getContext(),colorForBg));
    }

    /**
     * 设置是否显示小红点
     */
    public void setTipsOn(boolean isOn){
        mTitleRightImg.setTipOn(isOn);
    }

    /**
     * 返回右边文字
     */
    public TextView getTextRight(){
        return mTitleTextRight;
    }
}
