package com.zczczy.leo.fuwuwangapp.broadcast;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zczczy.leo.fuwuwangapp.tools.Constants;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.api.support.content.AbstractBroadcastReceiver;

/**
 * @author Created by LuLeo on 2016/6/20.
 *         you can contact me at :361769045@qq.com
 * @since 2016/6/20.
 */
@EReceiver
public class WXAppRegisterReceiver extends AbstractBroadcastReceiver {

    @ReceiverAction(actions = "com.tencent.mm.plugin.openapi.Intent.ACTION_REFRESH_WXAPP")
    void onRefreshWXApp(Context context) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        // 将该app注册到微信
        msgApi.registerApp(Constants.APP_ID);
    }

}
