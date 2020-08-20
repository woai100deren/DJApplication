package com.dj.ut;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UTUtilsTest {
    UTUtils utUtils;
    @Before
    public  void before() throws Exception{
        utUtils = new UTUtils();
        System.out.println("每个test方法前都会调用");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void add() {
        Assert.assertEquals(4,utUtils.add(1,3));
    }
}