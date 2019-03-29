package com.wisedu.xuechengapp.demo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wisedu.xuechengapp.demo.R;


public class Main2Activity extends BaseCompatActivity {
    private TextView mTitleTextView;
    private Button mbackwardButton;
    private Button mSettingButton;

    private Button authen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mbackwardButton = (Button) findViewById(R.id.button_backward);
        mSettingButton = (Button)findViewById(R.id.button_setting);
        mTitleTextView.setText("我的档案");
        mbackwardButton.setVisibility(View.INVISIBLE);

        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, SettingActivity.class);
                startActivity(intent);
            }
        });


        authen=findViewById(R.id.authen);
        authen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        });

    }

}
