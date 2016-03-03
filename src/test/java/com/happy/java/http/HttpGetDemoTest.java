package com.happy.java.http;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author guowangsun
 */
public class HttpGetDemoTest {

    @org.junit.Test
    public void testHttpGet() throws Exception {
        System.out.println(HttpGetDemo.httpGet("http://www.sina.com", null));
    }

    @org.junit.Test
    public void testHttpGetHeader() throws Exception {

    }
}