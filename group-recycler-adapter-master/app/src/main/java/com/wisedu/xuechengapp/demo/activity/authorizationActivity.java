package com.wisedu.xuechengapp.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedu.xuechengapp.demo.R;


public class authorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        Bundle bundle=getIntent().getExtras();

        String studentName = bundle.getString("content");
        TextView TstudentName = findViewById(R.id.name);
        TstudentName.setText(studentName);

        String reqAgencyName = bundle.getString("reqAgencyName");
        TextView TagencyName = findViewById(R.id.reqAgency);
        TagencyName.setText(reqAgencyName);

        String reqFileName = bundle.getString("reqFileName");
        TextView TreqFileName = findViewById(R.id.filename);
        TreqFileName.setText(reqFileName);

        Button approveButton = findViewById(R.id.approve);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast approve  = Toast.makeText(authorizationActivity.this,"同意授权",Toast.LENGTH_LONG);
                    approve.show();


                    //开始签名授权信息
                //授权机构名称、查看档案名称

                //返回到档案页面
                Intent intent = new Intent(authorizationActivity.this,MainActivity.class);
                startActivity(intent);


            }
        });

        Button rejectButton = findViewById(R.id.reject);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast approve  = Toast.makeText(authorizationActivity.this,"已拒绝",Toast.LENGTH_LONG);
                approve.show();


                //拒绝

                //返回到档案页面
                Intent intent = new Intent(authorizationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
