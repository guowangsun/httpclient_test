package com.qunar.flight.inter.itts;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.Closer;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            result = readStringFromStream(closer.register(httpResponse.getEntity().getContent()), "utf-8");
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

    private static String readStringFromStream(InputStream inputStream, String charset) throws IOException {
        int index = 0;
        char[] resultCharArr = new char[1024];
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        char[] buffer = new char[1024];
        int length;
        while ((length = bufferedReader.read(buffer)) != -1) {
            if (index + length > resultCharArr.length) {
                char[] newResultCharArr = new char[resultCharArr.length * 2];
                System.arraycopy(resultCharArr, 0, newResultCharArr, 0, resultCharArr.length);
                resultCharArr = newResultCharArr;
            }
            System.arraycopy(buffer, 0, resultCharArr, index, length);
            index += length;
        }
        return new String(resultCharArr);
    }
}
