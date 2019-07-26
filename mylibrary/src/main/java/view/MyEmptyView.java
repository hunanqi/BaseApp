package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mylibrary.R;
import com.android.mylibrary.R2;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 界面状态显示View
 */

public class MyEmptyView extends LinearLayout {

    @BindView(R2.id.empty_img)
    ImageView mEmptyImg;
    @BindView(R2.id.empty_txt)
    TextView mEmptyTxt;
    @BindView(R2.id.empty_jump)
    TextView mEmptyJump;
    @BindView(R2.id.empty_layout)
    ConstraintLayout mEmptyLayout;
    ConstraintSet mConstraintSet;
    private boolean isBottom=false;//是否下拉刷新

    String emptyText;  // 提示文字
    int emptyImg; // 提示图片

    public MyEmptyView(Context context) {
        super(context);
        initView();
    }

    public MyEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.my_empty_layout, this);
        ButterKnife.bind(this,this);
        emptyText = getContext().getString(R.string.empty_no_data);
        emptyImg = R.drawable.bg_empty_data;
    }

    /**
     * 设置显示图片
     */
    public void setEmptyImg(@DrawableRes int imgRes)
    {
        mEmptyImg.setImageResource(emptyImg = imgRes);
    }

    /**
     * 设置提示文字
     */
    public void setEmptyTxt(String text)
    {
        mEmptyTxt.setText(emptyText = text);
    }

    /**
     * 设置带有跳转按钮和提示文字的显示
     */
    public void showEmptyOnJump(String emptyText,String jumpText,OnClickListener listener)
    {
        setVisibility(VISIBLE);
        if (isBottom)
           mEmptyJump.setVisibility(GONE);
        else
            mEmptyJump.setVisibility(VISIBLE);
        mEmptyImg.setImageResource(emptyImg);
        mEmptyTxt.setText(emptyText);
        mEmptyJump.setText(jumpText);
        mEmptyJump.setOnClickListener(listener);
    }

    /**
     * 设置无数据时 默认显示
     */
    public void showEmptyOnNoData()
    {
        setVisibility(VISIBLE);
        mEmptyJump.setVisibility(GONE);
        mEmptyImg.setImageResource(emptyImg);
        mEmptyTxt.setText(emptyText);
    }
    /**
     * 设置网络错误时 默认显示
     */
    public void showEmptyOnNoData(OnClickListener listener)
    {
        setVisibility(VISIBLE);
        if (isBottom)
            mEmptyJump.setVisibility(GONE);
        else
            mEmptyJump.setVisibility(VISIBLE);
        mEmptyImg.setImageResource(R.drawable.bg_empty_data);
        mEmptyTxt.setText(getContext().getString(R.string.empty_no_data));
        mEmptyJump.setText(getContext().getString(R.string.empty_net_refresh));
        mEmptyJump.setOnClickListener(listener);
    }
    /**
     * 设置网络错误时 默认显示
     */
    public void showErrorOnNetError(OnClickListener listener)
    {
        setVisibility(VISIBLE);
        if (isBottom)
            mEmptyJump.setVisibility(GONE);
        else
            mEmptyJump.setVisibility(VISIBLE);
        mEmptyImg.setImageResource(R.drawable.bg_error_network);
        mEmptyTxt.setText(getContext().getString(R.string.empty_net_error));
        mEmptyJump.setText(getContext().getString(R.string.empty_net_refresh));
        mEmptyJump.setOnClickListener(listener);
    }

    public void setBottom(boolean bottom) {
        isBottom = bottom;
    }

    /**
     * 隐藏图片显示
     */
    public void hideImage()
    {
        mEmptyImg.setVisibility(GONE);
    }

    /**
     * 隐藏空View
     */
    public void hideEmptyView()
    {
        setVisibility(GONE);
    }

    public TextView getEmptyJump() {
        return mEmptyJump;
    }

    /**
     * 获取根布局
     */
    public ConstraintLayout getEmptyLayout() {
        return mEmptyLayout;
    }

    /**
     * 设置空布局img 距离top的百分比
     * @param mPercent 百分比 eg：0.5
     */
    public void setMarginTopPercent(float mPercent){

        mConstraintSet = new ConstraintSet();
        mConstraintSet.clone(mEmptyLayout);
        mConstraintSet.setVerticalBias(R.id.empty_img,mPercent);
        mConstraintSet.applyTo(mEmptyLayout);

    }
}
