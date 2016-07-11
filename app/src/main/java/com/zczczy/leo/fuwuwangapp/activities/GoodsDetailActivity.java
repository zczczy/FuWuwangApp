package com.zczczy.leo.fuwuwangapp.activities;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsCommentsFragment;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsCommentsFragment_;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsDetailFragment;
import com.zczczy.leo.fuwuwangapp.fragments.GoodsDetailFragment_;
import com.zczczy.leo.fuwuwangapp.items.GoodsCommentsItemView;
import com.zczczy.leo.fuwuwangapp.items.GoodsCommentsItemView_;
import com.zczczy.leo.fuwuwangapp.items.GoodsPropertiesPopup;
import com.zczczy.leo.fuwuwangapp.items.GoodsPropertiesPopup_;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Goods;
import com.zczczy.leo.fuwuwangapp.model.GoodsCommentsModel;
import com.zczczy.leo.fuwuwangapp.model.GoodsImgListModel;
import com.zczczy.leo.fuwuwangapp.model.PagerResult;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;
import com.zczczy.leo.fuwuwangapp.views.GlideSliderView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;

/**
 * @author Created by LuLeo on 2016/7/4.
 *         you can contact me at :361769045@qq.com
 * @since 2016/7/4.
 */
@EActivity(R.layout.activity_goods_detail)
public class GoodsDetailActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    TextView txt_goods_name, txt_rebate,
            txt_store_name, txt_rmb,
            txt_plus, txt_home_lb,
            goods_count, goods_sell_count, goods_by;

    @ViewById
    ImageView txt_coupon;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @ViewById
    RadioButton rb_good_detail, rb_good_review;

    @Extra
    String goodsId;

    @StringRes
    String home_rmb, home_lb, goods_no_store;

    @ViewById
    RelativeLayout parent;

    @ViewById
    LinearLayout ll_goods_by, ll_review;

    @ViewById
    SliderLayout sliderLayout;

    Goods goods;

    boolean isCanBuy;

    boolean isStart;

    FragmentManager fragmentManager;

    GoodsDetailFragment goodsDetailFragment;

    GoodsCommentsFragment goodsCommentsFragment;

    PopupWindow popupWindow;

    GoodsPropertiesPopup goodsPropertiesPopup;

    String linkUrl, PlUrl;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        fragmentManager = getSupportFragmentManager();
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        goodsPropertiesPopup = GoodsPropertiesPopup_.build(this);
        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUserIsLogin()) {
                    CartActivity_.intent(GoodsDetailActivity.this).start();
                } else {
                    LoginActivity_.intent(GoodsDetailActivity.this).start();
                }
            }
        });
        getGoodsDetailById(goodsId);
        getGoodsComments(goodsId);
    }

    @CheckedChange
    void rb_good_detail(boolean isChecked) {
        if (isChecked) {
            changeFragment(linkUrl);
        } else {
            changeFragment(goodsId);
        }
    }

    @Background
    void getGoodsComments(String goodsId) {
        afterGetGoodsComments(myRestClient.getGoodsCommentsByGoodsInfoId(goodsId, 1, 3));
    }

    @UiThread
    void afterGetGoodsComments(BaseModelJson<PagerResult<GoodsCommentsModel>> bmj) {
        if (bmj != null && bmj.Successful) {
            for (GoodsCommentsModel gcm : bmj.Data.ListData) {
                GoodsCommentsItemView goodsCommentsItemView = GoodsCommentsItemView_.build(this);
                goodsCommentsItemView.init(gcm);
                ll_review.addView(goodsCommentsItemView);
                ll_review.addView(layoutInflater.inflate(R.layout.horizontal_line, null));
            }
        }

    }

    void changeFragment(String parameter) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (goodsDetailFragment != null) {
            transaction.hide(goodsDetailFragment);
        }
        if (goodsCommentsFragment != null) {
            transaction.hide(goodsCommentsFragment);
        }
        if (rb_good_detail.isChecked()) {
            if (goodsDetailFragment == null) {
                goodsDetailFragment = GoodsDetailFragment_.builder().linkUrl(parameter).build();
                transaction.add(R.id.goods_detail_fragment, goodsDetailFragment);
            } else {
                transaction.show(goodsDetailFragment);
            }
        } else {
            if (goodsCommentsFragment == null) {
                goodsCommentsFragment = GoodsCommentsFragment_.builder().goodsId(parameter).build();
                transaction.add(R.id.goods_detail_fragment, goodsCommentsFragment);
            } else {
                transaction.show(goodsCommentsFragment);
            }
        }
        //transaction.commit();
        transaction.commitAllowingStateLoss();
    }

    @Background
    void getGoodsDetailById(String goodsInfoId) {
        afterGetGoodsDetailById(myRestClient.getGoodsDetailById(goodsInfoId));
    }

    @UiThread
    void afterGetGoodsDetailById(BaseModelJson<Goods> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (!bmj.Successful) {
            AndroidTool.showToast(this, bmj.Error);
        } else {
            goods = bmj.Data;
            if (Constants.GOODS_STATE_UP.equals(bmj.Data.GoodsDelStatus) && Constants.GOODS_STATE_PASS.equals(bmj.Data.GoodsCheckStatus)) {
                txt_goods_name.setText(bmj.Data.GodosName);
                //判断库存是否大于0
                isCanBuy = Integer.valueOf(bmj.Data.GoodsStock) > 0;
                goods_count.setText(isCanBuy ? bmj.Data.GoodsStock : "0");
                txt_store_name.setText(bmj.Data.StoreInfoName);
                goods_sell_count.setText(String.valueOf(bmj.Data.GoodsXl));
                ll_goods_by.setVisibility(("1".equals(bmj.Data.GoodsType)) ? View.GONE : View.VISIBLE);
                goods_by.setText(bmj.Data.GoodsIsBy);
                ll_goods_by.setVisibility(("1".equals(bmj.Data.GoodsType)) ? View.GONE : View.VISIBLE);
                goods_by.setText(bmj.Data.GoodsIsBy);

                if ("0".equals(bmj.Data.GoodsReturnTicket)) {
                    txt_rebate.setText(bmj.Data.TempDisp);
                    txt_coupon.setSelected(true);
                } else {
                    txt_coupon.setSelected(false);
                    txt_rebate.setText("");
                }

                PlUrl = goodsId;
                changeFragment(bmj.Data.StaticHtmlUrl);
                if (Float.valueOf(bmj.Data.GoodsPrice) > 0 && Integer.valueOf(bmj.Data.GoodsLBPrice) > 0) {
                    txt_rmb.setVisibility(View.VISIBLE);
                    txt_plus.setVisibility(View.VISIBLE);
                    txt_rmb.setText(String.format(home_rmb, bmj.Data.GoodsPrice));
                    txt_home_lb.setText(String.format(home_lb, bmj.Data.GoodsLBPrice));
                } else if (Float.valueOf(bmj.Data.GoodsPrice) > 0) {
                    txt_rmb.setVisibility(View.VISIBLE);
                    txt_plus.setVisibility(View.GONE);
                    txt_home_lb.setVisibility(View.GONE);
                    txt_rmb.setText(String.format(home_rmb, bmj.Data.GoodsPrice));
                } else if (Integer.valueOf(bmj.Data.GoodsLBPrice) > 0) {
                    txt_rmb.setVisibility(View.GONE);
                    txt_plus.setVisibility(View.GONE);
                    txt_home_lb.setVisibility(View.VISIBLE);
                    txt_home_lb.setText(String.format(home_lb, bmj.Data.GoodsLBPrice));
                }
                for (GoodsImgListModel nb : bmj.Data.GoodsImgList) {
                    GlideSliderView textSliderView = new GlideSliderView(this);
                    textSliderView.image(nb.GoodsImgUrl).
                            empty(R.drawable.goods_detail_banner).
                            error(R.drawable.goods_detail_banner).
                            setScaleType(BaseSliderView.ScaleType.CenterInside).
                            setOnSliderClickListener(this);
                    sliderLayout.addSlider(textSliderView);
                }
                if (bmj.Data.GoodsImgList == null || bmj.Data.GoodsImgList.size() <= 1) {
                    isStart = false;
                    sliderLayout.stopAutoCycle();
                } else {
                    isStart = true;
                    sliderLayout.startAutoCycle();
                }
            }
        }
    }

    @Click
    void txt_add_cart() {
        if (!checkUserIsLogin()) {
            LoginActivity_.intent(this).start();
        } else {
            if (goods != null && goods.IsUsing == 1 && goods.GoodsAttributeList != null && goods.GoodsAttributeList.size() > 0) {
                showProperties(true);
            } else {
                AndroidTool.showLoadDialog(this);
                addShoppingCart();
            }
        }
    }

    @Click
    void txt_buy() {
        if (!checkUserIsLogin()) {
            LoginActivity_.intent(this).start();
        } else {
            if (goods != null && goods.IsUsing == 1 && goods.GoodsAttributeList != null && goods.GoodsAttributeList.size() > 0) {
                showProperties(false);
            } else {
                AndroidTool.showLoadDialog(this);
                PreOrderActivity_.intent(this).goodsInfoId(goodsId).StoreInfoId(goods.StoreInfoId).GoodsAttributeId(0).orderCount(1).start();
            }
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
        map.put("GoodsAttributeId", "0");
        map.put("SelCount", "1");
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
            AndroidTool.showToast(this, "商品添加失败");
        } else if (bm.Successful) {
            AndroidTool.showToast(this, "商品添加成功");
        } else {
            AndroidTool.showToast(this, bm.Error);
        }
    }

    void showProperties(boolean isCart) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(goodsPropertiesPopup, ViewGroup.LayoutParams.MATCH_PARENT, parent.getHeight(), true);
            goodsPropertiesPopup.setData(popupWindow, goods);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            popupWindow.setBackgroundDrawable(dw);
        }
        goodsPropertiesPopup.setCart(isCart);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Click
    void ll_review() {
        GoodsCommentsActivity_.intent(this).goodsId(goodsId).start();
    }

    @Click
    void txt_more_review() {
        changeFragment(goodsId);
    }

    @Click
    void txt_store() {
        if (goods != null) {
            StoreInformationActivity_.intent(this).storeId(goods.StoreInfoId).start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderLayout.stopAutoCycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isStart && sliderLayout != null) {
            sliderLayout.startAutoCycle();
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
