package com.happy.java.http;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Closer;
import com.happy.java.utils.HttpUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author guowangsun
 */
public class HttpPostDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostDemo.class);

    /**
     * http post request
     *
     * @param url    URL
     * @param params params
     * @return response
     */
    public static String httpPost(String url, Map<String, String> params) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "URL is null or empty!");

        String result = null;
        Closer closer = Closer.create();
        CloseableHttpClient httpClient = closer.register(HttpClients.createDefault());
        try {
            List<NameValuePair> formParams = Lists.newArrayList();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = closer.register(httpClient.execute(httpPost));
            String contentType = httpResponse.getEntity().getContentType().getValue();
            String charset = null;
            if (contentType.contains("charset")) {
                charset = contentType.substring(contentType.indexOf("charset") + 8);
            }
            result = HttpUtils.readStringFromStream(closer.register(httpResponse.getEntity().getContent()), charset);
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
