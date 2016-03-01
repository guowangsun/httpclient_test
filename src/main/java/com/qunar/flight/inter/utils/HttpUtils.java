package com.qunar.flight.inter.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author guowangsun
 */
public class HttpUtils {

    /**
     * Read content from inputStream, use offer charset.
     * If charset is null or empty, charset use utf-8
     *
     * @param inputStream inputStream
     * @param charset     charset
     * @return content
     * @throws IOException
     */
    public static String readStringFromStream(InputStream inputStream, String charset) throws IOException {
        Preconditions.checkNotNull(inputStream, "inputStream is null");
        if (Strings.isNullOrEmpty(charset)) {
            charset = "utf-8";
        }
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
