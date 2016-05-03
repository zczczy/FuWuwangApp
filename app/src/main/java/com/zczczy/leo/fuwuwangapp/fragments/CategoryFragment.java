package com.zczczy.leo.fuwuwangapp.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CommonCategoryAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends BaseFragment {

    @ViewById
    RecyclerView recyclerView;

    @Bean(CommonCategoryAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    CommonCategoryFragment commonCategoryFragment;

    LinearLayoutManager linearLayoutManager;

    FragmentManager fragmentManager;

    @AfterInject
    void afterInject() {
        fragmentManager = getFragmentManager();
    }

    @AfterViews
    void afterView() {
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.getMoreData(0);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Object obj, int position) {
                changeFragment();
            }
        });
        changeFragment ();
    }

    void changeFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        commonCategoryFragment = CommonCategoryFragment_.builder().build();
        transaction.replace(R.id.common_fragment, commonCategoryFragment);
        transaction.commit();
    }
}
