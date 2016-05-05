package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.dao.SearchHistory;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/1/6.
 */

@EViewGroup(R.layout.search_history_item)
public class SearchHistoryItemView extends ItemView<SearchHistory> {

    @ViewById
    TextView text_search_content;

    public SearchHistoryItemView(Context context) {
        super(context);
    }

    @Override
    protected void init(Object... objects) {
        text_search_content.setText(_data.getSearchContent());
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
