package com.dj.displayutil;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

/**
 * 显示相关工具类
 * Created by wangjing4 on 2017/8/22.
 */

public class DisPlayUtils {
    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取当前activity屏幕亮度
     *
     * @return
     */
    public static float getActivityScreenBright(Activity activity) {
        float brightness = activity.getWindow().getAttributes().screenBrightness;
        if (brightness <= 0.01f)
            brightness = getScreenBrightness(activity);
        return brightness;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 获取系统设置屏幕亮度
     */
    public static float getScreenBrightness(Context context) {
        int value = 0;
        ContentResolver cr = context.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.SecurityException e) {
            e.printStackTrace();
        }
        return value / 255f;
    }
}
