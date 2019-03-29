package com.wisedu.xuechengapp.demo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedu.xuechengapp.demo.R;

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

public class reqAuthorActivity extends AppCompatActivity {

    String path="http://172.20.6.207:8010/authorize?DID=+PdSGNWibxXVQHURFozPMEAYi2Gxo2J7Z4PAagxT8FE=";

    TextView TstudentName;
    TextView TagencyName;
    TextView TreqFileName;
    Button approveButton;
    Button rejectButton;

    private TextView mTitleTextView;
    private Button mbackwardButton;
    private Button mSettingButton;


    String studentName="刘练";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_author);

        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mbackwardButton = (Button) findViewById(R.id.button_backward);
        mSettingButton = (Button)findViewById(R.id.button_setting);
        mTitleTextView.setText("授权申请");
        mSettingButton.setVisibility(View.INVISIBLE);

        TstudentName = findViewById(R.id.name);
        TstudentName.setText(studentName);
        TextPaint p=TstudentName.getPaint();
        p.setFakeBoldText(true);


        TagencyName = findViewById(R.id.reqAgency);
        TextPaint i=TagencyName.getPaint();
        i.setFakeBoldText(true);
        Bundle bundle = getIntent().getExtras();
        String institution = bundle.getString("institution");
        TagencyName.setText(institution);

        TreqFileName = findViewById(R.id.filename);
        String certname = bundle.getString("certname");
        TreqFileName.setText(certname);

        approveButton = findViewById(R.id.appr);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsynTask asyntask = new AsynTask(v.getContext());
                asyntask.execute(path);
            }
        });


        rejectButton = findViewById(R.id.reject);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast approve = Toast.makeText(reqAuthorActivity.this, "已拒绝", Toast.LENGTH_LONG);
                approve.show();
                //拒绝
                //返回到档案页面
                Intent intent = new Intent(reqAuthorActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void post() {

    }

    class AsynTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pdialog;

        public AsynTask(Context context) {
            pdialog = new ProgressDialog(context, 0);
            pdialog.setCancelable(true);
            pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdialog.setMessage("正在处理");
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpGet get = new HttpGet(params[0]);
            System.out.println(get.toString());

            HttpClient cilent = new DefaultHttpClient();
            try {
                HttpResponse responses = cilent.execute(get);
                System.out.println("返回值"+responses.getEntity().toString());
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

            Toast approve = Toast.makeText(reqAuthorActivity.this, "授权成功！", Toast.LENGTH_LONG);
            approve.show();

            Intent intent = new Intent(reqAuthorActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
}
