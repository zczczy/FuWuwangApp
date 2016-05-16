package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.SearchHistoryAdapter;
import com.zczczy.leo.fuwuwangapp.dao.SearchHistory;
import com.zczczy.leo.fuwuwangapp.dao.SearchHistoryDao;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/5.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    RecyclerView recyclerView;

    @Bean(SearchHistoryAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    @Bean
    SearchHistoryDao searchHistoryDao;

    @Extra
    boolean isService;

    EditText text_search;

    LinearLayoutManager linearLayoutManager;

    SearchHistory searchHistory;

    Paint paint = new Paint();

    @AfterViews
    void afterView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.getMoreData(0, 0);
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(0).paint(paint).build());

        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<SearchHistory>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, SearchHistory obj, int position) {
                searchHistoryDao.update(obj);
                if (isService) {
                    StoreSearchActivity_.intent(SearchActivity.this).searchName(obj.getSearchContent()).startForResult(1000);
                } else {
                    CommonSearchResultActivity_.intent(SearchActivity.this).searchContent(obj.getSearchContent()).startForResult(1000);
                }
            }
        });
        View view = myTitleBar.getmCustomView();
        text_search = (EditText) view.findViewById(R.id.text_search);
        text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!AndroidTool.checkIsNull(text_search) && actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchHistory = new SearchHistory();
                    searchHistory.setSearchContent(text_search.getText().toString());
                    searchHistory.setSearchTime(String.valueOf(System.currentTimeMillis()));
                    searchHistoryDao.insert(searchHistory);
                    if (isService) {
                        StoreSearchActivity_.intent(SearchActivity.this).searchName(text_search.getText().toString()).startForResult(1000);
                    } else {
                        CommonSearchResultActivity_.intent(SearchActivity.this).searchContent(text_search.getText().toString()).startForResult(1000);
                    }
                }
                return true;
            }
        });
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AndroidTool.checkIsNull(text_search)) {
                    searchHistory = new SearchHistory();
                    searchHistory.setSearchContent(text_search.getText().toString());
                    searchHistory.setSearchTime(String.valueOf(System.currentTimeMillis()));
                    searchHistoryDao.insert(searchHistory);
                    if (isService) {
                        StoreSearchActivity_.intent(SearchActivity.this).searchName(text_search.getText().toString()).startForResult(1000);
                    } else {
                        CommonSearchResultActivity_.intent(SearchActivity.this).searchContent(text_search.getText().toString()).startForResult(1000);
                    }
                }
            }
        });
    }

    @Click
    void btn_clear() {
        searchHistoryDao.clear();
        myAdapter.getMoreData(0, 0);
    }


    @OnActivityResult(value = 1000)
    void onResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            myAdapter.getMoreData(0, 0);
        }
    }

}
