package com.util;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

/**
 * Created by wangjing4 on 2017/7/19.
 */
public class DYTestUtilsTest {
    DYTestUtils dyTestUtils;
    @Before
    public void setUp() throws Exception {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.initMocks(this);
        dyTestUtils = spy(DYTestUtils.class);
    }
    @Test
    public void add() throws Exception {
        dyTestUtils.add(1,2);
//        verify(dyTestUtils).returnAdd(1,2);
//        verify(dyTestUtils).add(1,2);
    }

    @Test
    public void returnAdd() throws Exception {
        DYTestUtils dyTestUtils = new DYTestUtils();
        Boolean isFlag = (Boolean) Whitebox.getInternalState(dyTestUtils,"isFlag");
        System.out.println("设置前："+isFlag);
        assertEquals(dyTestUtils.returnAdd(1,2),5);
        dyTestUtils.setFlag(true);
        isFlag = (Boolean) Whitebox.getInternalState(dyTestUtils,"isFlag");
        System.out.println("设置后："+isFlag);
        assertEquals(dyTestUtils.returnAdd(1,2),3);
    }
}