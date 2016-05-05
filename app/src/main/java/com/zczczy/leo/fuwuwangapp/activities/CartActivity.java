package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.CartAdapter;
import com.zczczy.leo.fuwuwangapp.model.CartModel;
import com.zczczy.leo.fuwuwangapp.model.CheckOutModel;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/5/1.
 */
@EActivity(R.layout.activity_cart)
public class CartActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    CartAdapter myAdapter;

    @ViewById
    LinearLayout ll_checkout;

    @ViewById
    TextView txt_total_rmb, txt_total_lb;

    @ViewById
    CheckBox cb_all;

    LinearLayoutManager linearLayoutManager;


    @AfterViews
    void afterView() {
        recyclerView.setHasFixedSize(false);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.getMoreData();
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Click
    void ll_checkout() {
        //定义一个临时集合
        List<CheckOutModel> tempList = new ArrayList<>();
        //添加店铺
        for (CartModel cm : myAdapter.getItems()) {
            if (cm.level == 0) {
                CheckOutModel checkOutModel = new CheckOutModel();
                checkOutModel.StoreInfoId = cm.StoreInfoId;
                checkOutModel.StoreName = cm.StoreName;
                checkOutModel.isChecked = cm.isChecked;
                tempList.add(checkOutModel);
            }

        }
        //统计店铺下面的对应商品
        for (CartModel cm : myAdapter.getItems()) {
            if (cm.isChecked && cm.level == 1) {
                for (CheckOutModel checkOutModel : tempList) {
                    if (cm.StoreInfoId.equals(checkOutModel.StoreInfoId)) {
                        checkOutModel.ProductCount += cm.ProductCount;
                        checkOutModel.rmbTotal += cm.GoodsPrice;
                        checkOutModel.lbTotal += cm.GoodsLBPrice;
                    }
                }
            }
        }
        List<CheckOutModel> list = new ArrayList<>();
        //取出有商品的店铺
        for (CheckOutModel com : tempList) {
            if (com.ProductCount > 0) {
                list.add(com);
            }
        }
        AndroidTool.showToast(this, "" + list.size());
    }


    @Click
    void cb_all() {
        if (cb_all.isChecked()) {
            myAdapter.checkAll();
        } else {
            myAdapter.unCheckAll();
        }
    }
}
