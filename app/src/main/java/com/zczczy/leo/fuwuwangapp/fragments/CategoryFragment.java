package com.zczczy.leo.fuwuwangapp.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CommonCategoryAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;

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

    @Bean
    CommonCategoryAdapter myAdapter;

    @Bean
    OttoBus bus;

    CommonCategoryFragment commonCategoryFragment;

    LinearLayoutManager linearLayoutManager;

    FragmentManager fragmentManager;

    @AfterInject
    void afterInject() {
        fragmentManager = getFragmentManager();
    }

    @AfterViews
    void afterView() {
        bus.register(this);
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.getMoreData(0);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<GoodsTypeModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, GoodsTypeModel obj, int position) {
                changeFragment(obj.GoodsTypeId + "");
            }
        });
    }

    void changeFragment(String id) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        commonCategoryFragment = CommonCategoryFragment_.builder().id(id).build();
        transaction.replace(R.id.common_fragment, commonCategoryFragment);
        transaction.commit();
    }

    @Subscribe
    public void notifyUI(BaseModelJson<Object> bm) {
        if(bm.Successful){
            changeFragment(myAdapter.getItemData(0).GoodsTypeId + "");
        }
    }


}
