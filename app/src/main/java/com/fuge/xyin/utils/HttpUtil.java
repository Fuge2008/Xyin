package com.fuge.xyin.utils;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;


import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by wang kai tian on 17/6/7.
 */
public class HttpUtil {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private  String url;
    private String method;
    private String params;
    private Map<String, String> stringMap;
    private JSONObject result=new JSONObject();

    public  String post(String url,String json) throws IOException {
        OkHttpClient  client= new OkHttpClient.Builder()
                .build();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            result.put("code","500");
            result.put("msg",response.message());
        }
        return result.toJSONString();
    }

    public String httpPostForm(String url, Map<String, String> forms) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : forms.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException("api gateway  HttpUtil httpPostForm error！"+response.message());
        }
    }


    public  String get(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return  response.body().string();
        }else{
        }
        String result=  response.body().string();
        Log.e("打印数据返回:",result);
        response.close();//如果这里不关闭 会导致执行两次
        return result;
    }



    public synchronized String execute() throws IOException{
        if("POST".equals(method)){
            return post(url, params);
        }else if("GET".equals(method)){
            return get(url);
        }else if("POSTFORM".equals(method)){
            return httpPostForm(url, stringMap);

        }else{
            return null;
        }
    }


    public Map<String, String> getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


}