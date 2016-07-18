package com.zczczy.leo.fuwuwangapp.activities;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * @author Created by LuLeo on 2016/6/12.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/12.
 */
@EActivity(R.layout.activity_news)
public class NewsActivity extends BaseActivity {


    //公告
    @Click
    void rl_notice() {
        if (isNetworkAvailable(this)) {
            NoticeActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //资讯
    @Click
    void rl_topic() {
        if (isNetworkAvailable(this)) {
            InformationActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //麻团基金
    @Click
    void rl_fund() {
        if (isNetworkAvailable(this)) {
            FundActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //联盟商家
    @Click
    void rl_cooperation_merchant() {
        if (isNetworkAvailable(this)) {
            CooperationMerchantActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //体验中心
    @Click
    void rl_experience_center() {
        if (isNetworkAvailable(this)) {
            ExperienceActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //加盟代理
    @Click
    void rl_agent() {
        if (isNetworkAvailable(this)) {
            AgentActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //活动
    @Click
    void rl_activity() {
        if (isNetworkAvailable(this)) {
            ActivityActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //商学院
    @Click
    void rl_business_institute() {
        if (isNetworkAvailable(this)) {
            BusinessInstituteActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }

    //增值服务
    @Click
    void rl_service() {
        if (isNetworkAvailable(this)) {
            ServiceActivity_.intent(this).start();
        } else {
            AndroidTool.showToast(this, no_net);
        }
    }


}
