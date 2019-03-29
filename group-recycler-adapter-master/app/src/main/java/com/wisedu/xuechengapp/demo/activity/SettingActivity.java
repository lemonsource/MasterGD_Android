package com.wisedu.xuechengapp.demo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mob.tools.network.StringPart;
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


public class SettingActivity extends BaseCompatActivity implements AdapterView.OnItemClickListener{
    private String[] data = {"手机", "修改密码", "已实名认证","我的授权","关于",};
    private TextView mTitleTextView;
    private Button mSettingButton;
    private Button mBackwardButton;
    private Button logoutButton;

    String path="http://172.20.6.207:8010/InstitutionIdpushing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mSettingButton = (Button) findViewById(R.id.button_setting);
        mBackwardButton=findViewById(R.id.button_backward);
        logoutButton=findViewById(R.id.logout);
        mTitleTextView.setText("设置");
        mSettingButton.setVisibility(View.INVISIBLE);
        mBackwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        System.out.print(pref.getString("mobile","")+","+pref.getString("username","")+","+pref.getString("identity",""));
        if (pref.getString("identity","").isEmpty())
            data[2]="点击进行实名认证";

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent("com.gesoft.admin.loginout");
               sendBroadcast(intent);

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                SettingActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(position == 0){
            Intent intent = new Intent(SettingActivity.this, UpdatePhoneActivity.class);
            startActivity(intent);
        }else if(position == 1){
            Intent intent = new Intent(SettingActivity.this, UpdatePhoneActivity.class);
            startActivity(intent);
        }else if(position == 2){
            if (data[2].equals("点击进行实名认证")){
                Intent intent = new Intent(SettingActivity.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        }else if(position == 3){
            AsynTask asyntask = new AsynTask(this);
            asyntask.execute(path);
        }else if(position == 4){
            Intent intent = new Intent(SettingActivity.this, UpdatePhoneActivity.class);
            startActivity(intent);
        }
    }

    class AsynTask extends AsyncTask<String, Integer, String> {
        ProgressDialog pdialog;

        public AsynTask(Context context) {
            pdialog = new ProgressDialog(context, 0);
            pdialog.setCancelable(true);
            pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdialog.setMessage("正在查询");
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
        protected void onPostExecute(String result) {
            System.out.print("客户端取到的信息" + result);
            pdialog.dismiss();
            String s[]=result.split(",");
            Bundle bundle = new Bundle();
            bundle.putString("institution",s[0]);
            bundle.putString("certname",s[1]);
            Intent intent = new Intent(SettingActivity.this, reqAuthorActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


}
