package com.zczczy.leo.fuwuwangapp.activities;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.items.GoodsCommentsItemView;
import com.zczczy.leo.fuwuwangapp.items.GoodsCommentsItemView_;
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
import com.zczczy.leo.fuwuwangapp.viewgroup.MyScrollView;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.HashMap;

/**
 * Created by Leo on 2016/4/29.
 */
@EActivity(R.layout.activity_goods_detail_info)
public class GoodsDetailInfoActivity extends BaseActivity implements MyScrollView.OnScrollListener, BaseSliderView.OnSliderClickListener {

    @ViewById
    MyScrollView myScrollView;

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    RelativeLayout theViewStay;

    @ViewById
    View mBuyLayout;

    @ViewById
    LinearLayout parent, ll_rebate, ll_store, ll_review, ll_goods_by;

    @ViewById
    TextView goods_name, goods_describe, goods_by, goods_kucun, goods_knows, txt_rebate, txt_rmb, txt_plus, txt_home_lb;

    @ViewById
    CardView card_buy;

    @Extra
    String goodsId;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myRestClient;

    @ViewById
    SliderLayout sliderLayout;

    @StringRes
    String home_rmb, home_lb, goods_no_store;

    boolean isCanBuy;

    String storeId;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        myScrollView.setOnScrollListener(this);
        // 当布局的状态或者控件的可见性发生改变回调的接口
        parent.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // 这一步很重要，使得上面的购买布局和下面的购买布局重合
                        onScroll(myScrollView.getScrollY());
                    }
                });
        getGoodsDetailById(goodsId);
        getGoodsComments(goodsId);

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

    @Background
    void getGoodsDetailById(String goodsInfoId) {
        afterGetGoodsDetailById(myRestClient.getGoodsDetailById(goodsInfoId));
    }

    @UiThread
    void afterGetGoodsDetailById(BaseModelJson<Goods> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            if (Constants.GOODS_STATE_UP.equals(bmj.Data.GoodsDelStatus) && Constants.GOODS_STATE_PASS.equals(bmj.Data.GoodsCheckStatus)) {
                goods_name.setText(bmj.Data.GodosName);
                goods_describe.setText(bmj.Data.GoodsDesc);
                ll_goods_by.setVisibility(("1".equals(bmj.Data.GoodsType)) ? View.GONE : View.VISIBLE);
                goods_by.setText(bmj.Data.GoodsIsBy);
                //判断库存是否大于0
                isCanBuy = Integer.valueOf(bmj.Data.GoodsStock) > 0;
                goods_kucun.setText(isCanBuy ? bmj.Data.GoodsStock : "0");
                goods_knows.setText(bmj.Data.GoodsPurchaseNotes);
                txt_rebate.setText(bmj.Data.TempDisp);
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
                    DefaultSliderView textSliderView = new DefaultSliderView(this);
                    textSliderView.image(nb.GoodsImgUrl);
                    textSliderView.setOnSliderClickListener(this);
                    sliderLayout.addSlider(textSliderView);
                }
                if (bmj.Data.GoodsImgList == null || bmj.Data.GoodsImgList.size() <= 1) {
                    sliderLayout.stopAutoCycle();
                } else {
                    sliderLayout.startAutoCycle();
                }
                storeId = bmj.Data.StoreInfoId;
            } else {
                AndroidTool.showToast(this, "该商品已下架");
                finish();
            }

        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }

    @Click
    void ll_store() {
        StoreInformationActivity_.intent(this).storeId(storeId).start();
    }

    @Click
    void ll_all_review() {
        GoodsCommentsActivity_.intent(this).goodsId(goodsId).start();
    }

    @Click
    void txt_buy() {
        if (isCanBuy) {
            if (!checkUserIsLogin()) {
                LoginActivity_.intent(this).start();
            } else {
                PreOrderActivity_.intent(this).goodsInfoId(goodsId).orderCount(1).start();
            }
        } else {
            AndroidTool.showToast(this, goods_no_store);
        }

    }

    @Click
    void img_cart() {
        if (isCanBuy) {
            if (!checkUserIsLogin()) {
                LoginActivity_.intent(this).start();
            } else {
                AndroidTool.showLoadDialog(this);
                addShoppingCart(goodsId);
            }
        } else {
            AndroidTool.showToast(this, goods_no_store);
        }
    }

    /**
     * 添加商品
     *
     * @param goodsId
     */
    @Background
    void addShoppingCart(String goodsId) {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        HashMap<String, String> map = new HashMap<>();
        map.put("GoodsInfoId", goodsId);
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


    /**
     * 控制购买浮动条的 位置 上面的购买布局（theViewStay）和下面的购买布局（mBuyLayout）重合起来了
     * layout()这个方法是确定View的大小和位置的
     * ，然后将其绘制出来，里面的四个参数分别是View的四个点的坐标，他的坐标不是相对屏幕的原点，而且相对于他的父布局来说的，
     */
    @Override
    public void onScroll(int scrollY) {
        int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
        theViewStay.layout(0, mBuyLayout2ParentTop, theViewStay.getWidth(), mBuyLayout2ParentTop + theViewStay.getHeight());
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
