package com.zczczy.leo.fuwuwangapp.viewgroup;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;

/**
 * 设置确认对话框的提示信息
 * wh
 * 2015-7-6
 */
public class MyEidtViewDialog extends AlertDialog {
    private String title;

    public MyEidtViewDialog(Context context, String title, View.OnClickListener newclickListener) {
        super(context);
        this.title=title;
        if(newclickListener!=null)
        this.clickListener = newclickListener;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            MyEidtViewDialog.this.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editview_dialog_layout);

        LinearLayout confirm = (LinearLayout) findViewById(R.id.confirm);
        TextView txttitle = (TextView) findViewById(R.id.txttitle);
        txttitle.setText(title);
        confirm.setOnClickListener(clickListener);
    }

    public String getEditTextValue()
    {
        EditText message = (EditText) findViewById(R.id.confirm_message);
        return message.getText().toString();
    }

}
