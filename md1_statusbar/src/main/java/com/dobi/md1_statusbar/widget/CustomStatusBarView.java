package com.dobi.md1_statusbar.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.dobi.md1_statusbar.R;


/**
 * 自定义一个假的 状态栏
 *
 * Created by yw
 */

public class CustomStatusBarView extends View {
    public static final String TAG = "CustomStatusBarView";

    private int statusBarHeight;
    private boolean showAlways = false;
    private int showAfterSdkVersionInt = 123454321;
    private Context mContext;

    public CustomStatusBarView(Context context) {
        this(context, null);
    }

    public CustomStatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomStatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomStatusBarView, defStyleAttr, defStyleAttr);

        try {
            showAlways = a.getBoolean(R.styleable.CustomStatusBarView_showAlways, showAlways);

            if (!showAlways) {
                showAfterSdkVersionInt = a.getInt(R.styleable.CustomStatusBarView_showAfterSdkVersionInt, showAfterSdkVersionInt);
            }
        } finally {
            a.recycle();
        }
        initUI();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int h = resolveSize(statusBarHeight, heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    private void initUI() {
        if (isInEditMode()) {
            statusBarHeight = 0;
            return ;
        }

        if (Build.VERSION.SDK_INT < 19) {
            statusBarHeight = 0;
            return ;
        }

        statusBarHeight = getStatusBarHeight(getContext());
//        setBackgroundResource(R.color.tongzhi);

    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        // 获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            // 根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }

        if (statusBarHeight == -1) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (statusBarHeight == -1) {
            statusBarHeight = dpTopx(context, 25);
        }

        return statusBarHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}