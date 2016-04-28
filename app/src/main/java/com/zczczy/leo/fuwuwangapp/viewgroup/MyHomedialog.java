package com.zczczy.leo.fuwuwangapp.viewgroup;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;

/**
 * Created by zczczy on 2015/9/8.
 * 首页通告
 */
public class MyHomedialog extends AlertDialog {


    private String appConfigTitle;
    private String appConfigContent;
    private String IsCloseBtn;


    TextView txt_title,config_content,close_btn;


    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            MyHomedialog.this.dismiss();
        }
    };

    public MyHomedialog(Context context, String appConfigTitle, String appConfigContent, String IsCloseBtn, View.OnClickListener newclickListener) {
        super(context);
        this.appConfigTitle = appConfigTitle;
        this.appConfigContent = appConfigContent;
        this.IsCloseBtn = IsCloseBtn;

        if(newclickListener!=null)
            this.clickListener = newclickListener;
    }

    public void close(){
        MyHomedialog.this.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_dialog_layout);
        //设置标题
        txt_title = (TextView)findViewById(R.id.txt_title);
        txt_title.setText(appConfigTitle);
        //设置内容
        config_content = (TextView)findViewById(R.id.config_content);
        config_content.setText(appConfigContent);
        //设置按钮
        if ("0".equals(IsCloseBtn)){
            LinearLayout confirm = (LinearLayout) findViewById(R.id.confirm);
            confirm.setVisibility(View.GONE);
        }
        else if("1".equals(IsCloseBtn))
        {
            LinearLayout confirm = (LinearLayout) findViewById(R.id.confirm);
            confirm.setVisibility(View.VISIBLE);
            confirm.setOnClickListener(clickListener);

        }

    }
}
