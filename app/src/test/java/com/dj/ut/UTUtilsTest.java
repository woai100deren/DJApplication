package com.dj.ut;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UTUtilsTest {
    UTUtils utUtils;
    @Before
    public  void before() throws Exception{
        utUtils = new UTUtils();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() {
        Assert.assertEquals(4,utUtils.add(1,3));
    }

    @Test
    public void checkUrl() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class s = UTUtils.class;
        Method method = s.getDeclaredMethod("checkUrl",String.class);
        method.setAccessible(true);
//        Assert.assertFalse((Boolean) method.invoke(utUtils,null));
        Assert.assertFalse((Boolean) method.invoke(utUtils,""));
        Assert.assertFalse((Boolean) method.invoke(utUtils," "));
        Assert.assertFalse((Boolean) method.invoke(utUtils,"htt://wwww.asdasdad.com/aaa.apk"));
        Assert.assertFalse((Boolean) method.invoke(utUtils,"Https://wwww.asdasdad.com/aaa.ap"));
        Assert.assertFalse((Boolean) method.invoke(utUtils,"Https//wwww.asdasdad.com/aaa.apK"));
        Assert.assertFalse((Boolean) method.invoke(utUtils,"Https:/wwww.asdasdad.com/aaa.apK"));

        Assert.assertTrue((Boolean) method.invoke(utUtils,"http://wwww.asdasdad.com/aaa.apk"));
        Assert.assertTrue((Boolean) method.invoke(utUtils,"Http://wwww.asdasdad.com/aaa.apk"));
        Assert.assertTrue((Boolean) method.invoke(utUtils,"Https://wwww.asdasdad.com/aaa.apk"));
    }
}