package com.wisedu.xuechengapp.demo.tools;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetCertTask extends AsyncTask<String,Void,String> {
private String path = "http://172.20.6.207:8010/studentindex";
//    private String url;
//    private Context context;
//    //传入参数
//    private String params;
//    private TextView textView;
//
//    public xuechengAsyncTask(String url, Context context, String params, TextView textView) {
//        this.url = url;
//        this.context = context;
//        this.params = params;
//        this.textView = textView;
//    }


@Override
protected String doInBackground(String... params) {

        String result = null;
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();


        //请求属性名
        builder.add("id", "555");

        RequestBody body = builder.build();
        Request request = new Request.Builder().url(path).post(body).build();
        Response response = null;
        try {
        response = client.newCall(request).execute();
        result = response.body().string();
        } catch (IOException e) {
        e.printStackTrace();
        }

        return result;
        }


@Override
protected void onPostExecute(String s) {
        Log.e("result",""+s);
        try{
        JSONObject jsonObject = new JSONObject(s);
        Log.e("key",""+jsonObject.get("key"));
        }catch (JSONException e){
        e.printStackTrace();
        }
        super.onPostExecute(s);
        }
}
