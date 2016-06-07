package com.zczczy.leo.fuwuwangapp.activities;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.ShippingAddressAdapter;
import com.zczczy.leo.fuwuwangapp.model.MReceiptAddressModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2016/5/4.
 */
@EActivity(R.layout.activity_shipping_address)
public class ShippingAddressActivity extends BaseActivity {

    @ViewById
    RecyclerView recyclerView;

    @Bean(ShippingAddressAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    LinearLayoutManager linearLayoutManager;

    @Extra
    boolean isFinish;

    @AfterViews
    void afterView() {
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<MReceiptAddressModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, MReceiptAddressModel obj, int position) {
                if (isFinish) {
                    Intent intent = new Intent();
                    intent.putExtra("model", obj);
                    setResult(1001, intent);
                    finish();
                }
            }
        });
    }

    @Click
    void btn_add_shipping_address() {
        AddShippingAddressActivity_.intent(this).start();
    }

    public void onResume() {
        super.onResume();
        myAdapter.getMoreData();
    }


}
