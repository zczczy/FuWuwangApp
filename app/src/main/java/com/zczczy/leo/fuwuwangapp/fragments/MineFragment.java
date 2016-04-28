package com.zczczy.leo.fuwuwangapp.fragments;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.activities.VipActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Leo on 2016/4/27.
 */
@EFragment(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {




    @Click
    void rl_vip() {
        VipActivity_.intent(this).start();
    }

}
