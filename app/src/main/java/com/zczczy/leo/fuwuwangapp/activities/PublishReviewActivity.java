package com.zczczy.leo.fuwuwangapp.activities;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModel;
import com.zczczy.leo.fuwuwangapp.model.OrderDetailModel;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * Created by leo on 2016/5/8.
 */
@EActivity(R.layout.activity_publish_review)
public class PublishReviewActivity extends BaseActivity {

    @RestService
    MyDotNetRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @ViewById
    ImageView img_store_pic;

    @ViewById
    EditText edt_review_content;

    @ViewById
    RatingBar ratingBar;

    @Extra
    OrderDetailModel model;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        if (!StringUtils.isEmpty(model.GoodsImgSl)) {
            Picasso.with(this).load(model.GoodsImgSl).error(R.drawable.goods_default).placeholder(R.drawable.goods_default).into(img_store_pic);
        }
    }

    @Click
    void btn_comment() {
        if (ratingBar.getRating() <= 0) {
            AndroidTool.showToast(this, "请选择星级");
        } else if (AndroidTool.checkIsNull(edt_review_content)) {
            AndroidTool.showToast(this, "请输入评论内容");
        } else {
            AndroidTool.showLoadDialog(this);
            int dj = 2;
            if (ratingBar.getRating() < 3) {
                dj = 3;
            } else if (ratingBar.getRating() > 3) {
                dj = 1;
            }
            publishReview(dj);
        }
    }

    @Background
    void publishReview(int dj) {
        myRestClient.setHeader("Token", pre.token().get());
        myRestClient.setHeader("ShopToken", pre.shopToken().get());
        myRestClient.setHeader("Kbn", Constants.ANDROID);
        HashMap<String, Object> map = new HashMap<>();
        String GoodsInfoId = model.GoodsInfoId;
        String MOrderDetailId = model.MOrderDetailId;
        map.put("XNum", (int) ratingBar.getRating());
        map.put("GoodsInfoId", GoodsInfoId);
        map.put("MOrderDetailId", MOrderDetailId);
        map.put("GoodsCommentsDj", dj);
        map.put("GoodsCommentsNr", edt_review_content.getText().toString().trim());
        afterPublishReview(myRestClient.publishReview(map));
    }


    @UiThread
    void afterPublishReview(BaseModel bm) {
        AndroidTool.dismissLoadDialog();
        if (bm == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bm.Successful) {
            AndroidTool.showToast(this, "评论成功");
            setResult(RESULT_OK);
            finish();
        } else {
            AndroidTool.showToast(this, bm.Error);
        }
    }
}
