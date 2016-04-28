package com.zczczy.leo.fuwuwangapp.viewgroup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;

/**
 * Created by Leo on 2016/3/7.
 */
public class LuckDialog extends Dialog {

    public LuckDialog(Context context) {
        super(context);
    }

    public LuckDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LuckDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context context;
        private String title;
        private String message;
        private View contentView;
        private OnClickListener closeButtonClickListener, postiveButtonClickListener;
        private OnDismissListener onDismissListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setCloseButton(OnClickListener listener) {
            this.closeButtonClickListener = listener;
            return this;
        }

        public Builder setPostiveButtonClickListener(OnClickListener listener) {
            this.postiveButtonClickListener = listener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }



        public LuckDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LuckDialog dialog = new LuckDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.luck_dialog, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            ImageView img_luck_record = (ImageView) (layout).findViewById(R.id.img_luck_record);

            img_luck_record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (postiveButtonClickListener != null) {
                        postiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    }
                }
            });

            if (title != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(title);
            }

            ((ImageView) layout.findViewById(R.id.close))
                    .setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (closeButtonClickListener != null) {
                                closeButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE);
                            }
                        }
                    });

            dialog.setOnDismissListener(onDismissListener);

            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
//            dialog.setContentView(layout);

            return dialog;
        }

    }

}
