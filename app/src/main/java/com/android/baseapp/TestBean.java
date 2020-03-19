package com.android.baseapp;

import java.util.List;

/**
 * Created by hunanqi on 2020/3/19 0019.
 */
public class TestBean {
    private List<String>  childeList;

    public List<String> getChildeList() {
        return childeList;
    }

    public void setChildeList(List<String> childeList) {
        this.childeList = childeList;
    }

    public TestBean(List<String> childeList) {
        this.childeList = childeList;
    }
}
