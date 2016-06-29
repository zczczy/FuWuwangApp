package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.GoodsDetailInfoActivity_;
import com.zczczy.leo.fuwuwangapp.activities.StoreInformationActivity_;
import com.zczczy.leo.fuwuwangapp.model.BuyCartInfoList;

import org.androidannotations.annotations.Click;
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
    TextView txt_cart_goods_product, txt_cart_goods_price, txt_cart_goods_lb_price, txt_num, txt_ticket, txt_status;

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
            Picasso.with(context).load(_data.GoodsImgSl)
                    .resize(200, 200)
                    .placeholder(R.drawable.goods_default).error(R.drawable.goods_default).into(img_cart_goods_img);
        }
        txt_cart_goods_product.setText(_data.GodosName);
        if (Double.valueOf(_data.GoodsPrice) > 0) {
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
        txt_num.setText("x".concat(String.valueOf(_data.ProductCount)));
        if (!StringUtils.isEmpty(_data.XfNo)) {
            txt_ticket.setVisibility(VISIBLE);
            txt_status.setVisibility(VISIBLE);
            txt_ticket.setText(String.format(text_ticket_no, _data.XfNo.replaceAll("([\\d]{4})", "$1 ")));
            txt_status.setText(_data.XfStatusDisp);
        } else {
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
