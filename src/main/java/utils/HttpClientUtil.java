package utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface HttpClientUtil {
    /**
     * httpGet
     * @param uri 访问地址+参数
     */
    static HashMap<String, String> doGetHttp(String uri){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 响应模型
        CloseableHttpResponse response = null;
        //创建get请求
        HttpGet httpGet = new HttpGet(uri);
        //返回对象
        HashMap<String, String> reMap = new HashMap<>();
        try
        {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            Header[] headers = response.getHeaders("Set-Cookie");
            String cookie = "";
            for (int j = 0; j < headers.length; j++) {
                cookie+=headers[j].getValue()+";";
            }
            reMap.put("cookie",cookie);
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                reMap.put("length",String.valueOf(responseEntity.getContentLength()));
                reMap.put("response", EntityUtils.toString(responseEntity));
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return reMap;
    }

    static HashMap<String, Object> doGetHttp(String uri,Map<String,String> header){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 响应模型
        CloseableHttpResponse response = null;
        //创建get请求
        HttpGet httpGet = new HttpGet(uri);
        //返回对象
        HashMap<String, Object> reMap = new HashMap<>();
        try
        {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);
            // 由客户端执行(发送)Get请求
            if(!header.isEmpty()){
                for (String key:header.keySet()){
                    httpGet.setHeader(key, header.get(key));
                }
            }
            response = httpClient.execute(httpGet);
            Header[] headers = response.getHeaders("Set-Cookie");
            String cookie = "";
            for (int j = 0; j < headers.length; j++) {
                cookie+=headers[j].getValue()+";";
            }
            reMap.put("header",response.getAllHeaders());
            reMap.put("cookie",cookie);
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                reMap.put("length",String.valueOf(responseEntity.getContentLength()));
                reMap.put("response", EntityUtils.toString(responseEntity));
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return reMap;
    }

    /**
     * HTTP Post    无认证的
     * @param uri   访问地址
     * @param params 参数
     * @return
     */
    static  HashMap<String, String> doPostHttp(String uri,Map<String, String> params){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, String> reMap = new HashMap<>();
        try{

            //遍历map 将其中的数据转化为表单数据
            if (null != params && !params.isEmpty()) {
                ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();//用于存放表单数据.
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"UTF-8");
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //设置header
            //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //httpPost.setHeader("Accept", "application/json;charset=utf8");
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            reMap.put("length",String.valueOf(responseEntity.getContentLength()));
            reMap.put("response", EntityUtils.toString(responseEntity));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    /**
     * HTTP Post    无认证的
     * @param uri   访问地址
     * @param params 参数
     * @return
     */
    static Map<String, Object> doPost(String uri,Map<String, String> params){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, Object> reMap = new HashMap<>();
        try{

            //遍历map 将其中的数据转化为表单数据
            if (null != params && !params.isEmpty()) {
                ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();//用于存放表单数据.
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"UTF-8");
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //设置header
            //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //httpPost.setHeader("Accept", "application/json;charset=utf8");
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            reMap.put("header", response.getAllHeaders());
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            reMap.put("length",String.valueOf(responseEntity.getContentLength()));
            reMap.put("response", EntityUtils.toString(responseEntity));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    /**
     * HTTP Post    有认证的
     * @param url   地址
     * @param user  用户名
     * @param pwd   密码
     * @param params 参数
     * @return
     */
    static HashMap<String, String> doPostHttp(String url, String user, String pwd, Map<String, String> params){
        //返回对象
        HashMap<String, String> reMap = new HashMap<>();
        // 响应模型
        CloseableHttpResponse response = null;
        //创建URI
        URI uri = URI.create(url);
        //创建host
        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        //创建认证信息
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(uri.getHost(), uri.getPort()),
                new UsernamePasswordCredentials(user, pwd));
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
        HttpPost httpPost = new HttpPost(uri);

        try
        {
            //遍历map 将其中的数据转化为表单数据
            if (null != params && !params.isEmpty()) {
                ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();//用于存放表单数据.
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            //设置header
            //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //httpPost.setHeader("Accept", "application/json;charset=utf8");
            // Add AuthCache to the execution context
            HttpClientContext localContext = HttpClientContext.create();
            localContext.setAuthCache(authCache);
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(host, httpPost, localContext);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            reMap.put("length",String.valueOf(responseEntity.getContentLength()));
            reMap.put("response", EntityUtils.toString(responseEntity));
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    static Map<String, Object> doPost(String uri,Map<String,String> headers,Map<String, String> params){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, Object> reMap = new HashMap<>();
        try{

            //遍历map 将其中的数据转化为表单数据
            if (null != params && !params.isEmpty()) {
                ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();//用于存放表单数据.
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"UTF-8");
                httpPost.setEntity(urlEncodedFormEntity);
            }
            if(!headers.isEmpty()){
                for (String key:headers.keySet()){
                    httpPost.setHeader(key, headers.get(key));
                }
            }
            //设置header
            //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //httpPost.setHeader("Accept", "application/json;charset=utf8");
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体

            httpPost.abort();//释放post请求

            //处理http返回码302的情况
            if ( 302 == response.getStatusLine().getStatusCode()) {
                String locationUrl=response.getLastHeader("Location").getValue();
                Header[] headers2= response.getHeaders("Set-Cookie");
                String cookie = "";
                for (int j = 0; j < headers2.length; j++) {
                    cookie+=headers2[j].getValue()+";";
                }
                Map<String,String> head = new HashMap<>();
                head.put("cookie",cookie);
                reMap = doGetHttp(locationUrl,head);
                reMap.put("cookie",cookie);
            }else{
                HttpEntity responseEntity = response.getEntity();
                reMap.put("header", response.getAllHeaders());
                reMap.put("status",response.getStatusLine().toString());
                reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
                reMap.put("length",String.valueOf(responseEntity.getContentLength()));
                reMap.put("response", EntityUtils.toString(responseEntity));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    /**
     * HTTP Put    无认证的
     * @param uri   访问地址
     * @param params 参数
     * @return
     */
    static  HashMap<String, String> doPut(String uri,Map<String, String> params){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建HttpPut请求
        HttpPut httpPut = new HttpPut(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, String> reMap = new HashMap<>();
        try{

            //遍历map 将其中的数据转化为表单数据
            if (null != params && !params.isEmpty()) {
                ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();//用于存放表单数据.
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(pairs,"UTF-8");
                httpPut.setEntity(urlEncodedFormEntity);
            }
            //设置header
            //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //httpPost.setHeader("Accept", "application/json;charset=utf8");
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPut);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            reMap.put("length",String.valueOf(responseEntity.getContentLength()));
            reMap.put("response", EntityUtils.toString(responseEntity));

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }


    static Map<String, Object> doPost2(String uri,Map<String,String> headers,Map<String, Object> params){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, Object> reMap = new HashMap<>();
        try{

            //遍历map 将其中的数据转化为表单数据
            if (null != params && !params.isEmpty()) {
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();

                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String name = entry.getKey();
                    Object value = entry.getValue();
                    if (value.getClass().isArray()) {
                        String[] arr = (String[]) value;
                        for (String s : arr) {
                            builder.addPart(name, new StringBody(s, ContentType.TEXT_PLAIN));
                        }
                    } else {
                        builder.addPart(name, new StringBody(value.toString(), ContentType.TEXT_PLAIN));
                    }
                }
                httpPost.setEntity(builder.build());
            }
            if(!headers.isEmpty()){
                for (String key:headers.keySet()){
                    httpPost.setHeader(key, headers.get(key));
                }
            }
            //设置header
            //httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            //httpPost.setHeader("Accept", "application/json;charset=utf8");
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体

            httpPost.abort();//释放post请求

            //处理http返回码302的情况
            if ( 302 == response.getStatusLine().getStatusCode()) {
                String locationUrl=response.getLastHeader("Location").getValue();
                Header[] headers2= response.getHeaders("Set-Cookie");
                String cookie = "";
                for (int j = 0; j < headers2.length; j++) {
                    cookie+=headers2[j].getValue()+";";
                }
                Map<String,String> head = new HashMap<>();
                head.put("cookie",cookie);
                reMap = doGetHttp(locationUrl,head);
                reMap.put("cookie",cookie);
            }else{
                HttpEntity responseEntity = response.getEntity();
                reMap.put("header", response.getAllHeaders());
                reMap.put("status",response.getStatusLine().toString());
                reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
                reMap.put("length",String.valueOf(responseEntity.getContentLength()));
                reMap.put("response", EntityUtils.toString(responseEntity));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    static Map<String, Object> doPost3(String uri,Map<String,String> headers,String json){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建Post请求
        HttpPost httpPost = new HttpPost(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, Object> reMap = new HashMap<>();
        try{

            //遍历map 将其中的数据转化为表单数据
            httpPost.setEntity(new StringEntity(json));
            if(!headers.isEmpty()){
                for (String key:headers.keySet()){
                    httpPost.setHeader(key, headers.get(key));
                }
            }
            //设置header
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("Accept", "application/json;charset=utf8");
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体

            httpPost.abort();//释放post请求

            //处理http返回码302的情况
            if ( 302 == response.getStatusLine().getStatusCode()) {
                String locationUrl=response.getLastHeader("Location").getValue();
                Header[] headers2= response.getHeaders("Set-Cookie");
                String cookie = "";
                for (int j = 0; j < headers2.length; j++) {
                    cookie+=headers2[j].getValue()+";";
                }
                Map<String,String> head = new HashMap<>();
                head.put("cookie",cookie);
                reMap = doGetHttp(locationUrl,head);
                reMap.put("cookie",cookie);
            }else{
                HttpEntity responseEntity = response.getEntity();
                reMap.put("header", response.getAllHeaders());
                reMap.put("status",response.getStatusLine().toString());
                reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
                reMap.put("length",String.valueOf(responseEntity.getContentLength()));
                reMap.put("response", EntityUtils.toString(responseEntity));
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    /**
     * HTTP Put    无认证的
     * @param uri   访问地址
     * @param headers 参数
     * @return
     */
    static  HashMap<String, String> doDelete(String uri, Map<String,String> headers){
        // 获得Http客户端
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .build();
        // 创建HttpPut请求
        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(uri);
        // 响应模型
        CloseableHttpResponse response = null;
        //返回对象
        HashMap<String, String> reMap = new HashMap<>();
        try{
            //设置header
            if(!headers.isEmpty()){
                for (String key:headers.keySet()){
                    httpDelete.setHeader(key, headers.get(key));
                }
            }
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpDelete);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            reMap.put("status",response.getStatusLine().toString());
            reMap.put("code",String.valueOf(response.getStatusLine().getStatusCode()));
            if (responseEntity != null) {
                reMap.put("length", String.valueOf(responseEntity.getContentLength()));
                reMap.put("response", EntityUtils.toString(responseEntity));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }
}
