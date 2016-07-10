package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.PreOrderActivity_;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.GoodsAttribute;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyViewGroup;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorStateListRes;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.himanshusoni.quantityview.QuantityView;

/**
 * Created by Leo on 2016/5/7.
 */
@EViewGroup(R.layout.goods_properties_popup)
public class GoodsPropertiesPopup extends LinearLayout {

    @ViewById
    LinearLayout root, ll_child;

    @ViewById
    MyViewGroup myViewGroup;

    @ViewById
    QuantityView quantityView;

    @ViewById
    ImageView img_goods;

    @ViewById
    Button btn_confirm;

    @ColorStateListRes
    ColorStateList goods_properties_color;

    @ViewById
    TextView txt_store_count, txt_rmb, txt_plus, txt_home_lb;

    @StringRes
    String home_rmb, home_lb;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @Pref
    MyPrefs_ pre;

    Goods goods;

    Context context;

    PopupWindow popupWindow;

    List<TextView> textViews;

    boolean isCart;

    int selectedId;

    public GoodsPropertiesPopup(Context context) {
        super(context);
        this.context = context;
    }

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        quantityView.setQuantityClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setCart(boolean cart) {
        isCart = cart;
    }

    public void setData(PopupWindow popupWindow, Goods goods) {
        this.popupWindow = popupWindow;
        this.goods = goods;
        textViews = new ArrayList<>();
        int i = 0;
        if (!StringUtils.isEmpty(goods.GoodsImgSl)) {
            Glide.with(context).load(goods.GoodsImgSl)
                    .centerCrop()
                    .crossFade()
                    .error(R.drawable.goods_default)
                    .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                    .placeholder(R.drawable.goods_default)
                    .into(img_goods);
        }
        for (final GoodsAttribute g : goods.GoodsAttributeList) {
            final TextView textView = new TextView(context);
            textView.setText(g.GoodsAttributeName);
            textView.setBackgroundResource(R.drawable.goods_properties);
            textView.setTextColor(goods_properties_color);
            textView.setClickable(true);
            textView.setPadding(10, 10, 10, 10);
            textViews.add(textView);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (TextView tv : textViews) {
                        tv.setSelected(false);
                    }
                    textView.setSelected(true);
                    txt_store_count.setText(String.valueOf(g.GoodsAttributeStock));
                    if (Float.valueOf(g.GoodsAttributePrice) > 0 && Integer.valueOf(g.GoodsAttributeLbPrice) > 0) {
                        txt_rmb.setVisibility(View.VISIBLE);
                        txt_plus.setVisibility(View.VISIBLE);
                        txt_rmb.setText(String.format(home_rmb, g.GoodsAttributePrice));
                        txt_home_lb.setText(String.format(home_lb, g.GoodsAttributeLbPrice));
                    } else if (Float.valueOf(g.GoodsAttributePrice) > 0) {
                        txt_rmb.setVisibility(View.VISIBLE);
                        txt_plus.setVisibility(View.GONE);
                        txt_home_lb.setVisibility(View.GONE);
                        txt_rmb.setText(String.format(home_rmb, g.GoodsAttributePrice));
                    } else if (Integer.valueOf(g.GoodsAttributeLbPrice) > 0) {
                        txt_rmb.setVisibility(View.GONE);
                        txt_plus.setVisibility(View.GONE);
                        txt_home_lb.setVisibility(View.VISIBLE);
                        txt_home_lb.setText(String.format(home_lb, g.GoodsAttributeLbPrice));
                    }
                    selectedId = g.GoodsAttributeId;
                }
            });
            if (i == 0) {
                textView.performClick();
            }
            myViewGroup.addView(textView);
            i++;
        }
    }

    @Click
    void btn_confirm() {
        if (isCart) {
            AndroidTool.showLoadDialog(context);
            addShoppingCart();
        } else {
            popupWindow.dismiss();
            PreOrderActivity_.intent(context).goodsInfoId(goods.GoodsInfoId).StoreInfoId(goods.StoreInfoId).GoodsAttributeId(selectedId).orderCount(quantityView.getQuantity()).start();
        }
    }

    /**
     * 添加商品
     *
     * @param
     */
    @Background
    void addShoppingCart() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        HashMap<String, String> map = new HashMap<>();
        map.put("GoodsInfoId", goods.GoodsInfoId);
        map.put("SelCount", String.valueOf(quantityView.getQuantity()));
        map.put("GoodsAttributeId", String.valueOf(selectedId));
        afterAddShoppingCart(myRestClient.addShoppingCart(map));
    }

    /**
     * 添加商品后更新UI
     *
     * @param bm
     */
    @UiThread
    void afterAddShoppingCart(BaseModel bm) {
        AndroidTool.dismissLoadDialog();
        if (bm == null) {
            AndroidTool.showToast(context, "商品添加失败");
        } else if (bm.Successful) {
            popupWindow.dismiss();
            AndroidTool.showToast(context, "商品添加成功");
        } else {
            AndroidTool.showToast(context, bm.Error);
        }
    }

    @Touch
    void root(View v, MotionEvent event) {
        int height = v.getHeight();
        int flHeight = ll_child.getHeight();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height - flHeight && popupWindow != null) {
                popupWindow.dismiss();
            }
        }
    }

    @Click
    void img_close() {
        popupWindow.dismiss();
    }

}
