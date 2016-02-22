package com.qunar.flight.inter.itts;

/**
 * @author guowangsun
 */
public class HttpGetDemoTest {

    @org.junit.Test
    public void testHttpGet() throws Exception {
        System.out.println(HttpGetDemo.httpGet("http://www.sina.com", null));
    }
}