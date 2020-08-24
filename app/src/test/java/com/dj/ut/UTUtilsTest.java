package com.dj.ut;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    public void testLogin() throws Exception{
        UTUtils utUtils = mock(UTUtils.class);
        UTNetworkCallback utNetworkCallback = mock(UTNetworkCallback.class);
        doAnswer(new Answer<UTNetworkCallback>(){
            @Override
            public UTNetworkCallback answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                UTNetworkCallback networkCallback = (UTNetworkCallback) arguments[2];
                networkCallback.onFailure("500");

                return networkCallback;
            }
        }).when(utUtils).login(anyString(),anyString(),any(UTNetworkCallback.class));
        doNothing().when(utNetworkCallback).onFailure("500");
        utUtils.login("TaiaoPi0000", "123456", utNetworkCallback);
        verify(utUtils).login("TaiaoPi0000","123456", utNetworkCallback);
    }

    @Test
    public void testLoginApp ()  throws Exception{
        UTUtils jUnitTest = mock(UTUtils.class);
        //这里什么都不做   执行了就好
        doNothing().when(jUnitTest).loginApp(anyString(),anyString());
        jUnitTest.loginApp("TaiaoPi","123456");
        verify(jUnitTest).loginApp("TaiaoPi","123456");
    }
}