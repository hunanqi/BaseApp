package view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.mylibrary.R;
import com.android.mylibrary.R2;

import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.DialogFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import utils.ViewUtil;

/**
 * 加载对话框
 */

public class MyLoadingFragment extends DialogFragment {

    @BindView(R2.id.loading_view)
    ContentLoadingProgressBar mLoadingView;
    @BindView(R2.id.loading_text)
    TextView mLoadingText;
    String tips;
    Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView = inflater.inflate(R.layout.dialog_loading_layout, container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        if (getArguments() != null)
          tips = getArguments().getString("tips");
        if (!TextUtils.isEmpty(tips))
            mLoadingText.setText(tips);
        setCancelable(false);
        return mView;
    }

    public static MyLoadingFragment newInstance() {
        return new MyLoadingFragment();
    }

    public static MyLoadingFragment newInstance(String tips) {
        Bundle args = new Bundle();
        args.putString("tips",tips);
        MyLoadingFragment fragment = new MyLoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewUtil.dp2Px(getContext(), 130);
        lp.height = ViewUtil.dp2Px(getContext(), 130);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
