package com.android.baseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by hunanqi on 2020/3/19 0019.
 */
public class ListAdapter extends BaseExpandableListAdapter{
    private List<TestBean> mTestBeans;
    private Context mContext;

    public ListAdapter(List<TestBean> list, Context context) {
        mTestBeans=list;
        mContext=context;
    }

    @Override
    public int getGroupCount() {
        return mTestBeans.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mTestBeans.get(groupPosition).getChildeList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
                            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_01,parent,false);
                        }

                    return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_02,parent,false);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
