package com.qunar.flight.inter.itts;

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
        Map<String, String> params = Maps.newHashMap();
        params.put("searchCondition", "{\"extData\":null,\"fromDate\":\"2016-05-27\",\"toCity\":\"SIN\",\"domainList\":[\"bj1.trade.qunar.com\"],\"retDate\":null,\"filterCondition\":null,\"fromCity\":\"PEK\",\"queryId\":\"qunar20160201\"}");
        Map<String, String> header = Maps.newHashMap();
        header.put("Content-Type", "application/json");
        System.out.println(HttpGetDemo.httpGet("http://l-ttstwi99.f.beta.cn0.qunar.com:8078/debug/syncSearch", params, header));
    }
}