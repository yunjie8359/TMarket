package com.richfit.bi.android.tmarket.model;

/**
 * Created by YunJie on 2015/2/6.
 */
public class ItemCategoryModel {

    public String mName;
    public int mIconRes;

    public ItemCategoryModel(String name, int iconRes) {
        this.mName = name;
        this.mIconRes = iconRes;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmIconRes() {
        return mIconRes;
    }

    public void setmIconRes(int mIconRes) {
        this.mIconRes = mIconRes;
    }
}
