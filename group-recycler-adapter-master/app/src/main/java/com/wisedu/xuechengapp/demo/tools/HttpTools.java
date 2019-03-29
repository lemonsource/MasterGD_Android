package com.wisedu.xuechengapp.demo.tools;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 *以同步方式发送Http请求
 */
public class HttpTools
{

    /**
     * 通过GET方式发送请求
     * @param url URL地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public String getResultForHttpGet(String url, String params) throws Exception
    {
        String response = null; //返回信息
        //拼接请求URL
        if (null!=params&&!params.equals(""))
        {
            url += "?" + params;
        }

        int timeoutConnection = 10000000;
        int timeoutSocket = 5000000;
        //设置网络链接超时
        HttpParams httpParameters = new BasicHttpParams();
        //设置socket响应超时
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        // 构造HttpClient的实例
        HttpClient httpClient = new DefaultHttpClient(httpParameters);

        System.out.println("URL = "+url);
        // 生成一个请求对象
        HttpGet httpGet = new HttpGet(url);

        try
        {
            // 使用Http客户端发送请求对象
            HttpResponse httpResponse = httpClient.execute(httpGet);

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) //SC_OK = 200
            {
                // 获得返回结果
                response = EntityUtils.toString(httpResponse.getEntity());
            }
            else
            {
                response = "返回码：" + statusCode;
            }
        } catch (Exception e)
        {
            throw new Exception(e);
        }

        return response;
    }

    /**
     * 通过POST方式发送请求
     * @param url URL地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public String getReultForHttpPost(String url, List<NameValuePair> params) throws Exception
    {
        String response = null;
        int timeoutConnection = 300000;
        int timeoutSocket = 500000;
        // 设置网络链接超时
        HttpParams httpParameters = new BasicHttpParams();
        // 设置socket响应超时
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        // 构造HttpClient的实例
        HttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpPost httpPost = new HttpPost(url);
        if (params.size()>=0)
        {
            //设置httpPost请求参数
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        }
        //使用execute方法发送HTTP Post请求，并返回HttpResponse对象
        HttpResponse httpResponse = httpClient.execute(httpPost);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(statusCode == HttpStatus.SC_OK)
        {
            //获得返回结果
            System.out.println("Post请求成功");
            response = EntityUtils.toString(httpResponse.getEntity());
        }
        else
        {
            response = "返回码："+statusCode;
        }
        return response;
    }

}
