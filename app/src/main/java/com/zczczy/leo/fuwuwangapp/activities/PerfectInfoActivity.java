package com.zczczy.leo.fuwuwangapp.activities;

import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.R;
import com.zczczy.leo.fuwuwangapp.model.Bank;
import com.zczczy.leo.fuwuwangapp.model.BaseModelJson;
import com.zczczy.leo.fuwuwangapp.model.CityModel;
import com.zczczy.leo.fuwuwangapp.model.OpenAccount;
import com.zczczy.leo.fuwuwangapp.model.ProvinceModel;
import com.zczczy.leo.fuwuwangapp.model.UserBaseInfo;
import com.zczczy.leo.fuwuwangapp.model.UserFinanceInfo;
import com.zczczy.leo.fuwuwangapp.prefs.MyPrefs_;
import com.zczczy.leo.fuwuwangapp.rest.MyDotNetRestClient;
import com.zczczy.leo.fuwuwangapp.rest.MyErrorHandler;
import com.zczczy.leo.fuwuwangapp.rest.MyRestClient;
import com.zczczy.leo.fuwuwangapp.tools.AndroidTool;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyAlertDialog;
import com.zczczy.leo.fuwuwangapp.viewgroup.MyEidtViewDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2016/4/28.
 */
@EActivity(R.layout.activity_perfect_info)
public class PerfectInfoActivity extends BaseActivity {

    @ViewById
    EditText edt_real_name, edt_mobile_phone, edt_post_code, edt_delivery, edt_name, edt_bank_card, edt_bank, edt_bank_area,
            edt_bank_city, edt_bank_region, edt_ejpass, edt_id_card, edt_id_card2, edit_mobile,edt_email;

    EditText edit_code;

    @ViewById
    RadioGroup rg_rg;

    String mobile;

    @ViewById
    Button btn_save;

    @StringRes
    String timer, txt_ejpass;

    boolean isCode = false;

    CountDownTimer countDownTimer;



    @ViewById
    RadioButton rb_user_info, rb_finance_info;

    @ViewById
    LinearLayout ll_user_info, ll_finance_info, llejpass, layout_bank_region, ll_id_card, ll_id_card2,ll_email;

    @StringRes
    String txt_perfect_info_title,send_message;

    MyAlertDialog dialog;

    //开户银行
    String[] BankItems = {"请选择"};
    List<Bank> listBanks;
    int banksize = 0;

    //省
    String[] provs = {"请选择"};
    List<ProvinceModel> listprov = new ArrayList<>();
    int provsize = 0;

    //市
    String[] citys = {"请选择"};
    List<CityModel> listcity = new ArrayList<>();
    int citysize = 0;

    //开户行(支行)
    String[] OpenAccounts = {"请选择"};
    List<OpenAccount> listOpenAccount = new ArrayList<>();
    int OpenAccountsize = 0;

    @Pref
    MyPrefs_ pre;

    EditText editText;

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    MyDotNetRestClient myDotNetRestClient;

    @RestService
    MyRestClient myRestClient;

    //验证码弹框
    AlertDialog.Builder adb;

    AlertDialog ad;
    @SystemService
    InputMethodManager inputMethodManager;

    @SystemService
    LayoutInflater layoutInflater;

    TextView text_send;

    @AfterInject
    void afterInject() {
        myRestClient.setRestErrorHandler(myErrorHandler);
        myDotNetRestClient.setRestErrorHandler(myErrorHandler);
    }

    @AfterViews
    void afterView() {
        AndroidTool.showLoadDialog(this);
        edt_bank_area.setText("请选择");
        edt_bank_city.setText("请选择");
        edt_bank_region.setText("请选择");
        GetSafeMessage();
        getBankItems();
        getBind();
    }

    @Background
    void getBankItems() {
        BaseModelJson<List<Bank>> bmj = myDotNetRestClient.GetBankItemsList();
        if (bmj != null) {
            if (bmj.Successful) {
                BankItems = new String[bmj.Data.size() + 1];
                BankItems[0] = "请选择";
                listBanks = bmj.Data;
                for (int i = 0; i < bmj.Data.size(); i++) {
                    BankItems[i + 1] = bmj.Data.get(i).getBankName();
                }
            }
        }

        BaseModelJson<List<ProvinceModel>> bmjp = myDotNetRestClient.GetProvinceList();
        if (bmj != null) {
            if (bmjp.Successful) {
                provs = new String[bmjp.Data.size() + 1];
                provs[0] = "请选择";
                listprov.clear();
                listprov.addAll(bmjp.Data);
                for (int i = 0; i < bmjp.Data.size(); i++) {
                    provs[i + 1] = bmjp.Data.get(i).getName();
                }
            }
        }

        getBindFinance();
    }

    @Click
    void edt_bank_area() {
        AndroidTool.showSinglenChoice(this, "开户省份", provs, provsize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which > 0) {
                    edt_bank_area.setText(listprov.get(which - 1).getName().toString());
                    provsize = which;
                    citysize = 0;
                    OpenAccountsize = 0;
                    AndroidTool.showLoadDialog(PerfectInfoActivity.this);
                    getcitybind(listprov.get(which - 1).getCode().toString());
                } else {
                    listcity.clear();
                    listOpenAccount.clear();
                    provsize = 0;
                    citysize = 0;
                    citys = new String[1];
                    citys[0] = "请选择";
                    OpenAccounts = new String[1];
                    OpenAccounts[0] = "请选择";
                    //citys =String[1];
                    OpenAccountsize = 0;
                    edt_bank_area.setText("请选择");
                }
                edt_bank_city.setText("请选择");
                edt_bank_region.setText("请选择");
                dialog.dismiss();
            }
        });
    }

    @Background
    void getcitybind(String code) {
        if (provsize == 0) {
            AndroidTool.dismissLoadDialog();
            return;
        }
        if (code == null || code == "" || code.equals("") || code.isEmpty()) {
            code = listprov.get(provsize - 1).getCode().toString();
        }
        BaseModelJson<List<CityModel>> bmjp = myDotNetRestClient.GetCityList(code);
        if (bmjp != null) {
            if (bmjp.Successful) {
                citys = new String[bmjp.Data.size() + 1];
                citys[0] = "请选择";
                listcity.clear();
                listcity.addAll(bmjp.Data);
                for (int i = 0; i < bmjp.Data.size(); i++) {
                    citys[i + 1] = bmjp.Data.get(i).getName();
                }
            }
        }
        AndroidTool.dismissLoadDialog();
    }

    @Click
    void edt_bank_city() {
        AndroidTool.showSinglenChoice(this, "开户城市", citys, citysize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which > 0) {
                    edt_bank_city.setText(listcity.get(which - 1).getName().toString());
                    citysize = which;
                    OpenAccountsize = 0;
                    AndroidTool.showLoadDialog(PerfectInfoActivity.this);
                    getOpenAccountbind("", listcity.get(which - 1).getCode().toString());
                } else {
                    OpenAccountsize = 0;
                    listOpenAccount.clear();
                    OpenAccounts = new String[1];
                    OpenAccounts[0] = "请选择";
                    // OpenAccounts = new String[1];
                    edt_bank_city.setText("请选择");
                }
                edt_bank_region.setText("请选择");
                dialog.dismiss();
            }
        });
    }

    @Background
    void getOpenAccountbind(String bankid, String citycode) {
        if (banksize == 0 || citysize == 0) {
            AndroidTool.dismissLoadDialog();
            return;
        }
        if (citycode == null || citycode == "" || citycode.equals("") || citycode.isEmpty()) {
            citycode = listcity.get(citysize - 1).getCode().toString();
        }
        if (bankid == null || bankid == "" || bankid.equals("") || bankid.isEmpty()) {
            bankid = listBanks.get(banksize - 1).getBankId().toString();
        }
        BaseModelJson<List<OpenAccount>> bmjp = myDotNetRestClient.GetBankNumBerList(citycode, bankid);
        if (bmjp.Successful) {
            OpenAccounts = new String[bmjp.Data.size() + 1];
            OpenAccounts[0] = "请选择";
            listOpenAccount.clear();
            listOpenAccount.addAll(bmjp.Data);
            for (int i = 0; i < bmjp.Data.size(); i++) {
                OpenAccounts[i + 1] = bmjp.Data.get(i).getName();
            }
        }
        AndroidTool.dismissLoadDialog();
    }

    @Click
    void edt_bank_region() {
        AndroidTool.showSinglenChoice(this, "开户行名称", OpenAccounts, OpenAccountsize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which > 0) {
                    edt_bank_region.setText(listOpenAccount.get(which - 1).getName().toString());
                } else {
                    edt_bank_region.setText("请选择");
                }
                OpenAccountsize = which;
                dialog.dismiss();
            }
        });
    }

    @Click
    void edt_bank() {
        AndroidTool.showSinglenChoice(this, "开户银行", BankItems, banksize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                banksize = which;
                if (which == 1) {
                    layout_bank_region.setVisibility(View.GONE);
                    edt_bank.setText(listBanks.get(which - 1).getBankName());
                    OpenAccountsize = 0;
                } else if (which > 0) {
                    layout_bank_region.setVisibility(View.VISIBLE);
                    edt_bank.setText(listBanks.get(which - 1).getBankName());
                    //banksize = which;
                    AndroidTool.showLoadDialog(PerfectInfoActivity.this);
                    OpenAccountsize = 0;
                    getOpenAccountbind(listBanks.get(which - 1).getBankId().toString(), "");
                } else {
                    layout_bank_region.setVisibility(View.VISIBLE);
                    listOpenAccount.clear();
                    OpenAccountsize = 0;
                    OpenAccounts = new String[1];
                    OpenAccounts[0] = "请选择";
                    edt_bank.setText("请选择");
                }
                edt_bank_region.setText("请选择");
                dialog.dismiss();
            }
        });
    }

    @Background
    void getBindFinance() {
        String token = pre.token().get();
        myDotNetRestClient.setHeader("Token", token);
        BaseModelJson<UserFinanceInfo> bmj = myDotNetRestClient.GetFinancialById();

        if (bmj != null) {
            BaseModelJson<List<CityModel>> bmjp = myDotNetRestClient.GetCityList(bmj.Data.getProviceCode());

            if (bmjp != null) {
                if (bmjp.Successful) {
                    citys = new String[bmjp.Data.size() + 1];
                    citys[0] = "请选择";
                    listcity.clear();
                    listcity.addAll(bmjp.Data);
                    for (int i = 0; i < bmjp.Data.size(); i++) {
                        citys[i + 1] = bmjp.Data.get(i).getName();
                    }
                }
            }

            BaseModelJson<List<OpenAccount>> bmjpo = myDotNetRestClient.GetBankNumBerList(bmj.Data.getCityCode(), bmj.Data.getBankName());
            if (bmjpo != null) {
                if (bmjpo.Successful) {
                    OpenAccounts = new String[bmjpo.Data.size() + 1];
                    OpenAccounts[0] = "请选择";
                    listOpenAccount.clear();
                    listOpenAccount.addAll(bmjpo.Data);
                    for (int i = 0; i < bmjpo.Data.size(); i++) {
                        OpenAccounts[i + 1] = bmjpo.Data.get(i).getName();
                    }
                }
            }

            setBindFinance(bmj);
        }
    }

    @UiThread
    void setBindFinance(BaseModelJson<UserFinanceInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                //收款人
                edt_name.setText(bmj.Data.getRealName());
                edt_bank_card.setText(bmj.Data.getBankCode());
                edt_bank.setText(getBankNameStr(bmj.Data.getBankName()));
                edt_bank_area.setText(getProviceStr(bmj.Data.getProviceCode()));
                edt_bank_city.setText(getCityStr(bmj.Data.getCityCode()));
                if (getBankNameStr(bmj.Data.getBankName()).equals("中国工商银行")) {
                    layout_bank_region.setVisibility(View.GONE);
                } else {
                    layout_bank_region.setVisibility(View.VISIBLE);
                    edt_bank_region.setText(getRegionStr(bmj.Data.getBankNumber()));
                }
            }
        }
    }

    /*
    * 获取点击下拉框的值
    * */
    public String getBankNameStr(String id) {
        for (int i = 0; i < listBanks.size(); i++) {
            if (listBanks.get(i).getBankId().toString().equals(id)) {
                banksize = i + 1;
                return listBanks.get(i).getBankName().toString();
            }
        }
        return "";
    }

    public String getProviceStr(String id) {
        for (int i = 0; i < listprov.size(); i++) {
            if (listprov.get(i).getCode().toString().equals(id)) {
                provsize = i + 1;
                return listprov.get(i).getName().toString();
            }
        }
        return "";
    }

    public String getCityStr(String id) {
        for (int i = 0; i < listcity.size(); i++) {
            if (listcity.get(i).getCode().toString().equals(id)) {
                citysize = i + 1;
                return listcity.get(i).getName().toString();
            }
        }
        return "";
    }

    public String getRegionStr(String id) {

        for (int i = 0; i < listOpenAccount.size(); i++) {
            if (listOpenAccount.get(i).getNums().toString().equals(id)) {
                OpenAccountsize = i + 1;
                return listOpenAccount.get(i).getName().toString();
            }
        }
        return "";
    }


    //验证订阅
    @Background
    void GetSafeMessage() {
        BaseModelJson<String> bmj = myDotNetRestClient.SubscriptionExist(pre.username().get());
        AfterGetSafe(bmj);

    }

    @UiThread
    void AfterGetSafe(BaseModelJson<String> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            getMobile();
            isCode = true;
        } else {
            isCode = false;
        }
    }

    //获取手机号码
    @Background
    void getMobile() {

        BaseModelJson<String> bmj = myDotNetRestClient.GetMobile(pre.username().get());
        setMobile(bmj);
    }

    @UiThread
    void setMobile(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {

            }
        }
    }


    //变量
    String code;

    //验证验证码
    @Background
    void GetResult() {
        code = edit_code.getText().toString();
        BaseModelJson<String> bmj = myDotNetRestClient.VerifyExite(pre.username().get(), code, "1");
        AfterGetResult(bmj);

    }

    @UiThread
    void AfterGetResult(BaseModelJson<String> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            //验证码成功后
            saveBaseInfo();

        } else {
            AndroidTool.showToast(this, bmj.Error);
        }

    }


    //获取验证码
    @Background
    void sendCode() {
        Map<String, String> map = new HashMap<>();
        map.put("UserName", pre.username().get());
        map.put("SendType", "1");
        map.put("mobile", mobile);
        afterSendCode(myDotNetRestClient.SendVerificationCode(map));
    }

    @UiThread
    void afterSendCode(BaseModelJson<String> bmj) {
        if (bmj == null) {
            AndroidTool.showToast(this, no_net);
        } else if (bmj.Successful) {
            // AndroidTool.showToast(this, bmj.Data);
        } else {
            AndroidTool.showToast(this, bmj.Error);
        }
    }


    @Background
    void getBind() {
        String token = pre.token().get();
        myDotNetRestClient.setHeader("Token", token);
        BaseModelJson<UserBaseInfo> bmj = myDotNetRestClient.GetZcUserById();
        setBind(bmj);
    }

    @UiThread
    void setBind(BaseModelJson<UserBaseInfo> bmj) {
        AndroidTool.dismissLoadDialog();
        if (bmj != null) {
            if (bmj.Successful) {
                //真实姓名
                edt_real_name.setText(bmj.Data.getM_realrName());
                edt_mobile_phone.setText(bmj.Data.getM_mobile());
                mobile = bmj.Data.getM_mobile();
                edt_post_code.setText(bmj.Data.getM_ZipCode());
                edt_delivery.setText(bmj.Data.getM_Address());
                if ("0".equals(bmj.Data.getM_idCard()) || "".equals(bmj.Data.getM_idCard()) || bmj.Data.getM_idCard() == null) {
                    ll_id_card.setVisibility(View.VISIBLE);
                    ll_id_card2.setVisibility(View.VISIBLE);
                }
                //如果支付密码为空，则显示出来
                if (bmj.Data.getEjpass() == null || bmj.Data.getEjpass() == "" || bmj.Data.getEjpass().isEmpty()) {
                    llejpass.setVisibility(View.VISIBLE);
                }
                if(StringUtils.isEmpty(bmj.Data.getM_Email())){
                    ll_email.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    //保存按钮点击事件
    @Click
    void btn_save() {
        if (isNetworkAvailable(this)) {
            if (rb_user_info.isChecked()) {
                String realname = edt_real_name.getText().toString();
                if (realname == null || realname == "" || realname.isEmpty()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "真实姓名不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                String phone = edt_mobile_phone.getText().toString();
                if (phone == null || phone == "" || phone.isEmpty()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "手机号码不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                String id_card = edt_id_card.getText().toString();
                if ((id_card == null || id_card == "" || id_card.isEmpty()) && ll_id_card.isShown()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "身份证号不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                String email= edt_email.getText().toString();
                if(StringUtils.isEmpty(email) && ll_email.isShown()){
                    MyAlertDialog dialog = new MyAlertDialog(this, "邮箱不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                String yzbm = edt_post_code.getText().toString();
                if (yzbm == null || yzbm == "" || yzbm.isEmpty()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "邮政编码不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                String ejpass = edt_ejpass.getText().toString();
                if ((ejpass == null || ejpass == "" || ejpass.isEmpty()) && llejpass.isShown()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "支付密码不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                String delivery = edt_delivery.getText().toString();
                if (delivery == null || delivery == "" || delivery.isEmpty()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "邮寄地址不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                //验证订阅和修改手机号
                if (isCode && !mobile.equals(edt_mobile_phone.getText().toString())) {
                    final View view = layoutInflater.inflate(R.layout.per_code, null);
                    adb = new AlertDialog.Builder(this);
                    ad = adb.setView(view).create();
                    ad.show();
                    edit_code = (EditText) view.findViewById(R.id.edit_code);
                    Button cancel = (Button) view.findViewById(R.id.cancel);
                    Button confirm = (Button) view.findViewById(R.id.btn_confirm);
                    text_send = (TextView) view.findViewById(R.id.text_send);
                    countDownTimer = new CountDownTimer(AndroidTool.getCodeTime(pre.timerPerfectInfo().get()), 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            pre.timerPerfectInfo().put(System.currentTimeMillis() + millisUntilFinished);
                            text_send.setPressed(true);
                            text_send.setText(String.format(timer, millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            pre.timerPerfectInfo().put(0L);
                            text_send.setPressed(false);
                            text_send.setText(send_message);
                        }
                    };
                    if (AndroidTool.getCodeTime(pre.timerPerfectInfo().get()) < 120000L) {
                        countDownTimer.start();
                        edit_code.setEnabled(true);
                    } else {
                        edit_code.setEnabled(false);
                    }
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            ad.dismiss();
                        }
                    });
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (AndroidTool.checkIsNull(edit_code)) {
                                AndroidTool.showToast(PerfectInfoActivity.this, "验证码不能为空");
                            } else {
                                code = edit_code.getText().toString();
                                ad.dismiss();
                                AndroidTool.showLoadDialog(PerfectInfoActivity.this);
                                //验证验证码
                                GetResult();
                            }
                        }
                    });

                    //获取验证码点击按钮
                    text_send.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            edit_code.setEnabled(true);
                            if (pre.timerPerfectInfo().get() == 0L || AndroidTool.getCodeTime(pre.timerPerfectInfo().get()) >= 120000L) {
                                countDownTimer.start();
                                edit_code.setEnabled(true);
                                sendCode();
                            }
                        }
                    });
                } else {
                    saveBaseInfo();
                }
            } else {
                String realname = edt_name.getText().toString();
                if (realname == null || realname == "" || realname.isEmpty()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "收款人不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                String id_card = edt_id_card2.getText().toString();
                if ((id_card == null || id_card == "" || id_card.isEmpty()) && ll_id_card2.isShown()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "身份证号不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                String bank_card = edt_bank_card.getText().toString();
                if (bank_card == null || bank_card == "" || bank_card.isEmpty()) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "银行卡号不能为空！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                if (banksize == 0) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "请选择开户银行！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                if (provsize == 0) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "请选择开户地域！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                if (citysize == 0) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "请选择开户城市！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }

                if (layout_bank_region.getVisibility() == View.VISIBLE && OpenAccountsize == 0) {
                    MyAlertDialog dialog = new MyAlertDialog(this, "请选择开户行名称！", null);
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    return;
                }
                dialogxia = new MyEidtViewDialog(PerfectInfoActivity.this, txt_ejpass, listener);
                dialogxia.show();
                dialogxia.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogxia.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        } else {
            Toast.makeText(this, no_net, Toast.LENGTH_SHORT).show();
        }
    }

    MyEidtViewDialog dialogxia;

    View.OnClickListener listener = new View.OnClickListener() {
        //DialogInterface dif,int i
        @Override
        public void onClick(View view) {
            String str = dialogxia.getEditTextValue();//editText.getText().toString();
            if (str != null && str != "" && !str.isEmpty()) {
                AndroidTool.showLoadDialog(PerfectInfoActivity.this);
                saveFinanceInfo();
            } else {
                MyAlertDialog dialog = new MyAlertDialog(PerfectInfoActivity.this, "请输入支付密码！", null);
                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
                return;
            }
        }
    };


    @Background
    void saveBaseInfo() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        BaseModelJson<String> bm = myRestClient.SaveBaseInfo(edt_real_name.getText().toString(),
                edt_mobile_phone.getText().toString(), edt_post_code.getText().toString(),
                edt_delivery.getText().toString(), edt_ejpass.getText().toString(),
                edt_id_card.getText().toString(),edt_email.getText().toString());
        showsuccess(bm);
    }

    @Background
    void saveFinanceInfo() {
        String token = pre.token().get();
        myRestClient.setHeader("Token", token);
        String BankNumber = "";
        if (OpenAccountsize != 0) {
            BankNumber = listOpenAccount.get(OpenAccountsize - 1).getNums().toString();
        }
        BaseModelJson<String> bm = myRestClient.SaveFinancial(edt_name.getText().toString(), edt_bank_card.getText().toString(), listBanks.get(banksize - 1).getBankId().toString(), listprov.get(provsize - 1).getCode().toString(), listcity.get(citysize - 1).getCode().toString(), BankNumber, dialogxia.getEditTextValue(), edt_id_card2.getText().toString());
        showsuccess(bm);
    }

    @UiThread
    void showsuccess(BaseModelJson<String> bm) {
        AndroidTool.dismissLoadDialog();
        if (bm != null) {
            if (bm.Successful) {
                dialog = new MyAlertDialog(this, "保存成功！", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.close();
                        finish();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                if (ll_user_info.isShown()) {
                    pre.realname().put(edt_real_name.getText().toString());
                } else {
                    pre.realname().put(edt_name.getText().toString());
                }
            } else {
                MyAlertDialog dialog = new MyAlertDialog(this, bm.Error, null);
                dialog.show();
            }
        }
    }

    @CheckedChange
    void rb_user_info(boolean checked) {

        if (checked) {
            ll_user_info.setVisibility(View.VISIBLE);

        } else {
            ll_user_info.setVisibility(View.GONE);
        }

    }

    @CheckedChange
    void rb_finance_info(boolean checked) {

        if (checked) {
            ll_finance_info.setVisibility(View.VISIBLE);

        } else {
            ll_finance_info.setVisibility(View.GONE);
        }

    }

    public void setCode(String code){
        if(edit_code!=null){
            edit_code.setText(code);
        }
    }

}
