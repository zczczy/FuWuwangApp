package com.zczczy.leo.fuwuwangapp.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zczczy.leo.fuwuwangapp.viewgroup.CustomProgressDialog;
import com.zczczy.leo.fuwuwangapp.viewgroup.DateTimeDialog;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidTool {
    private static ProgressDialog infoDialog;
    private static CustomProgressDialog cpdialog;
    private static String MPHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    private static String TPHONE = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
    public static DecimalFormat df = new DecimalFormat("######0.00");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//    /**
//     * 显示等待对话框
//     *
//     * @param context
//     */
//    public static void showLoadDialog(Context context) {
//        if (infoDialog == null) {
//            infoDialog = new LoadingDialog(context, "正在加载..", R.anim.loading);
//            infoDialog.setCancelable(false);
//            infoDialog.show();
//        } else if (!infoDialog.isShowing()&& infoDialog.getContext() == context) {
//            infoDialog.setCancelable(false);
//            infoDialog.show();
//        } else if (!infoDialog.isShowing()&&infoDialog.getContext() != context) {
//            infoDialog = new LoadingDialog(context, "正在加载..",R.anim.loading);
//            infoDialog.setCancelable(false);
//            infoDialog.show();
//        }
//    }

    public static long getCodeTime(long timer) {
        return Math.abs(System.currentTimeMillis() - timer) >= 120000L ? 120000L : Math.abs(System.currentTimeMillis() - timer);
    }


    public static String getCurrentFirstDay(int month) {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, 1 - month);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return format.format(cal_1.getTime());
    }

    public static String getCurrentDay() {
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        return format.format(cal_1.getTime());
    }


    public static String getCurrentLastDay() {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        return format.format(cale.getTime());
    }

    public static String getYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static String changeLongToDate(long l) {

        return sdf.format(new Date(l));
    }


    /**
     * 显示等待对话框
     *
     * @param context
     */
    public static void showLoadDialog(final Context context) {
        if (cpdialog == null) {
            cpdialog = CustomProgressDialog.createDialog(context);
//            cpdialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
//                    Activity activity = (Activity) context;
//                    activity.finish();
//                }
//            });
//            cpdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialogInterface) {
//                    Activity activity = (Activity) context;
//                    activity.finish();
//                }
//            });
//            cpdialog.setCanceledOnTouchOutside(false);
            cpdialog.setCancelable(false);
            cpdialog.show();
        } else if (!cpdialog.isShowing() && cpdialog.getContext() == context) {
            cpdialog.setCanceledOnTouchOutside(false);
            cpdialog.show();
        } else if (!cpdialog.isShowing() && cpdialog.getContext() != context) {
            cpdialog = CustomProgressDialog.createDialog(context);
//            cpdialog.setCanceledOnTouchOutside(false);
            cpdialog.setCancelable(false);
            cpdialog.show();
        }
    }

    public static void showCancelabledialog(Context context) {
        if (cpdialog == null) {
            cpdialog = CustomProgressDialog.createDialog(context);
            cpdialog.setCancelable(false);
            cpdialog.show();
        } else if (!cpdialog.isShowing() && cpdialog.getContext() == context) {
            cpdialog.setCancelable(false);
            cpdialog.show();
        } else if (!cpdialog.isShowing() && cpdialog.getContext() != context) {
            cpdialog = CustomProgressDialog.createDialog(context);
            cpdialog.setCancelable(false);
            cpdialog.show();
        }
    }

    public static void showdialog(Context context) {
        if (cpdialog == null) {
            cpdialog = CustomProgressDialog.createDialog(context);
            cpdialog.setCanceledOnTouchOutside(false);
            cpdialog.show();
        } else if (!cpdialog.isShowing() && cpdialog.getContext() == context) {
            cpdialog.setCanceledOnTouchOutside(false);
            cpdialog.show();
        } else if (!cpdialog.isShowing() && cpdialog.getContext() != context) {
            cpdialog = CustomProgressDialog.createDialog(context);
            cpdialog.setCanceledOnTouchOutside(false);
            cpdialog.show();
        }
    }

    /**
     * 隐藏等待对话框
     */
    public static void dismissdialog(final Context context) {
        if (cpdialog != null && cpdialog.isShowing()) {
            cpdialog.dismiss();
        }
    }


    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 隐藏等待对话框
     */
    public static void dismissLoadDialog() {
        if (cpdialog != null && cpdialog.isShowing()) {
            cpdialog.dismiss();
        }
    }

//    /**
//     * 隐藏等待对话框
//     */
//    public static void dismissLoadDialog() {
//        if (infoDialog != null && infoDialog.isShowing()) {
//            infoDialog.dismiss();
//        }
//    }

    /**
     * 显示 Toast
     *
     * @param context
     * @param msg     消息
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示 Toast
     *
     * @param context
     * @param msg     消息
     */
    public static void showToast(Fragment context, String msg) {
        Toast.makeText(context.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param context
     * @param title    标题
     * @param items    元素
     * @param checkId  当前元素的id
     * @param listener 监听器
     */
    public static void showSinglenChoice(Context context, String title,
                                         String[] items, int checkId,
                                         DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, checkId > 0 ? checkId : 0,
                        listener).create().show();
    }

    public static Dialog showCustomDialogNoTitle(Context context, View view) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        return dialog;
    }


    public static DateTimeDialog dateTimeDialog(Context context) {
        DateTimeDialog dialog = new DateTimeDialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    /**
     * @param title    标题
     * @param view     编辑内容
     * @param listener 监听器
     * @return
     */
    public static Dialog showEditDialog(String title, EditText view,
                                        DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("确定", listener);
        return builder.create();
    }


    public static Dialog showViewDialog(String title, View view,
                                        DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("确定", listener);
        return builder.create();
    }

    /**
     * 判断是否为空
     *
     * @param context
     * @param txts
     * @return
     */
    public static boolean checkNotNull(Context context, String... txts) {
        for (String t : txts) {
            if (t == null || t.length() == 0) {
                if (context != null)
                    showToast(context, "请正确填写！！！");
                return false;
            }
        }
        return true;
    }

    /***
     * 去掉数组中重复的 元素
     *
     * @param resource
     * @return String[]
     */
    public static String[] Array_unique(String[] resource) {
        // array_unique
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < resource.length; i++) {
            if (!list.contains(resource[i])) {
                list.add(resource[i]);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 判断EditText是否为空！ 如果为空 返回true 否者返回false
     *
     * @param e
     * @return
     */
    public static boolean checkIsNull(EditText e) {
        if (e == null) {
            return true;
        }
        return "".equals(e.getText().toString().trim());
    }

    /**
     * 判断TextView是否为空！ 如果为空 返回true 否者返回false
     *
     * @param e
     * @return
     */
    public static boolean checkTextViewIsNull(TextView e) {
        if (e == null) {
            return true;
        }
        return "".equals(e.getText().toString().trim());
    }

    /**
     * 判断TextView是否为空！ 如果为空 返回true 否者返回false
     *
     * @param e
     * @return
     */
    public static boolean checkTextViewIsNull(TextView... e) {
        if (e == null) {
            return true;
        }
        for (TextView t : e) {
            if ("".equals(t.getText().toString().trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断手机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param mPhone
     * @return
     */
    public static boolean checkMPhone(EditText mPhone) {
        Pattern p = Pattern.compile(MPHONE);
        Matcher m = p.matcher(mPhone.getText().toString().trim());

        return !m.matches();
    }


    /**
     * 判断手机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param mPhone
     * @return
     */
    public static boolean checkMPhone(String mPhone) {
        Pattern p = Pattern.compile(MPHONE);
        Matcher m = p.matcher(mPhone.trim());
        return !m.matches();
    }

    /**
     * 判断座机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param tPhone
     * @return
     */
    public static boolean checkTPhone(String tPhone) {
        Pattern p = Pattern.compile(TPHONE);
        Matcher m = p.matcher(tPhone.trim());
        return !m.matches();
    }


    /**
     * 判断手机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param mPhone
     * @return
     */
    public static boolean checkMPhone(TextView mPhone) {
        Pattern p = Pattern.compile(MPHONE);
        Matcher m = p.matcher(mPhone.getText().toString().trim());

        return !m.matches();
    }


    /**
     * 判断邮箱 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(TextView email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(email.getText().toString().trim());
        return !m.matches();
    }

    /**
     * 判断邮箱 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(EditText email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(email.getText().toString().trim());
        return !m.matches();
    }

    /**
     * 判断邮箱 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(email.trim());
        return !m.matches();
    }


    /**
     * 判断座机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param tPhone
     * @return
     */
    public static boolean checkTPhone(EditText tPhone) {
        Pattern p = Pattern.compile(TPHONE);
        Matcher m = p.matcher(tPhone.getText().toString().trim());
        return !m.matches();
    }

    public static boolean isNetConnected(Context context) {
        boolean isNetConnected;
        // 获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            // String name = info.getTypeName();
            // L.i("当前网络名称：" + name);
            isNetConnected = true;
        } else {
            isNetConnected = false;
        }
        return isNetConnected;
    }

    /**
     * 将文件转换为Uri
     *
     * @param fileName
     * @return
     */
    public static Uri getUri(String fileName) {
        return Uri.fromFile(GetFile(fileName));
    }

    /**
     * 创建文件对象
     *
     * @param fileName
     * @return
     */
    public static File GetFile(String fileName) {
        File file = new File(fileName);
        return file;
    }

    /**
     * 获取图片跟路径地址
     *
     * @return
     */
    public static String BaseFilePath() {
        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/download_cache/");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return sb.toString();
    }

    public static String test(String str) {


        return "";
    }

}
