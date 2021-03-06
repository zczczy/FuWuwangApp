package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.MemberOrderActivity;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/5.
 */
@EViewGroup(R.layout.pre_order_item)
public class PreOrderItemView extends ItemView<OrderDetailModel> {

    @ViewById
    ImageView img_cart_goods_img;

    @ViewById
    TextView txt_cart_goods_product, txt_cart_goods_price, txt_cart_goods_property,
            txt_cart_goods_lb_price, txt_num, txt_ticket, txt_status;

    Context context;

    @StringRes
    String home_rmb, home_lb, text_ticket_no;

    public PreOrderItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.GoodsImgSl)) {
            Glide.with(context).load(_data.GoodsImgSl)
                    .centerCrop().crossFade()
                    .placeholder(R.drawable.goods_default).error(R.drawable.goods_default).into(img_cart_goods_img);
        }
        txt_cart_goods_product.setText(_data.ProductName);
        if (Double.valueOf(_data.ProductPrice) > 0) {
            txt_cart_goods_price.setVisibility(VISIBLE);
            txt_cart_goods_price.setText(String.format(home_rmb, _data.ProductPrice));
        } else {
            txt_cart_goods_price.setVisibility(GONE);
        }
        if (_data.ProductLbCount > 0) {
            txt_cart_goods_lb_price.setVisibility(VISIBLE);
            txt_cart_goods_lb_price.setText(String.format(home_lb, _data.ProductLbCount));
        } else {
            txt_cart_goods_lb_price.setVisibility(GONE);
        }
        txt_cart_goods_property.setText(_data.GoodsAttributeName);
        txt_num.setText("x".concat(String.valueOf(_data.ProductNum)));
        if (!StringUtils.isEmpty(_data.XfNo)) {
            txt_ticket.setVisibility(VISIBLE);
            txt_status.setVisibility(VISIBLE);
            txt_ticket.setText(String.format(text_ticket_no, _data.XfNo.replaceAll("([\\d]{4})", "$1 ")));
            txt_status.setText(_data.XfStatusDisp);
        } else {
            txt_ticket.setVisibility(GONE);
            txt_status.setVisibility(GONE);
        }

        if (context instanceof MemberOrderActivity) {
            txt_ticket.setVisibility(GONE);
            txt_status.setVisibility(GONE);
        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
