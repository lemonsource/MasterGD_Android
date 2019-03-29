package com.wisedu.xuechengapp.demo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
//import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.wisedu.groundrecycleradapter.GroupItemDecoration;
import com.wisedu.groundrecycleradapter.GroupRecyclerAdapter;
import com.wisedu.groundrecycleradapter.OnChildClickListener;
import com.wisedu.groundrecycleradapter.OnGroupClickListener;
import com.wisedu.xuechengapp.demo.R;
import com.wisedu.xuechengapp.demo.entity.Member;
import com.wisedu.xuechengapp.demo.entity.Team;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

////    private static final int REFRESH_COMOLETE = 0x01;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView mTitleTextView;
    private Button mbackwardButton;
    private Button mSettingButton;

//    private SwipeRefreshView swipeRefreshView;

//    private RecyclerView recyclerView = findViewById(R.id.recycler_view);

    private void jumpOtherActivity(String content){
        Intent intent = new Intent(MainActivity.this,authorizationActivity.class);
        intent.putExtra("content",content);
        startActivity(intent);
    }

    /******************************************数据来源************************************************************/

    String [] school2 = {"南京大学","东南大学"};

    List<Team> school = new ArrayList<>();

    //添加teams（学校）并包含相应的members（证书）
    final List<Team> teams = new ArrayList<>();

    List<Member> members = new ArrayList<>();
//            String teamName = Character.toString((char) ((int) ('A') + i)) + "学校";

    List<Member> members2 = new ArrayList<>();
//            String teamName = Character.toString((char) ((int) ('A') + i)) + "学校";

    /******************************************数据来源************************************************************/


//        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//    //使用设置好的recyclerAdapter
//        recyclerView.setAdapter(recyclerAdapter);



    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected  void onResume(){
        super.onResume();

        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if(clickedResult!=null){
            String content=clickedResult.getCustomContent();
            System.out.println("hahahahhaahahhahahahhaha");
            System.out.println(clickedResult);
            jumpOtherActivity(content);
            if(isTaskRoot()){
                return;
            }
            finish();
        }

    }
    @Override
    protected void onPause(){
        super.onPause();
        XGPushManager.onActivityStoped(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mbackwardButton = (Button) findViewById(R.id.button_backward);
        mSettingButton = (Button)findViewById(R.id.button_setting);
        mTitleTextView.setText("我的档案");
        mbackwardButton.setVisibility(View.INVISIBLE);
        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        XGPushConfig.enableDebug(this,true);

        XGPushConfig.enableOtherPush(getApplicationContext(),true);

        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

//        XGPushManager.bindAccount(getApplicationContext(), "XINGE");

//        Uri uri = getIntent().getData();
//        if(uri!=null){
//            if(!uri.getQueryParameter("ST_CODE").equals("")){
//                Integer selectId = Integer.parseInt(uri.getQueryParameter("NO_TYPE"));
//                String content = uri.getQueryParameter("ST_CODE");
//
//                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//                    if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
//
//                    }else{
//                        jumpOtherActivity();
//                    }
//                }else {
//                    jumpOtherActivity();
//                }
//            }
//        }

//        members.add(new Member("全日制本科毕业证书","2018年11月26日","刘练","201010342","计算机软件","2014年","理学","全日制本科","南京大学学术委员会","2014年6月29日"));
//
        members.add(new Member("南京大学全日制本科学位证书","2018年11月26日","刘练","201010342","计算机软件","2014年","理学","全日制本科","南京大学学术委员会","2014年6月29日"));
//
//        teams.add(new Team("南京大学", members));
//
        members2.add(new Member("东南大学全日制硕士毕业证书","2018年11月26日","刘练","201410342","计算机软件","2014年","工学","全日制硕士","东南大学学术委员会","2017年6月29日"));
//
        members2.add(new Member("东南大学全日制硕士学位证书","2018年11月26日","刘练","201410342","计算机软件","2014年","工学","全日志硕士","东南大学学术委员会","2017年6月29日"));
//
//        teams.add(new Team("东南大学", members2));



        /*************************************数据来源*****************************************************************/

        Intent intent = getIntent();
        Member cert = (Member) intent.getSerializableExtra("cert");
        members.add(cert);
        teams.add(new Team("南京大学",members));
        teams.add(new Team("东南大学",members2));
        /*************************************数据来源*****************************************************************/

        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         final GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder> recyclerAdapter =
                new GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder>(teams) {
                    @Override
                    protected TeamViewHolder onCreateGroupViewHolder(ViewGroup parent) {
                        return new TeamViewHolder(layoutInflater.inflate(R.layout.item_team_title, parent, false));
                    }

                    @Override
                    protected MemberViewHolder onCreateChildViewHolder(ViewGroup parent) {


                        return new MemberViewHolder(layoutInflater.inflate(R.layout.item_team_member, parent, false));
                    }

                    @Override
                    protected void onBindGroupViewHolder(TeamViewHolder holder, int groupPosition) {
                        holder.update(getGroup(groupPosition));
                    }

                    @Override
                    protected void onBindChildViewHolder(MemberViewHolder holder, int groupPosition, int childPosition) {
                        holder.update(getGroup(groupPosition).members.get(childPosition));
                    }

                    @Override
                    protected int getChildCount(Team group) {
                        return group.members.size();
                    }
                };


        //使用设置好的recyclerAdapter
        recyclerView.setAdapter(recyclerAdapter);



//         final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if(msg.what == 10){
//
//                    recyclerAdapter.notifyDataSetChanged();
//                    System.out.println("222222222222222222222222");
//                }
//            }
//        };


        class GameThread implements Runnable {
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

// 使用postInvalidate可以直接在线程中更新界面
                    recyclerView.postInvalidate();
                }
            }
        }

        new Thread(new GameThread()).start();

        GroupItemDecoration decoration = new GroupItemDecoration(recyclerAdapter);
        decoration.setGroupDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_16_dp, null));
        decoration.setTitleDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_1_px, null));
        decoration.setChildDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_white_header, null));
        recyclerView.addItemDecoration(decoration);

        recyclerAdapter.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public void onGroupItemClick(View itemView, int groupPosition) {
                showToast(recyclerAdapter.getGroup(groupPosition).title);
            }
        });
        recyclerAdapter.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public void onChildClick(View itemView, int groupPosition, int childPosition) {
                Team team = recyclerAdapter.getGroup(groupPosition);
//                showToast(team.title + ": " +team.members.get(childPosition).name);



                team.members.get(childPosition).downloadStatus=1;
                recyclerAdapter.notifyDataSetChanged();

//                System.out.println("33333333333333333333"+teams.get(0).members.get(childPosition).getDownloadStatus());
//
//                List<Team> newteams = new ArrayList<>();
//                newteams=teams;
////                teams=newteams.
////
//                teams.addAll()

//
//
//                  teams.get(0).members.remove(0);
//                teams.addAll(newteams);

//                recyclerAdapter.notifyDataSetChanged();
//                handler.sendEmptyMessage(10);

//                System.out.println("下载状态位"+team.members.get(childPosition).g);
                Bundle bundle = new Bundle();
                bundle.putString("studentName",team.members.get(childPosition).studentName);
                bundle.putString("name",team.members.get(childPosition).name);
                bundle.putString("studentId",team.members.get(childPosition).studentId);
                bundle.putString("major",team.members.get(childPosition).major);
                bundle.putString("graduateDate",team.members.get(childPosition).graduateDate);
                bundle.putString("discipline",team.members.get(childPosition).discipline);
                bundle.putString("degreeLevel",team.members.get(childPosition).degreeLevel);
                bundle.putString("issuer",team.members.get(childPosition).issuer);
                bundle.putString("issuingDate",team.members.get(childPosition).issuingDate);

                Intent intent = new Intent(MainActivity.this,certDetailActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        //下拉刷新页面

//                swipeRefreshView =  findViewById(R.id.srl);
//        swipeRefreshView.setProgressBackgroundColorSchemeColor(android.R.color.color_ff);
//        swipeRefreshView.setColorSchemeColors(getResources().getColor(android.R.color.holo_blue_bright),getResources().getColor(android.R.color.holo_red_light),getResources().getColor(android.R.color.holo_orange_light));

//        swipeRefreshLayout=findViewById(R.id.srl);
//        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_red_light,android.R.color.holo_orange_light);
//        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
//        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
                //刷新后执行的操作

//                handler.sendEmptyMessage(10);

//                final GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder> recyclerAdapter_refresh =
//                        new GroupRecyclerAdapter<Team, TeamViewHolder, MemberViewHolder>(teams) {
//                            @Override
//                            protected TeamViewHolder onCreateGroupViewHolder(ViewGroup parent) {
//                                return new TeamViewHolder(layoutInflater.inflate(R.layout.item_team_title, parent, false));
//                            }
//
//                            @Override
//                            protected MemberViewHolder onCreateChildViewHolder(ViewGroup parent) {
//
//
//                                return new MemberViewHolder(layoutInflater.inflate(R.layout.item_team_member, parent, false));
//                            }
//
//                            @Override
//                            protected void onBindGroupViewHolder(TeamViewHolder holder, int groupPosition) {
//                                holder.update(getGroup(groupPosition));
//                            }
//
//                            @Override
//                            protected void onBindChildViewHolder(MemberViewHolder holder, int groupPosition, int childPosition) {
//                                holder.update(getGroup(groupPosition).members.get(childPosition));
//                            }
//
//                            @Override
//                            protected int getChildCount(Team group) {
//                                return group.members.size();
//                            }
//                        };
////
//                recyclerView.setAdapter(recyclerAdapter_refresh);
//                GroupItemDecoration decoration = new GroupItemDecoration(recyclerAdapter_refresh);
//                decoration.setGroupDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_16_dp, null));
//                decoration.setTitleDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_height_1_px, null));
//                decoration.setChildDivider(ResourcesCompat.getDrawable(getResources(), R.drawable.divider_white_header, null));
//                recyclerView.addItemDecoration(decoration);
//
//
//                recyclerAdapter_refresh.setOnGroupClickListener(new OnGroupClickListener() {
//                    @Override
//                    public void onGroupItemClick(View itemView, int groupPosition) {
//                        showToast(recyclerAdapter_refresh.getGroup(groupPosition).title);
//                    }
//                });
//                recyclerAdapter_refresh.setOnChildClickListener(new OnChildClickListener() {
//                    @Override
//                    public void onChildClick(View itemView, int groupPosition, int childPosition) {
//                        Team team = recyclerAdapter_refresh.getGroup(groupPosition);
////                showToast(team.title + ": " +team.members.get(childPosition).name);
//
//                        team.members.get(childPosition).setDownloadStatus(1);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("studentName",team.members.get(childPosition).studentName);
//                        bundle.putString("name",team.members.get(childPosition).name);
//                        bundle.putString("studentId",team.members.get(childPosition).studentId);
//                        bundle.putString("major",team.members.get(childPosition).major);
//                        bundle.putString("graduateDate",team.members.get(childPosition).graduateDate);
//                        bundle.putString("discipline",team.members.get(childPosition).discipline);
//                        bundle.putString("degreeLevel",team.members.get(childPosition).degreeLevel);
//                        bundle.putString("issuer",team.members.get(childPosition).issuer);
//                        bundle.putString("issuingDate",team.members.get(childPosition).issuingDate);
//
//
//
//                        Intent intent = new Intent(MainActivity.this,certDetailActivity.class);
//                        intent.putExtras(bundle);
//
//                        startActivity(intent);
//                    }
//                });


//                final Random random = new Random();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        recyclerAdapter.notifyDataSetChanged();
//                        Toast.makeText(MainActivity.this,"更新了" +1+
//                                "条数据",Toast.LENGTH_SHORT).show();
//                        swipeRefreshView.setRefreshing(false);
//                    }
//                },1200);
//
//            }
//
//        });

   }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}


