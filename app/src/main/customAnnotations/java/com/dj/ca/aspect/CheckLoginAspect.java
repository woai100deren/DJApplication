package com.dj.ca.aspect;


import android.text.TextUtils;

import com.dj.ca.ann.CheckLogin;
import com.dj.logutil.LogUtils;
import com.eventbus.event.LoginEvent;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.greenrobot.eventbus.EventBus;

@Aspect
public class CheckLoginAspect {
    //execution方法里面要填写你自己的完整的包名路径：xxx.xxx.xxx.SingleClick
    @Pointcut("execution(@com.dj.ca.ann.CheckLogin * *(..)) && @annotation(checkLogin)")
    public void methodAnnotated(CheckLogin checkLogin) { }

    @Around("methodAnnotated(checkLogin)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, CheckLogin checkLogin) throws  Throwable{
        if(checkLogin.toLogin()){
            LogUtils.e("需要登陆");
            EventBus.getDefault().post(new LoginEvent());
        }else{
            LogUtils.e("不需要登陆");
            joinPoint.proceed();
        }

        if(!TextUtils.isEmpty(checkLogin.toastInfo())){
            LogUtils.e(checkLogin.toastInfo());
        }
    }
}

