package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.RebuiltRecommendedGoods;
import com.zczczy.leo.fuwuwangapp.tools.DisplayUtil;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/27.
 */
@EViewGroup(R.layout.fragment_home_item)
public class RecommendedGoodsItemView extends ItemView<RebuiltRecommendedGoods> {

    @ViewById
    ImageView img_pic;

    @ViewById
    LinearLayout ll_root;

    @ViewById
    TextView txt_product_name, txt_rmb, txt_plus, txt_home_lb;

    @StringRes
    String home_rmb, home_lb, special;

    Context context;

    public RecommendedGoodsItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void init(Object... objects) {

        int temp = DisplayUtil.dip2px(context, 177);

        if (DisplayUtil.getDensityDpi(context) >= 400 && DisplayUtil.getDensityDpi(context) < 480) {
            temp = DisplayUtil.dip2px(context, 177 * 480 / DisplayUtil.getDensityDpi(context));
        }
        LinearLayout.LayoutParams linearLayout = (LayoutParams) ll_root.getLayoutParams();
        if (viewHolder.getAdapterPosition() % 2 == 0) {
            linearLayout.setMargins(DisplayUtil.dip2px(context, 3.3f), 0, 0, DisplayUtil.dip2px(context, 6.6f));
        } else {
            linearLayout.setMargins(0, 0, DisplayUtil.dip2px(context, 3.3f), DisplayUtil.dip2px(context, 6.6f));
        }
        ll_root.setLayoutParams(linearLayout);
        img_pic.setLayoutParams(new LinearLayout.LayoutParams(temp, temp));
        if (!StringUtils.isEmpty(_data.GoodsImgSl)) {
            Glide.with(context).load(_data.GoodsImgSl)
                    .centerCrop()
                    .crossFade()
                    .placeholder(R.drawable.goods_default)
                    .error(R.drawable.goods_default)
                    .into(img_pic);
        }
        txt_product_name.setText(_data.GodosName);
        if (_data.GoodsLBPrice > 0 && Double.valueOf(_data.GoodsPrice) > 0) {
            txt_rmb.setVisibility(VISIBLE);
            txt_plus.setVisibility(VISIBLE);
            txt_home_lb.setVisibility(VISIBLE);
            txt_rmb.setText(String.format(home_rmb, _data.GoodsPrice));
            txt_home_lb.setText(String.format(home_lb, _data.GoodsLBPrice));
        } else if (_data.GoodsLBPrice > 0) {
            txt_rmb.setVisibility(GONE);
            txt_plus.setVisibility(GONE);
            txt_home_lb.setVisibility(VISIBLE);
            txt_home_lb.setText(String.format(home_lb, _data.GoodsLBPrice));
        } else if (Double.valueOf(_data.GoodsPrice) > 0) {
            txt_rmb.setVisibility(VISIBLE);
            txt_plus.setVisibility(GONE);
            txt_home_lb.setVisibility(GONE);
            txt_rmb.setText(String.format(home_rmb, _data.GoodsPrice));
        }
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
