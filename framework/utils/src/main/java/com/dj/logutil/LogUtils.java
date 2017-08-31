package com.dj.logutil;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by wangjing4 on 2017/8/22.
 */

public class LogUtils {
    public static final String LOG_TAG = "com.dj";
    private static boolean DEBUG = false;
    private static boolean NATIVE_LOG = true;

    public static void setDebug(boolean DEBUG) {
        LogUtils.DEBUG = DEBUG;
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void setNativeLog(boolean nativeLog) {
        NATIVE_LOG = nativeLog;
    }

    /**
     * 格式化打印json数据
     *
     * @param tag
     * @param jsonStr
     */
    public static void json(String tag, String jsonStr) {
        if (DEBUG) {
            Logger.t(tag).json(jsonStr);
        }
    }

    /**
     * 格式化打印json数据
     *
     * @param jsonStr
     */
    public static void json(String jsonStr) {
        if (DEBUG) {
            Logger.json(jsonStr);
        }
    }

    /**
     * 格式化打印xml数据
     *
     * @param tag
     * @param xmlStr
     */
    public static void xml(String tag, String xmlStr) {
        if (DEBUG) {
            Logger.t(tag).xml(xmlStr);
        }
    }

    /**
     * 格式化打印xml数据
     *
     * @param xmlStr
     */
    public static void xml(String xmlStr) {
        if (DEBUG) {
            Logger.xml(xmlStr);
        }
    }


    public static void i(String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(null), buildMessage(msg));
            } else {
                Logger.i(msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(tag), buildMessage(msg));
            } else {
                Logger.t(tag).i(msg);
            }
        }
    }

    public static void i(Object ...msg){
        if (DEBUG){
            Logger.i(buildMessage(msg));
        }
    }

    public static void v(String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(null), buildMessage(msg));
            } else {
                Logger.i(msg);
            }
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(tag), buildMessage(msg));
            } else {
                Logger.t(tag).i(msg);
            }
        }
    }

    public static void v(Object ...msg){
        if (DEBUG){
            Logger.v(buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(null), buildMessage(msg));
            } else {
                Logger.i(msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(tag), buildMessage(msg));
            } else {
                Logger.t(tag).i(msg);
            }
        }
    }

    public static void w(Object ...msg){
        if (DEBUG){
            Logger.w(buildMessage(msg));
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.e(getTag(null), buildMessage(msg));
            } else {
                Logger.e(msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.e(getTag(tag), buildMessage(msg));
            } else {
                Logger.t(tag).e(msg);
            }
        }
    }

    public static void e(Object ...msg){
        if (DEBUG){
            Logger.e(buildMessage(msg));
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(null), buildMessage(msg));
            } else {
                Logger.i(msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            if (NATIVE_LOG) {
                Log.i(getTag(tag), buildMessage(msg));
            } else {
                Logger.t(tag).i(msg);
            }
        }
//        saveLog2File(tag, msg);
    }

    public static void d(Object ...msg){
        if (DEBUG){
            Logger.d(buildMessage(msg));
        }
    }

    public static void e(Throwable thr) {
        e(null, thr);
    }

    public static void e(String tag, Throwable thr) {
        if (DEBUG) {
            Log.e(getTag(tag), Log.getStackTraceString(thr));
        }
    }

    /**
     * 拼接tag
     *
     * @param tag 后缀
     * @return 完整tag
     */
    private static String getTag(String tag) {
        if (TextUtils.isEmpty(tag) || LOG_TAG.equals(tag)) {
            return LOG_TAG;
        }
        return LOG_TAG + "-" + tag;
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        String name = caller.getClassName();
        name = name.substring(name.lastIndexOf(".") + 1, name.length());

        return new StringBuilder()
                .append("『" + name)
                .append(".")
                .append(caller.getMethodName())
                .append("()』")
                .append(TextUtils.isEmpty(msg) ? "" : msg).toString();
    }

    private static String buildMessage(Object ...msg){
        StringBuilder str = new StringBuilder();
        if (msg != null) {
            for (Object obj : msg) {
                if (obj == null) {
                    str.append("null").append(" ");
                    continue;
                }
                str.append(obj).append(" ");
            }
        } else {
            str.append("null");
        }
        return str.toString();
    }
}
