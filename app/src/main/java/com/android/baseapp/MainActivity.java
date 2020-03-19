package com.android.baseapp;

import android.widget.ExpandableListView;

import com.android.baseapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ExpandableListView01)
    ExpandableListView mExpandableListView01;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleText() {
        return 0;
    }

    @Override
    protected void initViewAndData() {
        List<TestBean> list=new ArrayList<>();
        for (int j=0;j<10;j++){
            List<String> list1=new ArrayList<>();
            for (int i=0;i<5;i++){
                list1.add("二级"+i);
            }
            TestBean testBean= new TestBean(list1);
            list.add(testBean);
        }
        ListAdapter listAdapter=new ListAdapter(list,mContext);
        mExpandableListView01.setGroupIndicator(null);
        mExpandableListView01.setAdapter(listAdapter);

    }

}
