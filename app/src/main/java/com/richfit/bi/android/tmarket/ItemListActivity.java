package com.richfit.bi.android.tmarket;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.richfit.bi.android.tmarket.adapter.NewestListAdapter;
import com.richfit.bi.android.tmarket.model.ResultData;

import org.json.JSONObject;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;


public class ItemListActivity extends ListActivity {

    private static final String TAG = "ItemListActivity";

    private LinkedList<Map<String, Object>> mListItems;
    private PullToRefreshListView mPullRefreshListView;
    private NewestListAdapter mAdapter;

    private int mCurrPage = 1;
    private int mPageSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ptr_list);

        mListItems = new LinkedList<Map<String, Object>>();

        Map<String, Integer> reqBean = new HashMap<String, Integer>();
        reqBean.put("currPage", mCurrPage);
        reqBean.put("pageSize", mPageSize);
        new GetDataTask().execute(reqBean);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
        // Mode.BOTH：同时支持上拉下拉
        // Mode.PULL_FROM_START：只支持下拉Pulling Down
        // Mode.PULL_FROM_END：只支持上拉Pulling Up
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            /**
             * 下拉
             */
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                mAdapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
//				Toast.makeText(PullToRefreshListActivity.this, "下拉", Toast.LENGTH_SHORT).show();
            }

            /**
             * 上拉
             */
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                Map<String, Integer> reqBean = new HashMap<String, Integer>();
                reqBean.put("currPage", ++mCurrPage);
                reqBean.put("pageSize", mPageSize);
                new GetDataTask().execute(reqBean);
            }

        });

        mAdapter = new NewestListAdapter(this, mListItems);
        mPullRefreshListView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetDataTask extends AsyncTask<Object, Integer, ResultData> {

        @Override
        protected ResultData doInBackground(Object... params) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

            String url = "http://123.57.65.248/gjj/service/item/getNewestList";
            Map<String, Object> reqBean = (Map<String, Object>) params[0];
            Log.i(TAG, String.format("请求连接：%s%n请求参数：%s", url, new JSONObject(reqBean).toString()));

            ResultData rd = restTemplate.postForObject(url, reqBean, ResultData.class);
            return rd;
        }

        @Override
        protected void onPostExecute(ResultData resultData) {
            Log.i(TAG, resultData.toString());
            if (resultData != null && "0".equals(resultData.getStat())) {
                List<Map<String, Object>> itemList = (List<Map<String, Object>>) resultData.getData();
                for (Map<String, Object> item : itemList) {
                    mListItems.addLast(item);
                }
                mAdapter.notifyDataSetChanged();
                mPullRefreshListView.onRefreshComplete();
                if (itemList.isEmpty() || itemList.size() < mPageSize) {
                    mPullRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
                }
            } else {
                Toast.makeText(ItemListActivity.this, resultData.getMsg(), Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(resultData);
        }
    }
}
