package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Goods;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/5/5.
 */
@EViewGroup(R.layout.second_category_goods_item)
public class SecondCategoryGoodsItemView extends ItemView<Goods> {

    @ViewById
    ImageView pic;

    @ViewById
    TextView goods_name, goods_describe, txt_normal, txt_longbi, txt_add_price, txt_review_count, txt_review_pre;

    @ViewById
    LinearLayout ll_longbi;

    Context context;

    @StringRes
    String home_rmb, home_lb, text_review_count, text_review_pre, add_money;

    public SecondCategoryGoodsItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.GoodsImgSl)) {
            Glide.with(context)
                    .load(_data.GoodsImgSl)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.goods_default)
                    .error(R.drawable.goods_default)
                    .into(pic);

        }

        goods_name.setText(_data.GodosName);
        goods_describe.setText(_data.GoodsDesc);
        if ("0".equals(_data.IsLbProduct)) {
            ll_longbi.setVisibility(View.GONE);
            txt_normal.setVisibility(View.VISIBLE);
            txt_normal.setText(String.format(home_rmb, _data.GoodsPrice));

        } else {
            ll_longbi.setVisibility(View.VISIBLE);
            txt_normal.setVisibility(View.GONE);
            txt_longbi.setText(_data.GoodsLBPrice);
            txt_add_price.setText("");
            if (_data.GoodsPrice != null) {
                txt_add_price.setText(String.format(add_money, _data.GoodsPrice));
            }
        }
        txt_review_count.setText(String.format(text_review_count, _data.PjNum));
        txt_review_pre.setText(String.format(text_review_pre, _data.PJBfb));

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
