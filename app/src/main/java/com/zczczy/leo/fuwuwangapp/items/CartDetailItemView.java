package com.zczczy.leo.fuwuwangapp.items;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.CartActivity;
import com.zczczy.leo.fuwuwangapp.adapters.BaseRecyclerViewAdapter;
import com.zczczy.leo.fuwuwangapp.adapters.CartAdapter;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.CartModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.HashMap;

import me.himanshusoni.quantityview.QuantityView;

/**
 * Created by Leo on 2016/5/1.
 */
@EViewGroup(R.layout.activity_cart_detail_item)
public class CartDetailItemView extends ItemView<CartModel> implements QuantityView.OnQuantityChangeListener {

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

    @RestService
    MyDotNetRestClient myRestClient;

    BaseViewHolder baseViewHolder;

    @Pref
    MyPrefs_ pre;

    int preCount;

    Context context;

    CartActivity cartActivity;

    CartAdapter cartAdapter;

    public CartDetailItemView(Context context) {
        super(context);
        this.context = context;
        cartActivity = (CartActivity) context;
    }

    @AfterViews
    void afterView() {
        quantityView.setQuantityClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        quantityView.setOnQuantityChangeListener(this);
    }


    @Override
    protected void init(Object... objects) {
        cartAdapter = (CartAdapter) objects[0];
        baseViewHolder = (BaseViewHolder) objects[1];
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
        cb_select.setChecked(_data.isChecked);
        quantityView.setQuantity(_data.ProductCount);
        preCount = _data.ProductCount;

    }

    @Click
    void cb_select() {
        _data.isChecked = cb_select.isChecked();
        //设置临时变量
        boolean temp = false;
        //判断是否全部被选中
        for (CartModel cm : cartAdapter.getItems()) {
            if (_data.StoreInfoId.equals(cm.StoreInfoId) && cm.level == 1 && !cm.isChecked) {
                temp = false;
                break;
            } else {
                temp = true;
            }
        }
        for (CartModel cm : cartAdapter.getItems()) {
            //如果 子节点全部被选中(即 temp= true) 并且此时的状态是checked=true; 选中父节点
            if (_data.StoreInfoId.equals(cm.StoreInfoId) && cm.level == 0) {
                cm.isChecked = temp;
            }
        }
        cartAdapter.notifyDataSetChanged();
        cartActivity.setTotalMoney();
    }

    @Override
    public void onQuantityChanged(int newQuantity, boolean programmatically) {

        if (!programmatically) {
            AndroidTool.showLoadDialog(context);
            if (newQuantity > preCount) {
                addShoppingCart();
            } else {
                subShoppingCart();
            }
        }
        if (newQuantity == quantityView.getMinQuantity()) {
            quantityView.getChildAt(0).setEnabled(false);
            quantityView.getChildAt(2).setEnabled(true);
        } else if (newQuantity == quantityView.getMaxQuantity()) {
            quantityView.getChildAt(0).setEnabled(true);
            quantityView.getChildAt(2).setEnabled(false);
        } else {
            quantityView.getChildAt(0).setEnabled(true);
            quantityView.getChildAt(2).setEnabled(true);
        }
    }

    @Background(serial = "addShopping")
    void addShoppingCart() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        HashMap<String, String> map = new HashMap<>();
        map.put("GoodsInfoId", _data.GoodsInfoId);
        afterAddShoppingCart(myRestClient.addShoppingCart(map));
    }

    @UiThread
    void afterAddShoppingCart(BaseModel bm) {
        AndroidTool.dismissLoadDialog();
        if (bm == null) {
            AndroidTool.showToast(context, "商品添加失败");
        } else if (!bm.Successful) {
            AndroidTool.showToast(context, bm.Error);
        } else {
            _data.ProductCount++;
            cartActivity.setTotalMoney();
            cartAdapter.notifyItemChanged(baseViewHolder.getAdapterPosition());
        }
    }

    @Background(serial = "subShopping")
    void subShoppingCart() {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        HashMap<String, String> map = new HashMap<>();
        map.put("GoodsInfoId", _data.GoodsInfoId);
        afterAubShoppingCart(myRestClient.subShoppingCart(map));
    }

    @UiThread
    void afterAubShoppingCart(BaseModel bm) {
        AndroidTool.dismissLoadDialog();
        if (bm == null) {
            AndroidTool.showToast(context, "修改失败");
        } else if (!bm.Successful) {
            AndroidTool.showToast(context, bm.Error);
        } else {
            _data.ProductCount--;
            cartActivity.setTotalMoney();
            cartAdapter.notifyItemChanged(baseViewHolder.getAdapterPosition());
        }
    }


    @Override
    public void onLimitReached() {
//        if (preCount == quantityView.getMaxQuantity()) {
//            quantityView.setEnabled(false);
//
//        } else {
//
//        }

    }


    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }

}
