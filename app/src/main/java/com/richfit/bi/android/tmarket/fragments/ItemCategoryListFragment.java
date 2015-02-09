package com.richfit.bi.android.tmarket.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.richfit.bi.android.tmarket.R;
import com.richfit.bi.android.tmarket.model.ItemCategoryModel;

public class ItemCategoryListFragment extends ListFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.category_list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ItemCategoryAdapter adapter = new ItemCategoryAdapter(getActivity());
        for (int i = 0; i < 20; i++) {
            adapter.add(new ItemCategoryModel("Category" + i, android.R.drawable.ic_menu_search));
        }
        setListAdapter(adapter);
    }

    public class ItemCategoryAdapter extends ArrayAdapter<ItemCategoryModel> {

        public ItemCategoryAdapter(Context context) {
            super(context, 0);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_row, null);
            }
            ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
            icon.setImageResource(getItem(position).mIconRes);
            TextView title = (TextView) convertView.findViewById(R.id.row_title);
            title.setText(getItem(position).mName);

            return convertView;
        }

    }

}
