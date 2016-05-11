package com.zczczy.leo.fuwuwangapp.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.zczczy.leo.fuwuwangapp.dao.SearchHistory;
import com.zczczy.leo.fuwuwangapp.dao.SearchHistoryDao;
import com.zczczy.leo.fuwuwangapp.items.SearchHistoryItemView_;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.List;

/**
 * Created by leo on 2016/5/5.
 */
@EBean
public class SearchHistoryAdapter extends BaseRecyclerViewAdapter<SearchHistory> {

    @Bean
    SearchHistoryDao searchHistoryDao;


    @Override
    @Background
    public void getMoreData(Object... objects) {
        afterGetData(searchHistoryDao.getAll());
    }

    @UiThread
    void afterGetData(List<SearchHistory> bmj) {
        if (getItemCount() > 0) {
            clear();
        }
        insertAll(bmj, getItems().size());
    }


    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return SearchHistoryItemView_.build(parent.getContext());
    }
}
