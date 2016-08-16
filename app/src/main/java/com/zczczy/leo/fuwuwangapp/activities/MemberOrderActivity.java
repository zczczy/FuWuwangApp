package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.MemberOrderAdapter;
import com.zczczy.leo.fuwuwangapp.model.PayResult;
import com.zczczy.leo.fuwuwangapp.model.ShopOrder;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/7.
 */
@EActivity(R.layout.activity_member_order)
public class MemberOrderActivity extends BaseUltimateRecyclerViewActivity<ShopOrder> {

    @Extra
    String title;

    @ViewById
    TextView empty_view;

    @Extra
    int orderState;

    @Bean
    void setAdapter(MemberOrderAdapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        myTitleBar.setTitle(title);
        empty_view.setText(String.format(empty_order, title));
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<ShopOrder>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, ShopOrder obj, int position) {
                OrderDetailActivity_.intent(MemberOrderActivity.this).orderId(obj.MOrderId).startForResult(1000);
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, orderState);
    }

    @Override
    public void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    public void onResume() {
        bus.register(this);
        super.onResume();
    }

    @Subscribe
    public void NotifyUI(PayResp resp) {
        switch (resp.errCode) {
            case 0:
                AndroidTool.showToast(this, "支付成功");
                break;
            case -1:
                AndroidTool.showToast(this, "支付异常");
                break;
            case -2:
                AndroidTool.showToast(this, "您取消了支付");
                break;
        }
        OrderDetailActivity_.intent(this).orderId(resp.extData).startForResult(1000);
    }

    @Subscribe
    public void NotifyUI(PayResult payResult) {
        switch (payResult.getResultStatus()) {
            case "9000":
                AndroidTool.showToast(this, "支付成功");
                break;
            case "8000":
                AndroidTool.showToast(this, "支付结果确认中");
                break;
            case "4000":
                AndroidTool.showToast(this, "订单支付失败");
                break;
            case "6001":
                AndroidTool.showToast(this, "用户中途取消");
                break;
            case "6002":
                AndroidTool.showToast(this, "网络连接出错");
                break;
            default: {
                AndroidTool.showToast(this, "网络连接出错");
            }
        }
        OrderDetailActivity_.intent(this).orderId(payResult.getOrderId()).startForResult(1000);
    }

    @OnActivityResult(1000)
    void onPay(int resultCode) {
        if (resultCode == RESULT_OK) {
            isRefresh = true;
            pageIndex = 1;
            afterLoadMore();
        }
    }

}
