package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.SearchHistoryAdapter;
import com.zczczy.leo.fuwuwangapp.dao.SearchHistory;
import com.zczczy.leo.fuwuwangapp.dao.SearchHistoryDao;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by leo on 2016/5/5.
 */
@EActivity(R.layout.activity_search)
public class SearchActivity extends BaseRecyclerViewActivity<SearchHistory> {

    @Bean
    SearchHistoryDao searchHistoryDao;

    @Extra
    boolean isService, isMerchant, isStore;

    @Extra
    String storeId;

    @StringRes
    String search_hint, search_service_hint, search_merchant_hint, search_store_hint;

    @ViewById
    EditText text_search;

    SearchHistory searchHistory;

    boolean isStoreSearch;

    @Bean
    void setAdapter(SearchHistoryAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }


    @AfterViews
    void afterView() {
        myAdapter.getMoreData(0, 0);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).margin(0).paint(paint).build());
        if (isService) {
            text_search.setHint(search_service_hint);
            myTitleBar.setRightText("店铺");
            isStoreSearch = false;
        } else if (isMerchant) {
            text_search.setHint(search_merchant_hint);
        } else if (isStore) {
            text_search.setHint(search_store_hint);
        } else {
            text_search.setHint(search_hint);
            myTitleBar.setRightText("商品");
            isStoreSearch = true;
        }

        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<SearchHistory>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, SearchHistory obj, int position) {
                searchHistoryDao.update(obj);
                if ((isService || !isStoreSearch) && !isMerchant) {
                    StoreSearchActivity_.intent(SearchActivity.this).searchName(obj.getSearchContent()).startForResult(1000);
                } else if (isMerchant) {
                    MerchantSearchResultActivity_.intent(SearchActivity.this).searchContent(obj.getSearchContent()).startForResult(1000);
                } else {
                    CommonSearchResultActivity_.intent(SearchActivity.this).storeId(storeId).searchContent(obj.getSearchContent()).startForResult(1000);
                }
            }
        });
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isService && !isMerchant && isStoreSearch) {
                    text_search.setHint(search_service_hint);
                    myTitleBar.setRightText("店铺");
                    isStoreSearch = false;
                } else if (!isService && !isMerchant && !isStoreSearch) {
                    text_search.setHint(search_hint);
                    myTitleBar.setRightText("商品");
                    isStoreSearch = true;
                } else {
                    if (!AndroidTool.checkIsNull(text_search)) {
                        searchHistory = new SearchHistory();
                        searchHistory.setSearchContent(text_search.getText().toString());
                        searchHistory.setSearchTime(String.valueOf(System.currentTimeMillis()));
                        searchHistoryDao.insert(searchHistory);
                        if ((isService || !isStoreSearch) && !isMerchant) {
                            StoreSearchActivity_.intent(SearchActivity.this).searchName(text_search.getText().toString()).startForResult(1000);
                        } else if (isMerchant) {
                            MerchantSearchResultActivity_.intent(SearchActivity.this).searchContent(text_search.getText().toString()).startForResult(1000);
                        } else {
                            CommonSearchResultActivity_.intent(SearchActivity.this).storeId(storeId).searchContent(text_search.getText().toString()).startForResult(1000);
                        }
                    }
                }
            }
        });
    }

    @EditorAction
    void text_search(int actionId) {
        if (!AndroidTool.checkIsNull(text_search) && actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchHistory = new SearchHistory();
            searchHistory.setSearchContent(text_search.getText().toString());
            searchHistory.setSearchTime(String.valueOf(System.currentTimeMillis()));
            searchHistoryDao.insert(searchHistory);
            if ((isService || !isStoreSearch) && !isMerchant) {
                StoreSearchActivity_.intent(SearchActivity.this).searchName(text_search.getText().toString()).startForResult(1000);
            } else if (isMerchant) {
                MerchantSearchResultActivity_.intent(SearchActivity.this).searchContent(text_search.getText().toString()).startForResult(1000);
            } else {
                CommonSearchResultActivity_.intent(SearchActivity.this).storeId(storeId).searchContent(text_search.getText().toString()).startForResult(1000);
            }
        }
    }


    @Click
    void btn_clear() {
        searchHistoryDao.clear();
        myAdapter.getMoreData(0, 0);
    }


    @OnActivityResult(value = 1000)
    void onResult(int resultCode) {
        if (resultCode == RESULT_OK || resultCode == 1000) {
            myAdapter.getMoreData(0, 0);
        }
    }

}
