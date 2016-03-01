package com.happy.java.http;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Closer;
import com.happy.java.utils.HttpUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author guowangsun
 */
public class HttpGetDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpGetDemo.class);

    /**
     * http get request
     *
     * @param url    URL
     * @param params params
     * @return response
     */
    public static String httpGet(String url, Map<String, String> params) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "URL is null or empty!");

        String result = null;
        Closer closer = Closer.create();
        CloseableHttpClient httpClient = closer.register(HttpClients.createDefault());
        try {
            HttpGet httpGet;
            if (params == null || params.size() == 0) {
                httpGet = new HttpGet(url);
            } else {
                URIBuilder uriBuilder = new URIBuilder(url);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
                httpGet = new HttpGet(uriBuilder.build());
            }
            CloseableHttpResponse httpResponse = closer.register(httpClient.execute(httpGet));
            result = HttpUtils.readStringFromStream(closer.register(httpResponse.getEntity().getContent()), "utf-8");
        } catch (ClientProtocolException e) {
            LOGGER.error("ClientProtocolException", e);
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        } catch (Exception e) {
            LOGGER.error("HttpGet Exception", e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                LOGGER.error("close Exception", e);
            }
        }
        return result;
    }

    public static String httpGet(String url, Map<String, String> params, Map<String, String> headers) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "URL is null or empty!");
        Preconditions.checkArgument(headers != null && !headers.isEmpty(), "header is null or empty!");

        String result = null;
        Closer closer = Closer.create();
        CloseableHttpClient httpClient = closer.register(HttpClients.createDefault());
        try {
            HttpGet httpGet;
            if (params == null || params.size() == 0) {
                httpGet = new HttpGet(url);
            } else {
                URIBuilder uriBuilder = new URIBuilder(url);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
                httpGet = new HttpGet(uriBuilder.build());
            }
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
            CloseableHttpResponse httpResponse = closer.register(httpClient.execute(httpGet));
            result = HttpUtils.readStringFromStream(closer.register(httpResponse.getEntity().getContent()), "utf-8");
        } catch (ClientProtocolException e) {
            LOGGER.error("ClientProtocolException", e);
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        } catch (Exception e) {
            LOGGER.error("HttpGet Exception", e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                LOGGER.error("close Exception", e);
            }
        }
        return result;
    }
}
