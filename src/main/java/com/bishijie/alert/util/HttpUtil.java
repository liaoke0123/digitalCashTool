/**
 * hxgy Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.bishijie.alert.util;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Response;
import com.ning.http.client.cookie.Cookie;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * Http Request Util
 * @author Rick
 * The dependency :
    <dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>fluent-hc</artifactId>
    <version>4.3.1</version>
    </dependency>
    <dependency>
    <groupId>com.ning</groupId>
    <artifactId>async-http-client</artifactId>
    <version>1.9.40</version>
    </dependency>
 */
public class HttpUtil {

    private static final boolean validCookies = true; //turn on the cookie

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final int SO_TIMEOUT = 45000;

    private static final int MAX_TOTAL_CONNECTION = 1000;

    public static AsyncHttpClient asyncHttpClient;

    public static Set<Cookie> cookies =  new HashSet<>();



    /** SETUP Async Request Object */
    static {
        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
        builder.setMaxConnections(MAX_TOTAL_CONNECTION);
        builder.setMaxConnectionsPerHost(90);
        builder.setRequestTimeout(SO_TIMEOUT);
        asyncHttpClient = new AsyncHttpClient(builder.build());
    }

    private HttpUtil() {
        throw new IllegalAccessError("Utility class");
    }






    /**
     * get request - root method
     * the result is String
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static String get(String url, Map<String, String> params,
                             Map<String, String> headers) {
        AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        useCookie(builder);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParam(key, params.get(key));
            }
        }
        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = null;
        try {
            Response asd = f.get();
            body = f.get().getResponseBody(DEFAULT_CHARSET);
            addCookie(f.get().getCookies());
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Get Request(the result is String)").msg("error,url={}").build(), url, e);
        }
        return body;
    }

    /**
     * get quest
     * the result is InputStream
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static InputStream getAndResultIsInputStream(String url, Map<String, String> params,
                                    Map<String, String> headers) {
        AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        useCookie(builder);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParam(key, params.get(key));
            }
        }
        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Future<Response> f = builder.execute();
        InputStream body = null;
        try {
            body = f.get().getResponseBodyAsStream();
            addCookie(f.get().getCookies());
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Get Request(the result is InputStream)").msg("error,url={}").build(), url, e);
        }
        return body;
    }

    /**
     * get request
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * get request
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    /**
     * the post request -- root method
     * the result is String
     * the params is a map
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        useCookie(builder);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParam(key, String.valueOf(params.get(key)));
            }
        }
        Future<Response> f = builder.execute();
        String body = null;
        try {
            body = f.get().getResponseBody(DEFAULT_CHARSET);
            addCookie(f.get().getCookies());
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Post Request(the result is String,the params is a map)").msg("error,url={}").build(), url, e);
        }
        return body;
    }


    /**
     * the post request -- root method
     * the result is String
     * the params is a String in request body (Json)
     * @param url
     * @param s
     * @return
     */
    public static String post(String url, String s,Map<String, String> headers) {
        AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(s);
        useCookie(builder);
        builder.setHeader("Content-Type", DEFAULT_CONTENT_TYPE);
        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, headers.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = null;
        try {
            body = f.get().getResponseBody(DEFAULT_CHARSET);
            addCookie(f.get().getCookies());
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Post Request(the result is String,the param is a string in request body)").msg("error,url={}").build(), url, e);
        }
        return body;
    }


    /**
     * do post by custom contentType
     * not use cookies
     * @param url
     * @param params
     * @param contentType
     * @return
     */
    public static String postWithCustomContentType(String url, String params, String contentType) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", contentType);
        HttpEntity entity = new StringEntity(params);
        httpPost.setEntity(entity);
        HttpResponse response = client.execute(httpPost);
        String respString = "";
        if (response.getStatusLine().getStatusCode() == 200) {
            InputStream inSm = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(inSm, DEFAULT_CHARSET));
            for (String temp = br.readLine(); temp != null; respString += temp, temp = br
                    .readLine()) ;
        } else {
            throw new RuntimeException(LogUtil.builder().method("Post Request(the result is String,the param is a string with custom contentType)").msg("error").build());
        }
        return respString;
    }

    /**
     * do post by custom contentType and charset
     * @param url
     * @param params
     * @param charset
     * @param contentType
     * @return
     */
    public static String postWithCustomContentTypeAndCharset(String url, String params, String charset,
                                String contentType)  {
        AsyncHttpClient.BoundRequestBuilder builder = asyncHttpClient.preparePost(url);
        builder.setBodyEncoding(charset);
        builder.setBody(params);
        builder.setHeader("Content-Type", contentType);
        useCookie(builder);
        Future<Response> f = builder.execute();
        String body = null;
        try {
            body = f.get().getResponseBody(DEFAULT_CHARSET);
            addCookie(f.get().getCookies());
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Post Request(the result is String,the param is a string with custom contentType and charset)").msg("error,url={}").build(), url, e);
        }
        return body;
    }

    /**
     * post with ssl
     * params is String in request body
     * @param url
     * @param params
     * @param contentType
     * @param sslContext
     * @return
     */
    public static String postBySSLAndParamsInRequestBody(String url, String params, String contentType, SSLContext sslContext)  {
        AsyncHttpClient http = new AsyncHttpClient(new AsyncHttpClientConfig.Builder()
                .setConnectTimeout(SO_TIMEOUT).setSSLContext(sslContext).build());
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(params);
        builder.setHeader("Content-Type", contentType);
        useCookie(builder);
        Future<Response> f = builder.execute();
        String body = null;
        try {
            body = f.get().getResponseBody(DEFAULT_CHARSET);
            addCookie(f.get().getCookies());
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Post Request(the result is String ,the params is a String in request body with custom contentType and a sslContext )").msg("error,url={}").build(), url, e);
        }
        http.close();
        return body;
    }


    /**
     * post with ssl
     * params is Map in query
     * @param url
     * @param params
     * @param contentType
     * @param sslContext
     * @return
     */
    public static String postBySSLAndParamsInQuery(String url, Map<String, String> params, String contentType,
                                       SSLContext sslContext)  {
        AsyncHttpClient http = new AsyncHttpClient(new AsyncHttpClientConfig.Builder()
                .setConnectTimeout(SO_TIMEOUT).setSSLContext(sslContext).build());
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParam(key, params.get(key));
            }
        }
        builder.setHeader("Content-Type", contentType);
        Future<Response> f = builder.execute();
        String body = null;
        try {
            body = f.get().getResponseBody(DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error(LogUtil.builder().method("Post Request(the result is String ,the params is a map in query body with custom contentType and a sslContext )").msg("error,url={}").build(), url, e);
        }
        http.close();
        return body;
    }


    /**
     * add cookie
     * @param responseCookies
     */
    public static void addCookie( List<Cookie>  responseCookies){
        if(!validCookies){
            return;
        }
        if(responseCookies!=null&&responseCookies.size()>0){
            for(Cookie cookie:responseCookies){
                cookies.add(cookie);
            }
        }
    }

    /**
     * use cookie
     * @param builder
     */
    public static void useCookie( AsyncHttpClient.BoundRequestBuilder builder){
        if(!validCookies){
            return;
        }
        builder.setCookies(cookies);
    }

}
