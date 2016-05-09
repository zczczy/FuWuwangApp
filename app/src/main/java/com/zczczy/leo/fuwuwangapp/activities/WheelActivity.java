package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.Lottery;
import com.zczczy.leo.fuwuwangapp.model.LotteryConfig;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.LuckDialog;
import com.zczczy.leo.fuwuwangapp.viewgroup.RotateView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FromHtml;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_wheel)
public class WheelActivity extends BaseActivity implements Runnable {

    @ViewById
    RotateView rotate_view;

    @ViewById
    ImageView id_start_btn, img_luck_record;

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @ViewById
    @FromHtml(R.string.lottery_introduction)
    TextView txt_lottery_introduction;

    @ViewById
    @FromHtml(R.string.tips)
    TextView txt_tips;

    @ViewById
    TextView txt_times;

    @Bean
    MyErrorHandler myErrorHandler;

    @StringRes
    String lottery_introduce, my_lottery_record, left_times, zero_times, thanks_for_you_participation;

    @Extra
    int times;

    int mCurDegree = 0;//当前旋转角度

    boolean isRunning, canLuck;

    int luck = -200;

    @AfterInject
    void afterInject() {
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        isRunning = true;
        canLuck = true;
        AndroidTool.showLoadDialog(this);
        getLotteryConfigInfo();
        txt_times.setText(String.format(left_times, times));
    }

    @Background
    void getLotteryConfigInfo() {
        afterGetLotteryConfigInfo(myDotNetRestClient.getLotteryConfigInfo());
    }

    @UiThread
    void afterGetLotteryConfigInfo(BaseModelJson<LotteryConfig> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null && bmj.Successful) {
            if (bmj.Data != null && !StringUtils.isEmpty(bmj.Data.BigImgUrl)) {
                Picasso.with(this).load(bmj.Data.BigImgUrl).into(rotate_view);
            }
            if (bmj.Data != null && !StringUtils.isEmpty(bmj.Data.HandImgUrl)) {
                Picasso.with(this).load(bmj.Data.HandImgUrl).into(id_start_btn);
            }
        } else {
            finish();
        }
    }

    @Click
    void id_start_btn() {
        if (isNetworkAvailable(this)) {
            if (canLuck && times > 0) {
                canLuck = false;
                isRunning = true;
                times--;
                txt_times.setText(String.format(left_times, times));
                new Thread(this).start();
                lottery();
            } else if (times == 0) {
                AndroidTool.showToast(this, zero_times);
            }
        } else {
            AndroidTool.showToast(this, no_net);
        }

    }

    @Background(delay = 1000)
    void lottery() {
        afterLottery(myDotNetRestClient.lottery(pre.username().get()));
    }

    @UiThread
    void afterLottery(BaseModelJson<Lottery> bmj) {
        LuckDialog.Builder builder = new LuckDialog.Builder(this);
        builder.setCloseButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPostiveButtonClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                LotteryInfoRecordActivity_.intent(WheelActivity.this).title(my_lottery_record).method(0).start();
            }
        });
        if (bmj != null && bmj.Successful) {
            luck = bmj.Data.PanNo;
            if (luck == 8) {
                builder.setTitle(bmj.Data.ProductName);
                builder.setMessage("还有" + times + "次机会");
            } else {
                builder.setMessage(bmj.Data.ProductName);
            }
        } else {
            luck = 8;
            builder.setTitle(thanks_for_you_participation);
            builder.setMessage("还有" + times + "次机会");
        }
        LuckDialog ld = builder.create();
        ld.setCanceledOnTouchOutside(false);
        ld.show();
    }


    @UiThread
    void rotation() {
        rotate_view.setRotation(mCurDegree);
        rotate_view.postInvalidate();
    }

    @Override
    public void finish() {
        isRunning = false;
        super.finish();
    }


    @Override
    public void run() {
        while (isRunning) {
            if (mCurDegree / 360 == 1) {
                mCurDegree = 0;
            }
            if ((luck - 1) * 45 < mCurDegree && mCurDegree < (luck) * 45) {
                int temp = (int) (Math.random() * 25);
                mCurDegree += temp;
                if ((luck - 1) * 45 >= mCurDegree || mCurDegree >= (luck) * 45) {
                    mCurDegree -= temp;
                }
                Log.e("mCurDegree", mCurDegree + "");
                Log.e("luck", luck + "");
                Log.e("Math.random()", temp + "");
                luck = -200;
                isRunning = false;
                canLuck = true;
            } else {
                mCurDegree += 5;
            }
            rotation();
            SystemClock.sleep(6);
        }
    }

    @Click
    void txt_lottery_introduction() {
        CommonWebViewActivity_.intent(this).title(lottery_introduce)
                .methodName(MyApplication.DETAILPAGE + MyApplication.LOTTERYDIST).start();
    }

    @Override
    public void onBackPressed() {
        if (times>0) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("提示").setMessage("返回将放弃本次所有次数抽奖资格，是否确认").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();

        } else {
            finish();
        }
    }

}