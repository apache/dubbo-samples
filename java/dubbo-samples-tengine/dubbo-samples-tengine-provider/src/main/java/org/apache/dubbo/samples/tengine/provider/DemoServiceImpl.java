/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.samples.tengine.provider;

import org.apache.dubbo.samples.tengine.DemoService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoServiceImpl implements DemoService {
    @Override
    public Map<String, Object> tengineDubbo(Map<String, Object> context) {
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("body", "dubbo success\n");
        ret.put("status", "200");
        ret.put("test", "123");

        return ret;
    }

    @Override
    //a sample for dubbo to http test
    public Map<String, Object>  dubbo2Http(Map<String, Object> context) {
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        String destUrl = "http://127.0.0.1:9280";

        Map<String, Object> ret = null;
        String testValue = (String)context.get("test");
        if (testValue != null) {
            System.out.println("test value: " + testValue);
            ret = new HashMap<String, Object>();
            if (testValue.equals("null")) {
                System.out.println("dubbo test: null");
                return null;
            } else if (testValue.equals("body empty")) {
                System.out.println("dubbo test: body empty");
                ret.put("status", "302");
            } else if (testValue.equals("status empty")) {
                System.out.println("dubbo test: status empty");
                ret.put("body", "dubbo failed");
            } else {
                System.out.println("dubbo test: unkown test");
            }

            return ret;
        }

        //prepare params
        String method = null;
        String url = null;
        byte[] body = null;
        List<NameValuePair> params = null;
        Map<String, String> headers = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("get key = " + key + ", value = " + value);
            if (key.equals("args")) {
                params = URLEncodedUtils.parse((String)value, Charset.forName("UTF-8"));
            } else if (key.equals("method")) {
                method = (String)value;
            } else if (key.equals("body")) {
                body = (byte[])value;
            } else if (key.equals("uri")) {
                url = destUrl + value;
            } else {
                headers.put(key, (String)value);
            }
        }

        //do http request
        ret = sendRequest(url, method, params, headers, body);

        return ret;
    }

    Map<String, Object> sendRequest(String url, String method, List<NameValuePair> params, Map<String, String> headers, byte[] body) {
        Map<String, Object> result = new HashMap<String, Object>();

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            client = HttpClients.createDefault();

            URIBuilder uriBuilder = new URIBuilder(url);

            uriBuilder.addParameters(params);

            System.out.println("get to: " + uriBuilder.build().toString());

            HttpRequestBase request = null;

            if (method.equals("GET")) {
                request = new HttpGet(uriBuilder.build());
            } else if (method.equals("POST")) {
                request = new HttpPost(uriBuilder.build());
                if (body != null) {
                    HttpEntity entity = new ByteArrayEntity(body);
                    ((HttpPost)request).setEntity(entity);
                }
            } else {
                result.put("status", "500");
                return result;
            }

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                System.out.println("header key = " + entry.getKey() + ", value = " + entry.getValue());
                if (entry.getKey().equalsIgnoreCase("content-length")) {
                    System.out.println("skip content-length");
                    continue;
                }
                request.setHeader(new BasicHeader(entry.getKey(), entry.getValue()));
            }

            RequestConfig.Builder reqConf = RequestConfig.custom();
            reqConf.setRedirectsEnabled(false);
            request.setConfig(reqConf.build());

            response = client.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            result.put("status", String.valueOf(statusCode));

            HttpEntity entity = response.getEntity();

            byte[] responseBody = EntityUtils.toByteArray(entity);
            result.put("body", responseBody);

            System.out.println("body byte");

            Header[] responseHeaders = response.getAllHeaders();
            for (Header h : responseHeaders) {
                System.out.println("header key = " + h.getName() + ", value = " + h.getValue());
                result.put(h.getName(), h.getValue());
            }
        }catch (Exception e){
            System.out.println("error: " + e.getCause().getMessage());
            e.printStackTrace();
            result.put("status", "502");
            result.put("body", e.getCause().getMessage().getBytes());
        }

        return result;
    }

    @Override
    public Map<String, Object> tengineTest(Map<String, Object> context) {
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        Map<String, Object> ret = null;
        String testValue = (String)context.get("test");
        if (testValue != null) {
            System.out.println("test value: " + testValue);
            ret = new HashMap<String, Object>();
            if (testValue.equals("null")) {
                System.out.println("dubbo test: null");
                return null;
            } else if (testValue.equals("body empty")) {
                System.out.println("dubbo test: body empty");
                ret.put("status", "302");
            } else if (testValue.equals("status empty")) {
                System.out.println("dubbo test: status empty");
                ret.put("body", "dubbo failed");
            } else {
                System.out.println("dubbo test: unkown test");
            }

            return ret;
        }

        return null;
    }
}
