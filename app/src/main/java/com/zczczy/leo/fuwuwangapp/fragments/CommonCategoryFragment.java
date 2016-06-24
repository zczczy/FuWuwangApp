package com.zczczy.leo.fuwuwangapp.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CommonSearchResultActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CommonCategoryAdapter;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/3.
 */
@EFragment(R.layout.fragment_common_category)
public class CommonCategoryFragment extends BaseFragment {

    @ViewById
    RecyclerView recyclerView;

    @Bean(CommonCategoryAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    GridLayoutManager gridLayoutManager;

//    @FragmentArg
//    String id;

    @FragmentArg
    GoodsTypeModel mGoodsTypeModel;


    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        recyclerView.setHasFixedSize(false);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.getMoreData(mGoodsTypeModel.ChildGoodsType);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<GoodsTypeModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, GoodsTypeModel obj, int position) {
                CommonSearchResultActivity_.intent(CommonCategoryFragment.this).goodsTypeId(obj.GoodsTypeId).isStart(true).start();
            }
        });
    }
}
