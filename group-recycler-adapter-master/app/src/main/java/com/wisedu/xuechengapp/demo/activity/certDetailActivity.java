package com.wisedu.xuechengapp.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wisedu.xuechengapp.demo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;


public class certDetailActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private View contentView;

    private Button button_picture;
    private Button button_url;
    private Button button_return;

    private TextView mTitleTextView;
    private Button mbackwardButton;
    private Button mSettingButton;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String path="http://172.20.6.207:8010/validate?cipherText=nhMxPvhUr3rdHy1EGDvrMEYw8F/WqP+SPdvPZjqOc4fhKH/HpBGEM82t3Gf4YD68FI4c3LKzSRZ31KJVAsOVNrid0oBHdrqV3bLNV45itmNYXBvJHZweGgMuU+aOe1vUZP954WhbnvKEWlJrClGW/KTuIQeHvlEPNpctw/1NJOQFgjN24w4eWQ7uWoMRcH216DuWOUbVZDJDSfta9hGLCaaAG7uZ1IEXOVSfj8Ko8c3EDubPe94xUzkABy6BR8+BN1hUrocHWgGQBwxT7WBZN0oYxVsbKaUSIkNqT1BMfPqKl/H3x5XTR0ZPHW/NDoQs/+4RjMhHgu4FDhZBbrndGv5sEmwZxAkjJ3PVcfQOxf3YUqMwZIZ+GCBpZl6j1wQwKlkC2OZvIxLi1paN3uPiEb90G1y0WHDVWx5UAlEs/Ks340Lfh0eketM0+xE8BAQS3GS8FZDJaprCqJV8/3qQUvimh3eQbFZpHTA1IxzyHXj52SYtBYv/M5CZE+W3PeQAnhU3oub8bT2VJfuJ5uJ85HeIPsiHJ9yZxX3g82CdI9xyqN0riNz8ZdmhNLyOoKueHS6hIuWjfjnfA/B9c+Kh1bIN4dL9NXJ61gw79enm9SN96dqyE8SJKQolZiRbK+NGcHBuUzrCU1U4phYv4rX97Q==&studentSign=3045022100801DCAEDD0DFAAE190EAF4D3AB3C2EDAADC459B8C2867855AFE6EDA573BC163502202C15539691F2BC4A6F0F812D2E5D8E8B3CCB2EACE8848D2F8BED7F811FF707DE&uuid=b606c380-0378-41dc-afb8-2979f90ec82b";
    //String path ="www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cert);

        Bundle bundle = getIntent().getExtras();
        String studentName = bundle.getString("studentName");
        TextView name2textView=findViewById(R.id.name2);
        name2textView.setText(studentName);

        String major = bundle.getString("major");
        TextView major2textView=findViewById(R.id.major2);
        major2textView.setText(major);

        String studentId = bundle.getString("studentId");
        TextView student2textView=findViewById(R.id.studentId2);
        student2textView.setText(studentId);

        String graduateDate = bundle.getString("graduateDate");
        TextView graduateDate2textView=findViewById(R.id.graduateDate2);
        graduateDate2textView.setText(graduateDate);


        String discipline = bundle.getString("discipline");
        TextView discipline2textView=findViewById(R.id.discipline2);
        discipline2textView.setText(discipline);

        String degreeLevel = bundle.getString("degreeLevel");
        TextView degreeLevel2textView=findViewById(R.id.degreeLevel2);
        degreeLevel2textView.setText(degreeLevel);

        String issuingDate = bundle.getString("issuingDate");
        TextView issuingDate2textView=findViewById(R.id.issuingDate2);
        issuingDate2textView.setText(issuingDate);

        String issuer = bundle.getString("issuer");
        TextView issuer2textView=findViewById(R.id.issuer2);
        issuer2textView.setText(issuer);

        String name = bundle.getString("name");
        ImageView certImage = findViewById(R.id.certImage);

        switch (name){
            case "学位证书":certImage.setImageResource(R.drawable.njugra);break;
            case "南京大学全日制本科学位证书":certImage.setImageResource(R.drawable.njuba);break;
            case "东南大学全日制硕士毕业证书":certImage.setImageResource(R.drawable.seugra);break;
            case "东南大学全日制硕士学位证书":certImage.setImageResource(R.drawable.seuma);break;

            default:certImage.setImageResource(R.drawable.njucert2);break;
        }


        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mbackwardButton = (Button) findViewById(R.id.button_backward);
        mSettingButton = (Button)findViewById(R.id.button_setting);
        mTitleTextView.setText("证书详情");
        mbackwardButton.setVisibility(View.VISIBLE);
        mbackwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(certDetailActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
        mSettingButton.setText("分享");

        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNormalDialog();
                //showShare();
            }
        });

    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(certDetailActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("请您选择分享方式:");
        normalDialog.setPositiveButton("截图分享",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cutAndSaveImage(getWindow().getDecorView());
                        showShare2();
                    }
                });
        normalDialog.setNegativeButton("链接分享",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        showShare();
                    }
                });
        // 显示
        normalDialog.show();
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");

        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(path);

        //oks.setTitleUrl("http://www.baidu.com");

        // text是分享文本，所有平台都需要这个字段
        oks.setText("学程链分享给您一个证书链接,点击可验证");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getResources().getResourcePackageName(R.drawable.vfy) + "/" + getResources().getResourceTypeName(R.drawable.vfy) + "/" + getResources().getResourceEntryName(R.drawable.vfy));
       // oks.setImageUrl("http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg");
        //oks.setImagePath("/home/lixiuxiu/Desktop/vfy.jpg");
        oks.setImageUrl("http://s3.sinaimg.cn/orignal/001owXWuzy7l3rLuyBk72");

        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(path);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    private void showShare2() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("https://www.baidu.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(Environment.getExternalStorageDirectory()+ "/XCPicture/1.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if (platform.getName().equalsIgnoreCase(QQ.NAME)) {
                    paramsToShare.setText(null);
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                    paramsToShare.setImagePath(Environment.getExternalStorageDirectory()+ "/XCPicture/1.png");
                } else if (platform.getName().equalsIgnoreCase(QZone.NAME)) {
                    paramsToShare.setText(null);
                    paramsToShare.setTitle(null);
                    paramsToShare.setTitleUrl(null);
                    paramsToShare.setImagePath(Environment.getExternalStorageDirectory()+ "/XCPicture/1.png");
                }

            }
        });
        oks.setUrl("https://www.baidu.com/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("test");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://www.baidu.com/");
        // 启动分享GUI
        oks.show(this);
    }



    public void cutAndSaveImage(View view) {
        verifyStoragePermissions(this);
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            saveImage(this, bmp);
        }
    }

    public String saveImage(Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "XCPicture");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //String fileName = System.currentTimeMillis() + ".jpg";
        String fileName = "1.png";
        File file = new File(appDir, fileName);
        if(file.exists())
            file.delete();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

        return fileName;
    }

    public static void verifyStoragePermissions(Activity activity) { // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) { // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

/*    private void showPopwindow() {
        contentView = LayoutInflater.from(certDetailActivity.this).inflate(R.layout.pop, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        button_picture=LayoutInflater.from(certDetailActivity.this).inflate(R.layout.pop, null).findViewById(R.id.button_picture);
        button_url=LayoutInflater.from(certDetailActivity.this).inflate(R.layout.pop, null).findViewById(R.id.button_url);
        button_return=LayoutInflater.from(certDetailActivity.this).inflate(R.layout.pop, null).findViewById(R.id.button_return);
        System.out.print("button_picture:"+button_picture.getText().toString());
        button_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        button_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare2();
            }
        });
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
                return true;
            }
        }
        return false;
    }

    public void openPopWindow(View v) {
        //从底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }*/

}
