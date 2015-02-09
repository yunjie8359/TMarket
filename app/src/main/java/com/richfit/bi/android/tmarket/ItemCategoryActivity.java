package com.richfit.bi.android.tmarket;

import android.os.Bundle;

import com.richfit.bi.android.tmarket.fragments.ItemCategoryListFragment;

/**
 * Created by YunJie on 2015/2/6.
 */
public class ItemCategoryActivity extends SlidingMenuBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_content_frame);

//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.content_frame, new ItemCategoryListFragment())
//                .commit();

        setSlidingActionBarEnabled(true);
    }

}
