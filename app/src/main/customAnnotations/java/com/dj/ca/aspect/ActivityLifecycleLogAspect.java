package com.dj.ca.aspect;


import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ActivityLifecycleLogAspect {
    private static final String TAG = ActivityLifecycleLogAspect.class.getSimpleName();

    @Pointcut("execution(* com.dj.ca.CustomAnnotationsActivity.on*(..))")
    public void logActivityLifecycle() {
    }

    @Before("logActivityLifecycle()")
    public void log(final JoinPoint joinPoint) {
        Log.e(TAG, "log: " + joinPoint.toLongString());
    }
}


