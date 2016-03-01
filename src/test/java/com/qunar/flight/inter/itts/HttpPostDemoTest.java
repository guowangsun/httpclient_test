package com.qunar.flight.inter.itts;

/**
 * @author guowangsun
 */
public class HttpPostDemoTest {

    @org.junit.Test
    public void testHttpPost() throws Exception {
        System.out.println(HttpPostDemo.httpPost("http://10.86.140.144:8081/post", null));
    }
}