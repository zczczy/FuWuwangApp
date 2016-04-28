package com.zczczy.leo.fuwuwangapp.viewgroup;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;

/**
 * 设置确认对话框的提示信息
 * wh
 * 2015-7-6
 */
public class MyAlertDialog extends AlertDialog{
    private String message;


    TextView confirm_message;

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            MyAlertDialog.this.dismiss();
        }
    };

    public MyAlertDialog(Context context, String message, View.OnClickListener newclickListener) {
        super(context);
        this.message = message;
        if(newclickListener!=null)
        this.clickListener = newclickListener;
    }

    public void close(){
        MyAlertDialog.this.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_dialog_layout);
        //设置标题
        confirm_message = (TextView)findViewById(R.id.confirm_message);
        confirm_message.setText(message);
        LinearLayout confirm = (LinearLayout) findViewById(R.id.confirm);
        confirm.setOnClickListener(clickListener);
    }

}
