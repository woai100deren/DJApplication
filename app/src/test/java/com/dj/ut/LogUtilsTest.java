package com.dj.ut;

import com.dj.logutil.LogUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LogUtilsTest {
    @Test
    public void e() {
        LogUtils.setDebug(true);
        LogUtils.e("123");
    }
}