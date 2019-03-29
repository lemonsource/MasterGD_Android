package com.wisedu.xuechengapp.demo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.wisedu.xuechengapp.demo.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpActivity extends BaseCompatActivity {
    private TextView mMsgTxt;
    private Button mSendBtn;
    String path_1 = "http://172.20.6.207:8010/studentindex?username=mx&password=123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        mMsgTxt = (TextView) findViewById(R.id.txt_show_message);
        mSendBtn = (Button) findViewById(R.id.btn_send);

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("onClick----准备发送请求！");
                post();
            }
        });

    }

    public void post() {
        AsynTask asyntask = new AsynTask(this);
        asyntask.execute(path_1);
    }

    class AsynTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pdialog;

        public AsynTask(Context context) {
            pdialog = new ProgressDialog(context, 0);
            pdialog.setCancelable(true);
            pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdialog.setMessage("取消");
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpGet get = new HttpGet(params[0]);
            System.out.println(get);

            HttpClient cilent = new DefaultHttpClient();
            try {
                HttpResponse responses = cilent.execute(get);
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
        protected void onCancelled() {

            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            mMsgTxt.setText(result);
            System.out.println("客户端取到的信息" + result);
            pdialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            mMsgTxt.setText("task start.......");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            System.out.println("" + values[0]);
            mMsgTxt.setText("" + values[0]);
            pdialog.setProgress(values[0]);
        }

    }

}