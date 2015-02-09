package com.richfit.bi.android.tmarket.adapter;

import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.richfit.bi.android.tmarket.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewestListAdapter extends BaseAdapter {

    private static final String TAG = "NewestListAdapter";

    private Context mContext;                        //运行上下文
    private List<Map<String, Object>> mItemList;    //商品信息集合  
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public NewestListAdapter(Context context, List<Map<String, Object>> itemList) {
        super();
        this.mContext = context;
        this.mItemList = itemList;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(config);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher) // resource or drawable
                .showImageForEmptyUri(R.drawable.ic_launcher) // resource or drawable
                .showImageOnFail(R.drawable.ic_launcher) // resource or drawable
                .cacheInMemory(true) // default false
                .cacheOnDisk(true)
                .build();
    }

    @Override
    public int getCount() {
        return this.mItemList != null ? this.mItemList.size() : 0;
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return this.mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setOptions(DisplayImageOptions options) {
        this.options = options;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.activity_item_list, null);
        }

        ImageView mImageView = (ImageView) convertView.findViewById(R.id.image);
        TextView mItemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView mPrice = (TextView) convertView.findViewById(R.id.price);

        Map<String, Object> item = this.getItem(position);

        String imageUrl = ((List<Map<String, Object>>) item.get("photos")).get(0).get("photoUrl").toString();
        String itemName = item.get("itemName").toString();
        String price = item.get("price").toString();

        this.imageLoader.displayImage(imageUrl + "_small.jpg", mImageView, this.options);
        mItemName.setText(itemName);
        mPrice.setText(price);

        return convertView;
    }

}
