//package com.dj.ca.aspect;
//
//import android.util.Log;
//import android.view.View;
//
//import com.dj.ca.ann.SingleClick;
//import com.dj.collection.R;
//import com.dj.logutil.LogUtils;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.lang.reflect.Method;
//import java.util.Calendar;
//
//@Aspect
//public class SingleClickAspect {
//    static int TIME_TAG = R.id.click_time;
//    public static final int MIN_CLICK_DELAY_TIME = 1000;//间隔时间600ms
//
//
//    //execution方法里面要填写你自己的完整的包名路径：xxx.xxx.xxx.SingleClick
//    @Pointcut("execution(@com.dj.ca.ann.SingleClick * *(..)) && @annotation(singleClick)")
//    public void methodAnnotated(SingleClick singleClick) { }
//
//    @Around("methodAnnotated(singleClick)")
//    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, SingleClick singleClick) throws  Throwable{
//        LogUtils.e("注解的值是："+singleClick.describe());
//
//        View view=null;
//        for (Object  arg :joinPoint.getArgs()){
//            if(arg instanceof  View ){
//                view =(View)arg;
//            }
//        }
//
//        if(view!=null){
//            Object  tag=view.getTag(TIME_TAG);
//            long lastClickTime= ((tag != null) ? (long) tag : 0);
//            LogUtils.e( "lastClickTime:" + lastClickTime);
//            long currentTime= Calendar.getInstance().getTimeInMillis();
//            if(currentTime -lastClickTime >MIN_CLICK_DELAY_TIME){//过滤掉600毫秒内的连续点击
//                view.setTag(TIME_TAG,currentTime);
//                LogUtils.e( "currentTime:" + currentTime);
//                joinPoint.proceed();//执行原方法
//
//            }
//        }
//    }
//}
//
