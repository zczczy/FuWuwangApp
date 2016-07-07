package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.GoodsTypeModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

/**
 * Created by leo on 2016/5/4.
 */
@EViewGroup(R.layout.fragment_second_category_item)
public class SecondCategoryItemView extends ItemView<GoodsTypeModel> {

    @ViewById
    ImageView img_second_url;

    @ViewById
    TextView txt_second;

    Context context;

    public SecondCategoryItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {
        if (!StringUtils.isEmpty(_data.GoodsTypeIcon)) {
            Glide.with(context).load(_data.GoodsTypeIcon)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.goods_default).error(R.drawable.goods_default).into(img_second_url);
        }
        txt_second.setText(_data.GoodsTypeName);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
