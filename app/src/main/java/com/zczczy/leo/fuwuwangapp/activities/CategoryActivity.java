package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CategoryAdapter;
import com.zczczy.leo.fuwuwangapp.fragments.CommonCategoryFragment;
import com.zczczy.leo.fuwuwangapp.fragments.CommonCategoryFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.SecondCategoryGoodsFragment;
import com.zczczy.leo.fuwuwangapp.fragments.SecondCategoryGoodsFragment_;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Leo on 2016/5/21.
 */
@EActivity(R.layout.activity_category)
public class CategoryActivity extends BaseRecyclerViewActivity<GoodsTypeModel> {

    @Extra
    String goodsTypeId, title, goodsType;

    FragmentManager fragmentManager;

    SecondCategoryGoodsFragment secondCategoryGoodsFragment;

    @ViewById
    RadioButton rb_price, rb_filter, rb_sell_count;

    @ViewById
    RadioGroup radio_group;

    String priceMin, priceMax;

    EditText edt_min_price, edt_max_price;

    boolean isRefresh;

    PopupWindow popupWindow;

    View view;

    String orderBy = Constants.SELL_COUNT;

    boolean isSelected;

    GoodsTypeModel currentGoodsTypeModel;

    @Bean
    void setMyAdapter(CategoryAdapter myAdapter) {
        this.myAdapter = myAdapter;
        fragmentManager = getSupportFragmentManager();
    }

    @AfterViews
    void afterView() {
        if (!StringUtils.isEmpty(title)) {
            myTitleBar.setTitle(title);
        }
        recyclerView.removeItemDecoration(itemDecoration);
        priceMin = "0";
        priceMax = "0";
        myAdapter.getMoreData("0", goodsTypeId);
        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<GoodsTypeModel>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, GoodsTypeModel obj, int position) {
                if (!obj.isSelected) {
                    currentGoodsTypeModel = obj;
                    if (rb_sell_count.isChecked()) {
                        rb_sell_count(true);
                    } else {
                        rb_sell_count.setChecked(true);
                    }
                }
            }
        });
    }

    @Click
    void rb_filter() {
        if (rb_filter.isChecked()) {
            orderBy = Constants.PRICE_FILTER;
            isRefresh = true;
            showProperties();
        }
    }


    @Click
    void rb_price() {
        if (rb_price.isChecked() && isSelected) {
            isRefresh = true;
            isSelected = false;
            rb_price.setSelected(false);
            orderBy = Constants.PRICE_ASC;
            changeFragment(currentGoodsTypeModel);
        } else if (rb_price.isChecked() && !isSelected) {
            isRefresh = true;
            isSelected = true;
            rb_price.setSelected(true);
            orderBy = Constants.PRICE_DESC;
            changeFragment(currentGoodsTypeModel);
        }
    }

    @CheckedChange
    void rb_sell_count(boolean isChecked) {
        if (isChecked) {
            orderBy = Constants.SELL_COUNT;
            isRefresh = true;
            changeFragment(currentGoodsTypeModel);
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
                    priceMin = "".equals(edt_min_price.getText().toString()) ? "0" : edt_min_price.getText().toString();
                    priceMax = "".equals(edt_max_price.getText().toString()) ? "0" : edt_max_price.getText().toString();
                    closeInputMethod(view);
                    popupWindow.dismiss();
                    changeFragment(currentGoodsTypeModel);
                }
            });
            popupWindow = new PopupWindow(view, DisplayUtil.dip2px(this, 220), DisplayUtil.dip2px(this, 110), true);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            popupWindow.setBackgroundDrawable(dw);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    closeInputMethod(view);
                }
            });
            popupWindow.setOutsideTouchable(true);
        }
        popupWindow.showAsDropDown(rb_filter);
    }

    public void notifyUI(BaseModelJson<List<GoodsTypeModel>> bm) {
        if (bm.Successful) {
            if (myAdapter.getItemData(0) != null) {
                changeFragment(myAdapter.getItemData(0));
            }
        }
    }

    void changeFragment(GoodsTypeModel model) {
        if (model == null)
            return;
        if (model != currentGoodsTypeModel) {
            if (edt_min_price != null && edt_max_price != null) {
                edt_min_price.setText("");
                edt_max_price.setText("");
            }
            priceMin = "0";
            priceMax = "0";
        }
        currentGoodsTypeModel = model;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int firstPosition = 0;
        int lastPosition = 0;
        for (int i = 0; i < myAdapter.getItems().size(); i++) {
            GoodsTypeModel tempModel = myAdapter.getItems().get(i);
            if (tempModel.GoodsTypeId == model.GoodsTypeId) {
                firstPosition = i;
            }
            if (tempModel.isSelected) {
                lastPosition = i;
                tempModel.isSelected = false;
            }
        }
        model.isSelected = true;
        myAdapter.notifyItemChanged(firstPosition);
        myAdapter.notifyItemChanged(lastPosition);
        linearLayoutManager.scrollToPosition(firstPosition);
        secondCategoryGoodsFragment = SecondCategoryGoodsFragment_.builder().GoodsType(goodsType).GoodsTypeId(model.GoodsTypeId + "").likeName("").orderBy(orderBy).priceMin(priceMin).priceMax(priceMax).build();
        transaction.replace(R.id.common_fragment, secondCategoryGoodsFragment);
        transaction.commit();
    }
}
