package com.wisedu.xuechengapp.demo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.Gson;
import com.wisedu.xuechengapp.demo.R;
import com.wisedu.xuechengapp.demo.entity.Member;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;


public class LoginActivity extends BaseCompatActivity {


    private EditText mobile = null ;
    private EditText password = null ;


    private Button login = null ;
    private Button register = null ;
    private Button forget = null ;
    //private CheckBox cb;
    private SharedPreferences sharedPreferences ;

    String path = "http://172.20.6.207:8010/studentindex";
    String id="341181199707072345";
    String url = "http://172.20.6.207:8010/showRecordsByIdentityID";
//    String url = "http://172.20.6.207:8010/studentindex?mobile=18851656827&password=123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mobile=findViewById(R.id.mobile);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forget=findViewById(R.id.login_error);
        register=findViewById(R.id.register);
        //cb = (CheckBox)findViewById(R.id.cb);
        sharedPreferences = getSharedPreferences("login",MODE_PRIVATE);

        /*String name = sharedPreferences.getString("name", "");
        String pass = sharedPreferences.getString("pass", "");
        if (!name.isEmpty() && !pass.isEmpty())
        {
            username.setText(name);
            username.setText(pass);
        }*/
        //给login绑定监听事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iflogin();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });

        //给register绑定监听事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetpassActivity.class);
                startActivity(intent);
            }
        });

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());
    }

    public void iflogin() {
        String phone = mobile.getText().toString();
        String pass = password.getText().toString();
        Log.i("MainActivity", phone.isEmpty() + "");
        if (phone.isEmpty() ) {
            mobile.setFocusable(true);
            mobile.setFocusableInTouchMode(true);
            mobile.requestFocus();
            CharSequence html1 = Html.fromHtml("<font color='#FFFFFF'>请输入用户名</font>");
            mobile.setError(html1);
        }

        if (pass.isEmpty()) {
            password.setFocusable(true);
            password.setFocusableInTouchMode(true);
            password.requestFocus();
            CharSequence html1 = Html.fromHtml("<font color='#FFFFFF'>请输入密码</font>");
            password.setError(html1);
        }

        System.out.println(path);
        new AsynTask(this).execute(path,phone,pass);
        /*Intent intent = new Intent(LoginActivity.this, ShareActivity.class);
        startActivity(intent);*/

    }

    class AsynTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pdialog;

        public AsynTask(Context context) {
            pdialog = new ProgressDialog(context, 0);
            pdialog.setCancelable(true);
            pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdialog.setMessage("正在登录");
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpPost httpPost = new HttpPost(params[0]);
            NameValuePair pair1 = new BasicNameValuePair("mobile", params[1]);
            NameValuePair pair2 = new BasicNameValuePair("password", params[2]);

            ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(pair1);
            pairs.add(pair2);
            try {
                //创建代表请求体的对象（注意，是请求体）
                HttpEntity requestEntity = new UrlEncodedFormEntity(pairs);
                //将请求体放置在请求对象当中
                httpPost.setEntity(requestEntity);
                HttpClient cilent = new DefaultHttpClient();
                HttpResponse responses = cilent.execute(httpPost);
                if (responses.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result_1 = EntityUtils.toString(responses.getEntity());
                    System.out.println("客户端取到的信息" + result_1);
                    return result_1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "不对";
        }


        @Override
        protected void onPostExecute(String result) {
            pdialog.cancel();
            if (result.equals("success")) {

                SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
                editor.putString("mobile",mobile.getText().toString()); //以键值对形式存储
                editor.putString("username",""); //以键值对形式存储
                editor.putString("identity",""); //以键值对形式存储
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent);
            }else if(result.contains("yes")){

                String s[]=result.split(",");
                new AsynTask2(getWindow().getDecorView().getContext()).execute(s[1],s[2]);

               /* HttpGet get2 = new HttpGet(url);
                HttpClient cilent2 = new DefaultHttpClient();
                try {
                    HttpResponse responses2 = cilent2.execute(get2);
                    if (responses2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        String result2 = EntityUtils.toString(responses2.getEntity());
                        System.out.println("客户端取到的信息" + result2);
                        Member cert = JsonToObjecT(result2);
                        cert.setDownloadStatus(0);


                        SharedPreferences.Editor editor = getSharedPreferences("login",MODE_PRIVATE).edit();
                        editor.putString("mobile",mobile.getText().toString()); //以键值对形式存储
                        editor.putString("username",s[1]); //以键值对形式存储
                        editor.putString("identity",s[2]); //以键值对形式存储
                        editor.apply();


                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("cert",cert);
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }else{
                password.setFocusable(true);
                password.setFocusableInTouchMode(true);
                password.requestFocus();
                CharSequence html1 = Html.fromHtml("<font color='#FFFFFF'>用户名或密码错误</font>");
                password.setError(html1);
            }
            pdialog.dismiss();
        }
    }

    public Member JsonToObjecT(String jsonData){
        Gson gson = new Gson();
        Member member = gson.fromJson(jsonData,Member.class);
        return member;
    }


    class AsynTask2 extends AsyncTask<String, Integer, String> {
        ProgressDialog pdialog;

        public AsynTask2(Context context) {

        }

        @Override
        protected String doInBackground(String... params) {
            HttpGet get2 = new HttpGet(url+"?IdentityID="+params[1]);
            HttpClient cilent2 = new DefaultHttpClient();
            try {
                HttpResponse responses2 = cilent2.execute(get2);
                if (responses2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result2 = EntityUtils.toString(responses2.getEntity());
                    System.out.println("客户端取到的信息" + result2);
                    Member cert = JsonToObjecT(result2);
                    cert.setDownloadStatus(0);


                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("mobile", mobile.getText().toString()); //以键值对形式存储
                    editor.putString("username", params[0]); //以键值对形式存储
                    editor.putString("identity", params[1]); //以键值对形式存储
                    editor.apply();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("cert", cert);
                    startActivity(intent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "不对";
        }


        @Override
        protected void onPostExecute(String result) {

        }

    }

}


