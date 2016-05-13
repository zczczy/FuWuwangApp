package com.zczczy.leo.fuwuwangapp.viewgroup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.TintTypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zczczy.leo.fuwuwangapp.R;


/**
 * Created by leo on 2015/10/23.
 */
public class MyTitleBar extends RelativeLayout {

    private static final String TAG = "MyTitleBar";

    private TextView mTitleTextView;

    private CharSequence mTitleText, mLeftText, mRightText, mSearchHintText;

    private TextView mLeftTextView, mRightTextView;

    private ImageButton mNavButtonView;

    private ImageButton mRightButtonView;

    private ImageView logoView;

    private View mCustomView;

    private int mCustomViewId;

    private EditText mSearchView;

    private int mTitleTextColor, mLeftTextColor, mRightTextColor, mSearchHintTextColor;

    private float mTitleSize, mLeftTextSize, mRightTextSize;

    private int textSize = 15;

    private final AppCompatDrawableManager mDrawableManager;

    private Drawable searchDrawable;

    private int mRightTextMarginRight, mleftTextMarginLeft;

    private int mLeftTextDrawablePadding, mRightTextDrawablePadding;

    private boolean mLogoShape;

    public MyTitleBar(Context context) {
        this(context, null);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.MyTitleBar, defStyleAttr, 0);

        mRightTextMarginRight = a.getDimensionPixelSize(R.styleable.MyTitleBar_mRightTextMarginRight, pxFromDp(0));
        mleftTextMarginLeft = a.getDimensionPixelSize(R.styleable.MyTitleBar_mLeftTextMarginLeft, pxFromDp(0));


        final Drawable rightButtonView = a.getDrawable(R.styleable.MyTitleBar_mRightButtonIcon);
        if (rightButtonView != null) {
            setRightButtonIcon(rightButtonView);
        }

        final CharSequence mRightText = a.getText(R.styleable.MyTitleBar_mRightText);
        if (!TextUtils.isEmpty(mRightText)) {
            setRightText(mRightText);
        }

        mRightTextDrawablePadding = a.getDimensionPixelSize(R.styleable.MyTitleBar_mRightTextDrawablePadding, pxFromDp(5));
        final Drawable mRightTextRightDrawable = a.getDrawable(R.styleable.MyTitleBar_mRightTextRightDrawable);
        if (mRightTextRightDrawable != null) {
            mRightTextRightDrawable.setBounds(0, 0, mRightTextRightDrawable.getMinimumWidth(), mRightTextRightDrawable.getMinimumHeight());
            mRightTextView.setCompoundDrawablePadding(mRightTextDrawablePadding);
            mRightTextView.setCompoundDrawables(null, null, mRightTextRightDrawable, null);
        }
        final Drawable mRightTextLeftDrawable = a.getDrawable(R.styleable.MyTitleBar_mRightTextLeftDrawable);
        if (mRightTextLeftDrawable != null) {
            mRightTextLeftDrawable.setBounds(0, 0, mRightTextLeftDrawable.getMinimumWidth(), mRightTextLeftDrawable.getMinimumHeight());
            mRightTextView.setCompoundDrawablePadding(mRightTextDrawablePadding);
            mRightTextView.setCompoundDrawables(mRightTextLeftDrawable, null, null, null);
        }


        if (a.hasValue(R.styleable.MyTitleBar_mRightTextColor)) {
            setRightTextColor(a.getColor(R.styleable.MyTitleBar_mRightTextColor, 0xffffffff));
        }

        if (a.hasValue(R.styleable.MyTitleBar_mRightTextSize)) {
            setRightTextSize(a.getDimensionPixelSize(R.styleable.MyTitleBar_mRightTextSize, textSize));
        }

        final CharSequence title = a.getText(R.styleable.MyTitleBar_mTitle);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (a.hasValue(R.styleable.MyTitleBar_mTitleTextColor)) {
            setTitleTextColor(a.getColor(R.styleable.MyTitleBar_mTitleTextColor, 0xffffffff));
        }
        if (a.hasValue(R.styleable.MyTitleBar_mTitleSize)) {
            setTitleSize(a.getDimensionPixelSize(R.styleable.MyTitleBar_mTitleSize, 20));
        }

        final CharSequence searchHintText = a.getText(R.styleable.MyTitleBar_mSearchHintText);
        if (!TextUtils.isEmpty(searchHintText)) {
            setSearchText(searchHintText);
        }

        final Drawable searchDrawableLeft = a.getDrawable(R.styleable.MyTitleBar_mSearchViewDrawableLeft);
        if (searchDrawableLeft != null) {
            setSearchDrawableLeft(searchDrawableLeft);
        }

        final Drawable searchDrawableRight = a.getDrawable(R.styleable.MyTitleBar_mSearchViewDrawableRight);
        if (searchDrawableRight != null) {
            setSearchDrawableRight(searchDrawableRight);
        }

        if (a.hasValue(R.styleable.MyTitleBar_mSearchHintTextColor)) {
            setSearchHintTextColor(a.getColor(R.styleable.MyTitleBar_mSearchHintTextColor, 0xffffffff));
        }

        final Drawable navIcon = a.getDrawable(R.styleable.MyTitleBar_mNavButtonIcon);
        if (navIcon != null) {
            setNavButtonIcon(navIcon);
        }

        final CharSequence mLeftText = a.getText(R.styleable.MyTitleBar_mLeftText);
        if (!TextUtils.isEmpty(mLeftText)) {
            setLeftText(mLeftText);
        }
        if (a.hasValue(R.styleable.MyTitleBar_mLeftTextColor)) {
            setLeftTextColor(a.getColor(R.styleable.MyTitleBar_mLeftTextColor, 0xffffffff));
        }

        if (a.hasValue(R.styleable.MyTitleBar_mLeftTextSize)) {
            setLeftTextSize(a.getDimensionPixelSize(R.styleable.MyTitleBar_mLeftTextSize, textSize));
        }

        mLeftTextDrawablePadding = a.getDimensionPixelSize(R.styleable.MyTitleBar_mLeftTextDrawablePadding, pxFromDp(5));

        final Drawable leftTextRightDrawable = a.getDrawable(R.styleable.MyTitleBar_mLeftTextRightDrawable);
        if (leftTextRightDrawable != null) {
            leftTextRightDrawable.setBounds(0, 0, leftTextRightDrawable.getMinimumWidth(), leftTextRightDrawable.getMinimumHeight());
            mLeftTextView.setCompoundDrawablePadding(mLeftTextDrawablePadding);
            mLeftTextView.setCompoundDrawables(null, null, leftTextRightDrawable, null);
        }

        final Drawable leftTextLeftDrawable = a.getDrawable(R.styleable.MyTitleBar_mLeftTextLeftDrawable);
        if (leftTextLeftDrawable != null) {
            leftTextLeftDrawable.setBounds(0, 0, leftTextLeftDrawable.getMinimumWidth(), leftTextLeftDrawable.getMinimumHeight());
            mLeftTextView.setCompoundDrawablePadding(mLeftTextDrawablePadding);
            mLeftTextView.setCompoundDrawables(leftTextLeftDrawable, null, null, null);
        }

        final Drawable logoDrawable = a.getDrawable(R.styleable.MyTitleBar_mLogoIcon);
        if (logoDrawable != null) {
            setLogoIcon(logoDrawable);
        }

        if (a.hasValue(R.styleable.MyTitleBar_mCustomView)) {
            mCustomViewId = a.getResourceId(R.styleable.MyTitleBar_mCustomView, 0);
        }

        if (mCustomViewId != 0) {
            setCustomView(mCustomViewId);
        }

        if (mNavButtonView != null) {
            mNavButtonView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }

        a.recycle();

        mDrawableManager = AppCompatDrawableManager.get();
    }

    /**
     * @param resId
     */
    public void setCustomView(@LayoutRes int resId) {
        mCustomView = inflate(getContext(), resId, null);
        setCustomView(mCustomView);
    }

    /**
     * @param customView
     */
    public void setCustomView(View customView) {
        mCustomView = customView;
        if (customView != null) {
            if (mTitleTextView != null) {
                mTitleTextView.setVisibility(GONE);
            }
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            mCustomView.setLayoutParams(layoutParams);
            addView(customView, layoutParams);
        }
    }


    public void setCustomViewOnClickListener(OnClickListener listener) {
        if (mCustomView != null) {
            mCustomView.setOnClickListener(listener);
        }
    }

    public View getmCustomView() {
        return mCustomView;
    }


    /**
     * set Navigation  ClickListener
     *
     * @param listener
     */
    public void setNavigationOnClickListener(OnClickListener listener) {
//        ensureNavButtonView();
        if (mNavButtonView != null)
            mNavButtonView.setOnClickListener(listener);
    }

    /**
     * set RightButton  ClickListener
     *
     * @param listener
     */
    public void setRightButtonOnClickListener(OnClickListener listener) {
        //ensureRightButtonView();
        if (mRightButtonView != null)
            mRightButtonView.setOnClickListener(listener);

    }

    /**
     * set Search ClickListener
     *
     * @param listener
     */
    public void setSearchViewOnClickListener(OnClickListener listener) {
        if (mSearchView != null)
            mSearchView.setOnClickListener(listener);

    }

    /**
     * set Right ClickListener
     *
     * @param listener
     */
    public void setRightTextOnClickListener(OnClickListener listener) {
        if (mRightTextView != null)
            mRightTextView.setOnClickListener(listener);

    }

    public void setLeftTextOnClickListener(OnClickListener listener) {
        if (mLeftTextView != null)
            mLeftTextView.setOnClickListener(listener);
    }

    public void setLogoOnClickListener(OnClickListener listener) {
        if (logoView != null)
            logoView.setOnClickListener(listener);

    }

    public void setSearchDrawableRight(Drawable icon) {
        searchDrawable = icon;
        if (mSearchView != null) {
            mSearchView.setCompoundDrawablesWithIntrinsicBounds(null, null, searchDrawable, null);
        }
    }

    public void setSearchDrawableLeft(Drawable icon) {
        searchDrawable = icon;
        if (mSearchView != null) {
            mSearchView.setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
        }
    }

    public void setSearchText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mSearchView == null) {
                mSearchView = new EditText(getContext());
                mSearchView.setSingleLine();
                mSearchView.setEllipsize(TextUtils.TruncateAt.END);
                mSearchView.setClickable(true);
                if (mSearchHintTextColor != 0) {
                    mSearchView.setHintTextColor(mSearchHintTextColor);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mSearchView.setLayoutParams(layoutParams);
                addView(mSearchView, layoutParams);
            }
        }
        if (mSearchView != null) {
            mSearchView.setHint(title);
        }
        mSearchHintText = title;
    }


    public void setSearchHintTextColor(@ColorInt int color) {
        mSearchHintTextColor = color;
        if (mSearchView != null) {
            mSearchView.setHintTextColor(mSearchHintTextColor);
        }
    }


    public void setSearchHintText(@StringRes int resId) {
        setSearchText(getContext().getText(resId));
    }

    public CharSequence getSearchHintText() {
        return mSearchHintText;
    }


    private void ensureRightButtonView() {
        if (mRightButtonView == null) {
            mRightButtonView = new ImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            mRightButtonView.setId(R.id.m_right_button);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mRightButtonView.setLayoutParams(layoutParams);
            addView(mRightButtonView, layoutParams);
        }
    }


    public void setRightButtonIcon(@Nullable Drawable icon) {
        if (icon != null) {
            ensureRightButtonView();
        }
        if (mRightButtonView != null) {
            mRightButtonView.setImageDrawable(icon);
        }
    }


    public void setRightButtonIcon(@DrawableRes int resId) {
        setRightButtonIcon(mDrawableManager.getDrawable(getContext(), resId));
    }

    public void setNavigationIcon(@DrawableRes int resId) {
        setNavButtonIcon(mDrawableManager.getDrawable(getContext(), resId));
    }

    public void setNavButtonIcon(@Nullable Drawable icon) {
        if (icon != null) {
            ensureNavButtonView();
        }
        if (mNavButtonView != null) {
            mNavButtonView.setImageDrawable(icon);
        }
    }

    private void ensureNavButtonView() {
        if (mNavButtonView == null) {
            mNavButtonView = new ImageButton(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            mNavButtonView.setId(R.id.m_nav_button);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mNavButtonView.setLayoutParams(layoutParams);
            addView(mNavButtonView, layoutParams);
        }
    }

    public void setLogoIcon(@DrawableRes int resId) {
        setLogoIcon(mDrawableManager.getDrawable(getContext(), resId));
    }

    public void setLogoIcon(@Nullable Drawable icon) {
        if (icon != null) {
            ensureLogoView();
        }
        if (logoView != null) {
            logoView.setImageDrawable(icon);
        }
    }

    private void ensureLogoView() {
        if (logoView == null) {
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            if (!mLogoShape) {
//                logoView = new CircleImageView(getContext());
                logoView = new ImageView(getContext(), null, R.attr.toolbarNavigationButtonStyle);
                layoutParams = new LayoutParams(100, 100);
            } else {
                logoView = new ImageView(getContext(), null, R.attr.toolbarNavigationButtonStyle);
            }
            logoView.setId(R.id.m_logo);
            if (mLeftTextView != null) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.m_left_text);
            } else if (mNavButtonView != null) {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.m_nav_button);
            } else {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            logoView.setLayoutParams(layoutParams);
            addView(logoView, layoutParams);
        }
    }

    public void setLeftText(@StringRes int resId) {
        setLeftText(getContext().getText(resId));
    }

    public void setLeftTextSize(float size) {
        mLeftTextSize = size;
        if (mLeftTextView != null) {
            mLeftTextView.setTextSize(size);
        }
    }


    public void setLeftText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mLeftTextView == null) {
                mLeftTextView = new TextView(getContext());
                mLeftTextView.setSingleLine();
                mLeftTextView.setGravity(Gravity.CENTER);
                mLeftTextView.setId(R.id.m_left_text);
                mLeftTextView.setEllipsize(TextUtils.TruncateAt.END);
                mLeftTextView.setMaxEms(4);
                if (mLeftTextColor != 0) {
                    mLeftTextView.setTextColor(mLeftTextColor);
                } else {
                    mLeftTextView.setTextColor(getResources().getColor(R.color.white_color));
                }
                if (mLeftTextSize != 0) {
                    mLeftTextView.setTextSize(mLeftTextSize);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                layoutParams.alignWithParent = true;
                if (mNavButtonView == null) {
                    layoutParams.setMargins(mleftTextMarginLeft, 0, 5, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                } else {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.m_nav_button);
                }
                mLeftTextView.setLayoutParams(layoutParams);
                addView(mLeftTextView, layoutParams);
            }
        }
        if (mLeftTextView != null) {
            mLeftTextView.setText(title);
        }
        mLeftText = title;
    }

    public CharSequence getLeftText() {
        return mLeftText;
    }


    public void setLeftTextColor(@ColorInt int color) {
        mLeftTextColor = color;
        if (mLeftTextView != null) {
            mLeftTextView.setTextColor(color);
        }
    }

    public void setRightTextSize(float size) {
        mRightTextSize = size;
        if (mRightTextView != null) {
            mRightTextView.setTextSize(size);
        }
    }


    public void setRightText(@StringRes int resId) {
        setRightText(getContext().getText(resId));
    }

    public CharSequence getRightText() {
        return mRightText;
    }

    public void setRightText(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mRightTextView == null) {
                mRightTextView = new TextView(getContext());
                mRightTextView.setSingleLine();
                mRightTextView.setGravity(Gravity.CENTER);
                mRightTextView.setEllipsize(TextUtils.TruncateAt.END);
                mRightTextView.setId(R.id.m_right_text);
                if (mRightTextColor != 0) {
                    mRightTextView.setTextColor(mRightTextColor);
                } else {
                    mRightTextView.setTextColor(getResources().getColor(R.color.white_color));
                }
                if (mRightTextSize != 0) {
                    mRightTextView.setTextSize(mRightTextSize);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.alignWithParent = true;
                if (mRightButtonView == null) {
                    layoutParams.setMargins(5, 0, mRightTextMarginRight, 0);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                } else {
                    layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.m_right_button);
                }
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                mRightTextView.setLayoutParams(layoutParams);
                addView(mRightTextView, layoutParams);
            }
        }
        if (mRightTextView != null) {
            mRightTextView.setText(title);
        }
        mRightText = title;
    }


    public void setRightTextColor(@ColorInt int color) {
        mRightTextColor = color;
        if (mRightTextView != null) {
            mRightTextView.setTextColor(color);
        }
    }


    public void setTitleTextColor(@ColorInt int color) {
        mTitleTextColor = color;
        if (mTitleTextView != null) {
            mTitleTextView.setTextColor(color);
        }
    }

    public void setTitleSize(float size) {
        mTitleSize = size;
        if (mTitleTextView != null) {
            mTitleTextView.setTextSize(size);
        }
    }


    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (mTitleTextView == null) {
                mTitleTextView = new TextView(getContext());
                mTitleTextView.setSingleLine();
                mTitleTextView.setGravity(Gravity.CENTER);
                mTitleTextView.setTextSize(20);
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (mTitleTextColor != 0) {
                    mTitleTextView.setTextColor(mTitleTextColor);
                } else {
                    mTitleTextView.setTextColor(getResources().getColor(R.color.white_color));
                }
                if (mTitleSize != 0) {
                    mTitleTextView.setTextSize(mTitleSize);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                mTitleTextView.setLayoutParams(layoutParams);
                addView(mTitleTextView, layoutParams);
            }
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
        if (mCustomView != null) {
            mCustomView.setVisibility(GONE);
        }
        mTitleTextView.setVisibility(VISIBLE);
        mTitleText = title;
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    public CharSequence getTitle() {
        return mTitleText;
    }


    private int dpFromPx(final float px) {
        return (int) (px / getResources().getDisplayMetrics().density);
    }

    private int pxFromDp(final float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    public ImageButton getmRightButtonView() {
        return mRightButtonView;
    }


    public void showCustomView() {
        if (mCustomView != null) {
            mCustomView.setVisibility(VISIBLE);
            mTitleTextView.setVisibility(GONE);
        }
    }

    public void showLeftTextView() {
        if (mLeftTextView != null)
            mLeftTextView.setVisibility(VISIBLE);
    }

    public void hideLeftTextView() {
        if (mLeftTextView != null)
            mLeftTextView.setVisibility(GONE);
    }

    public void showRightTextView() {
        if (mRightTextView != null)
            mRightTextView.setVisibility(VISIBLE);
    }

    public void hideRightTextView() {
        if (mRightTextView != null)
            mRightTextView.setVisibility(GONE);
    }
}
