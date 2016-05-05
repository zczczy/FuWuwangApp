package com.zczczy.leo.fuwuwangapp.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.androidannotations.annotations.EBean;
import org.androidannotations.ormlite.annotations.OrmLiteDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Leo on 2016/1/6.
 */
@EBean
public class SearchHistoryDao {

    @OrmLiteDao(helper = DatabaseHelper.class)
    Dao<SearchHistory, Integer> searchHistories;

    public void insert(SearchHistory searchHistory) {
        QueryBuilder queryBuilder = searchHistories.queryBuilder();
        try {
            long count = queryBuilder.where().eq("searchContent", searchHistory.getSearchContent()).countOf();
            if (count <= 0) {
                searchHistories.create(searchHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            searchHistories.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SearchHistory> getAll() {
        List<SearchHistory> list;
        QueryBuilder<SearchHistory, Integer> queryBuilder = searchHistories.queryBuilder();
        try {
            list = queryBuilder.orderBy("searchTime", true).query();
        } catch (SQLException e) {
            e.printStackTrace();
            list = new ArrayList<>();
        }
        Collections.reverse(list);
        return list;
    }

    public void update(SearchHistory searchHistory) {
        searchHistory.setSearchTime(String.valueOf(System.currentTimeMillis()));
        try {
            searchHistories.update(searchHistory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
