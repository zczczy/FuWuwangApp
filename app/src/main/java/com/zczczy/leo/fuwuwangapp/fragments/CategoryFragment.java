package com.zczczy.leo.fuwuwangapp.fragments;

import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.marshalchen.ultimaterecyclerview.divideritemdecoration.HorizontalDividerItemDecoration;
import com.squareup.otto.Subscribe;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity_;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CommonCategoryAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.FirstCategoryAdapter;
import com.zczczy.leo.fuwuwangapp.listener.OttoBus;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_category)
public class CategoryFragment extends BaseFragment {


    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    FirstCategoryAdapter myAdapter;

    @Bean
    OttoBus bus;


    Paint paint = new Paint();

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
        myAdapter.getMoreData();
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<GoodsTypeModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, GoodsTypeModel obj, int position) {
                changeFragment(obj.GoodsTypeId + "");
            }
        });
        paint.setStrokeWidth(1);
        paint.setColor(line_color);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(0, 30).paint(paint).build());

        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity_.intent(CategoryFragment.this).start();
            }
        });

        myTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public void notifyUI(BaseModel bm) {
        if (bm.Successful) {
            changeFragment(myAdapter.getItemData(0).GoodsTypeId + "");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            bus.unregister(this);
        } else {
            bus.register(this);
        }
    }

}