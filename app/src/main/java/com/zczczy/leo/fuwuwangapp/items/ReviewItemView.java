package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

/**
 * Created by leo on 2016/5/8.
 */
@EViewGroup(R.layout.activity_review_item)
public class ReviewItemView extends ItemView<OrderDetailModel> {

    @ViewById
    ImageView img_avatar;

    @ViewById
    TextView txt_name, txt_des;

    @StringRes
    String text_order_no;

    Context context;

    public ReviewItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.GoodsImgSl)) {
            Picasso.with(context).load(_data.GoodsImgSl).placeholder(R.drawable.goods_default).error(R.drawable.goods_default).into(img_avatar);
        }
        txt_name.setText(_data.ProductName);
        txt_des.setText(String.format(text_order_no, _data.GoodsDesc));
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
