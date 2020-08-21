package com.dj.bugly.annotation;


import androidx.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by wangjing4 on 2017/7/20.
 */
@Retention(CLASS) @Target(FIELD)
public @interface AnntationExceptionName {
    @IdRes int value();
}
