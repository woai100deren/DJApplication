package com.dj.numberutil;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 数字相关工具类
 * Created by wangjing4 on 2018/08/22
 */
public class NumberUtils {

    /**
     * 字符转int
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    /**
     * 支持小数点，向上取整
     * @param str
     * @param defaultValue
     * @return
     */
    public static int parseInt(String str, int defaultValue) {
        int number = defaultValue;
        if (!TextUtils.isEmpty(str)) {
            try {
                Double d = Double.parseDouble(str);
                d = Math.ceil(d);
                number = d.intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return number;
    }

    /**
     * 字符转float
     * @param str
     * @return
     */
    public static float parseFloat(String str) {
        float number = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                number = Float.parseFloat(str);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return number;

    }

    /**
     * 字符转double
     * @param str
     * @return
     */
    public static double parseDouble(String str) {
        double number = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return number;
    }


    /**
     * 支持小数点，向上取整
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        long number = 0;
        if (!TextUtils.isEmpty(str)) {
            try {
                Double d = Double.parseDouble(str);
                d = Math.ceil(d);
                number = d.longValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return number;
    }

    /**
     * 末尾为0省略0,未四舍五入
     * @param s
     * @return
     */
    public static String getDoubleStrWithOneDecimal(String s) {
        try {
            if (s.indexOf(".") != -1) {
                s = s.substring(0, s.indexOf(".") + 2);
                if (s.charAt(s.length() - 1) == '0') {
                    s = s.substring(0, s.indexOf("."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * @return 获得区间内的一个随机数
     */
    public static int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }


    /**
     * 金额转化为120,000
     *
     * @param data
     * @return
     */
    public static String formatTosepara(String data) {

        double amount = NumberUtils.parseDouble(data);
        DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(amount);
    }

    /**
     * float保留4位小数
     *
     * @param number
     * @return
     */
    public static float get4bitFloat(float number) {
        DecimalFormat df = new DecimalFormat("#.####");
        return Float.valueOf(df.format(number));
    }

    /**
     * 判断字符串是否全为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 对传入参数除100,默认整除时保留小数位
     *
     * @param num
     * @param decNum 保留的小数位
     * @param decNum 末位为.0...时是否保留小数位
     * @return
     */
    public static String divideHundred(long num, int decNum, boolean retainDec) {
        BigDecimal result = new BigDecimal(num).divide(new BigDecimal(100)).setScale(decNum >= 0 ? decNum : 0, BigDecimal.ROUND_FLOOR);
        if (!retainDec) {
            return getDoubleStrWithOneDecimal(result.toString());
        } else {
            return result.toString();
        }
    }

    /**
     * 精确的 乘法运算
     * @param var1 double 乘数1
     * @param var2 double 乘数2
     * @return
     */
    public static double mul(double var1, double var2) {

        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(var1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(var2));
        return bigDecimal1.multiply(bigDecimal2).doubleValue();
    }

    /**
     * 精确的 加运算
     * @param var1 参数1
     * @param var2 参数1
     * @return
     */
    public static double add(double var1, double var2) {

        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(var1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(var2));
        return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    /**
     * 精确的 减法运算
     * @param var1 参数1
     * @param var2 参数2
     * @return
     */
    public static double sub(double var1, double var2) {

        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(var1));
        BigDecimal bigDecimal2 = new BigDecimal(Double.toString(var2));
        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }
}
