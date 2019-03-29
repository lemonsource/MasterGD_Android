package com.wisedu.xuechengapp.demo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;
import com.wisedu.xuechengapp.demo.R;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;


public class ForgetpassActivity extends AppCompatActivity {
    private Button getcode, returnlogin;
    private Button updatebnt;
    private EditText phonenumber, code, password, repassword;
    private String phone;
    String path = "http://172.20.6.207:8010/studentupdatepassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        getcode = (Button) findViewById(R.id.getmessage);
        updatebnt = (Button) findViewById(R.id.updatebnt);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        code = (EditText) findViewById(R.id.message);
        password=findViewById(R.id.password);
        repassword=findViewById(R.id.repassword);
        returnlogin=findViewById(R.id.return_login);


        MobSDK.init(this);

        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);


        returnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetpassActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.getVerificationCode("86", phonenumber.getText().toString());
            }
        });

        updatebnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode("86", phonenumber.getText().toString(), code.getText().toString());
            }
        });
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result" + event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    String pass=password.getText().toString();
                    String repass=repassword.getText().toString();
                    if (!pass.equals(repass)) {
                        password.setFocusable(true);
                        password.setFocusableInTouchMode(true);
                        password.requestFocus();
                        CharSequence html1 = Html.fromHtml("<font color='#FFFFFF'>请输入密码</font>");
                        password.setError(html1);
                    }else {
                        submit();
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//                    textV.setText(data.toString());
                }

            } else {
//				((Throwable) data).printStackTrace();
//				Toast.makeText(MainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//					Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(ForgetpassActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };


    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    void submit(){
        new AsynTask(this).execute(path,phonenumber.getText().toString(),password.getText().toString());
    }

    class AsynTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pdialog;

        public AsynTask(Context context) {
            pdialog = new ProgressDialog(context, 0);
            pdialog.setCancelable(true);
            pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdialog.setMessage("正在注册");
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
            if (result.equals("success")) {
                showSuccessDialog();
            }
            if (result.equals("error")) {
                showNormalDialog();
            }
            pdialog.dismiss();
        }


    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ForgetpassActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("注册失败，请重试！");
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do

                    }
                });
        // 显示
        normalDialog.show();
    }

    private void showSuccessDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ForgetpassActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("注册成功，去登录！");
        normalDialog.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        Intent intent = new Intent(ForgetpassActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
        // 显示
        normalDialog.show();
    }


}