package com.zczczy.leo.fuwuwangapp.fragments;

import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zczczy.leo.fuwuwangapp.MyApplication;
import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.ChangePasswordActivity_;
import com.zczczy.leo.fuwuwangapp.activities.LoginActivity_;
import com.zczczy.leo.fuwuwangapp.activities.MemberInfoActivity_;
import com.zczczy.leo.fuwuwangapp.activities.MemberOrderActivity_;
import com.zczczy.leo.fuwuwangapp.activities.NewsActivity_;
import com.zczczy.leo.fuwuwangapp.activities.ReviewActivity_;
import com.zczczy.leo.fuwuwangapp.activities.SettingActivity_;
import com.zczczy.leo.fuwuwangapp.activities.VipActivity_;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.springframework.util.StringUtils;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {

    @ViewById
    ImageView img_vip_icon;

    @ViewById
    TextView txt_name, txt_address;

    @ViewById
    LinearLayout ll_color_control;

    @ViewById
    RelativeLayout rl_vip, rl_change_pass;

    @ViewById
    ImageView img_avatar;

    @StringRes
    String text_location, text_login, text_no_pay, text_paid, text_send, text_all_order;

    @AfterViews
    void afterView() {
//        setData();
    }

    @Click
    void txt_review() {
        if (checkUserIsLogin()) {
            ReviewActivity_.intent(this).start();
        } else {
            LoginActivity_.intent(this).startForResult(1000);
        }
    }

    @Click
    void txt_already_order() {
        if (checkUserIsLogin()) {
            MemberOrderActivity_.intent(this).orderState(Constants.SEND).title(text_send).start();
        } else {
            LoginActivity_.intent(this).startForResult(1000);
        }
    }

    @Click
    void txt_waiting_order() {
        if (checkUserIsLogin()) {
            MemberOrderActivity_.intent(this).orderState(Constants.DUEPAYMENT).title(text_no_pay).start();
        } else {
            LoginActivity_.intent(this).startForResult(1000);
        }
    }

    @Click
    void txt_paid_order() {
        if (checkUserIsLogin()) {
            MemberOrderActivity_.intent(this).orderState(Constants.PAID).title(text_paid).start();
        } else {
            LoginActivity_.intent(this).startForResult(1000);
        }
    }

    @Click
    void rl_whole() {
        if (checkUserIsLogin()) {
            MemberOrderActivity_.intent(this).orderState(Constants.ALL_ORDER).title(text_all_order).start();
        } else {
            LoginActivity_.intent(this).startForResult(1000);
        }
    }

    @Click
    void ll_login() {
        if (checkUserIsLogin()) {
            MemberInfoActivity_.intent(this).start();
        } else {
            LoginActivity_.intent(this).startForResult(1000);
        }
    }

    @OnActivityResult(value = 1000)
    void afterLogin(int resultCode) {
        if (resultCode == FragmentActivity.RESULT_OK) {
            setData();
        }
    }

    @Click
    void rl_change_pass() {
        ChangePasswordActivity_.intent(this).start();
    }

    @Click
    void card_logout() {
        if (checkUserIsLogin()) {
            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
            adb.setTitle("提示").setMessage("确定要注销吗？").setPositiveButton("注销", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String temp1 = pre.locationAddress().get();
                    String temp2 = pre.cityId().get();
                    pre.clear();
                    pre.locationAddress().put(temp1);
                    pre.cityId().put(temp2);
                    setData();
                }
            }).setNegativeButton("取消", null).setIcon(R.mipmap.logo).create().show();
        }
    }

    void setData() {
        if (checkUserIsLogin()) {
            txt_name.setText(pre.username().get());
            rl_change_pass.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(pre.avatar().get())) {
                Picasso.with(getActivity()).load(pre.avatar().get()).fit().placeholder(R.drawable
                        .default_header).error(R.drawable.default_header).into(img_avatar);
            }
            if (Constants.VIP.equals(pre.userType().get())) {
                rl_vip.setVisibility(View.VISIBLE);
                img_vip_icon.setVisibility(View.VISIBLE);
                txt_address.setText(String.format(text_location, pre.locationAddress().getOr("北京")));
            } else {
                rl_vip.setVisibility(View.GONE);
                img_vip_icon.setVisibility(View.INVISIBLE);
                txt_address.setText(String.format(text_location, pre.locationAddress().getOr("北京")));
            }
        } else {
            rl_change_pass.setVisibility(View.GONE);
            img_vip_icon.setVisibility(View.GONE);
            rl_vip.setVisibility(View.GONE);
            img_avatar.setImageResource(R.drawable.default_header);
            txt_name.setText(text_login);
            txt_address.setText(String.format(text_location, ""));
        }
    }

    @Click
    void rl_vip() {
        if (checkUserIsLogin()) {
            VipActivity_.intent(this).start();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setData();
        }
    }

    @Click
    void rl_news() {
        NewsActivity_.intent(this).start();
    }
}
