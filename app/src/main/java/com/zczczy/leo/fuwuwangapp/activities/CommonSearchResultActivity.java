package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseUltimateRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.GoodsAdapters;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2016/5/6.
 */
@EActivity(R.layout.activity_common_search_result)
public class CommonSearchResultActivity extends BaseUltimateRecyclerViewActivity<Goods> {

    @ViewById
    TextView empty_view;

    @Extra
    String searchContent;

    @Extra
    int goodsTypeId;

    @Extra
    String goodsType, storeId;

    @Extra
    boolean isStart;

    @ViewById
    RadioButton rb_price, rb_filter;

    String priceMin, priceMax;

    View view;

    EditText edt_min_price, edt_max_price;

    PopupWindow popupWindow;

    @ViewById
    TextView txt_title_search;

    boolean isSelected;

    String orderBy = Constants.PRICE_FILTER;


    @Bean
    void setAdapter(GoodsAdapters myAdapter) {
        this.myAdapter = myAdapter;
    }


    @AfterViews
    void afterView() {
        empty_view.setText(empty_search);
        txt_title_search.setText(searchContent);
        myTitleBar.setCustomViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart) {
                    SearchActivity_.intent(CommonSearchResultActivity.this).start();
                }
                finish();
            }
        });
        myAdapter.setOnItemClickListener(new BaseUltimateRecyclerViewAdapter.OnItemClickListener<Goods>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Goods obj, int position) {
                GoodsDetailActivity_.intent(CommonSearchResultActivity.this).goodsId(obj.GoodsInfoId).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
        isSelected = true;
    }

    @Click
    void rb_filter() {
        if (rb_filter.isChecked()) {
            orderBy = Constants.PRICE_FILTER;
            isRefresh = true;
            showProperties();
        }
    }

    @CheckedChange
    void rb_sell_count(boolean isChecked) {
        if (isChecked) {
            orderBy = Constants.SELL_COUNT;
            isRefresh = true;
            afterLoadMore();
        }
    }

    @Click
    void rb_price() {
        if (rb_price.isChecked() && isSelected) {
            isRefresh = true;
            isSelected = false;
            rb_price.setSelected(false);
            orderBy = Constants.PRICE_ASC;
            afterLoadMore();

        } else if (rb_price.isChecked() && !isSelected) {
            isRefresh = true;
            isSelected = true;
            rb_price.setSelected(true);
            orderBy = Constants.PRICE_DESC;
            afterLoadMore();
        }
    }

    void showProperties() {
        if (popupWindow == null) {
            view = layoutInflater.inflate(R.layout.filter_popup, null);
            edt_min_price = (EditText) view.findViewById(R.id.edt_min_price);
            edt_max_price = (EditText) view.findViewById(R.id.edt_max_price);
            view.findViewById(R.id.txt_reset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edt_min_price.setText("");
                    edt_max_price.setText("");
                }
            });
            view.findViewById(R.id.txt_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    priceMin = edt_min_price.getText().toString();
                    priceMax = edt_max_price.getText().toString();
                    closeInputMethod(view);
                    popupWindow.dismiss();
                    afterLoadMore();
                }
            });
//            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindow = new PopupWindow(view, DisplayUtil.dip2px(this, 220), DisplayUtil.dip2px(this, 110), true);
//            //实例化一个ColorDrawable颜色为半透明
//            ColorDrawable dw = new ColorDrawable(0xb0000000);
//            //设置SelectPicPopupWindow弹出窗体的背景
//            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    closeInputMethod(view);
                }
            });
        }
        popupWindow.showAsDropDown(rb_filter);
    }


    void afterLoadMore() {
        myAdapter.getMoreData(pageIndex, Constants.PAGE_COUNT, isRefresh, 1, "0", "2", searchContent, orderBy, priceMin, priceMax, storeId);
    }

    @Override
    public void finish() {
        bus.unregister(this);
        setResult(RESULT_OK);
        super.finish();
    }
}
