package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

import me.himanshusoni.quantityview.QuantityView;

/**
 * Created by Leo on 2016/5/1.
 */
@EViewGroup(R.layout.activity_cart_detail_item)
public class CartDetailItemView extends ItemView<BuyCartInfoList> {

    @ViewById
    CheckBox cb_select;

    @ViewById
    ImageView img_cart_goods_img;

    @ViewById
    TextView txt_cart_goods_product, txt_cart_goods_price, txt_cart_goods_lb_price;

    @ViewById
    QuantityView quantityView;

    @StringRes
    String home_rmb, home_lb;

    Context context;


    public CartDetailItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.getGoodsImgSl())) {
            Picasso.with(context).load(_data.getGoodsImgSl()).error(R.drawable.goods_default).into(img_cart_goods_img);
        }
        txt_cart_goods_product.setText(_data.getGodosName());
        if (_data.getGoodsPrice() > 0) {
            txt_cart_goods_price.setVisibility(VISIBLE);
            txt_cart_goods_price.setText(String.format(home_rmb, _data.getGoodsPrice()));
        } else {
            txt_cart_goods_price.setVisibility(GONE);
        }
        if (_data.getGoodsLBPrice() > 0) {
            txt_cart_goods_lb_price.setVisibility(VISIBLE);
            txt_cart_goods_lb_price.setText(String.format(home_lb, _data.getGoodsLBPrice()));
        } else {
            txt_cart_goods_lb_price.setVisibility(GONE);
        }
        quantityView.setQuantity(_data.getProductCount());
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
