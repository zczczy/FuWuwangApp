package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/5.
 */
@EViewGroup(R.layout.pre_order_item)
public class PreOrderItemView extends ItemView<BuyCartInfoList> {


    @ViewById
    ImageView img_cart_goods_img;

    @ViewById
    TextView txt_cart_goods_product, txt_cart_goods_price, txt_cart_goods_lb_price, txt_num;

    Context context;

    @StringRes
    String home_rmb, home_lb;

    public PreOrderItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.GoodsImgSl)) {
            Picasso.with(context).load(_data.GoodsImgSl).placeholder(R.drawable.goods_default).error(R.drawable.goods_default).into(img_cart_goods_img);
        }
        txt_cart_goods_product.setText(_data.GodosName);
        if (_data.GoodsPrice > 0) {
            txt_cart_goods_price.setVisibility(VISIBLE);
            txt_cart_goods_price.setText(String.format(home_rmb, _data.GoodsPrice));
        } else {
            txt_cart_goods_price.setVisibility(GONE);
        }
        if (_data.GoodsLBPrice > 0) {
            txt_cart_goods_lb_price.setVisibility(VISIBLE);
            txt_cart_goods_lb_price.setText(String.format(home_lb, _data.GoodsLBPrice));
        } else {
            txt_cart_goods_lb_price.setVisibility(GONE);
        }
        txt_num.setText("x" + _data.ProductCount);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
