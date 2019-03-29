package com.wisedu.xuechengapp.demo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mob.MobSDK;
import com.wisedu.xuechengapp.demo.R;
import com.wisedu.xuechengapp.demo.service.SmEncryptService;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;

public class ShareActivity extends AppCompatActivity {
    SmEncryptService smEncryptService=new SmEncryptService();
    String uuid="hsijhdlaj";
    String svcUrl = "http://localhost:8006/addSigntoInstitution?plainText={plainText}";

    private TextView mTitleTextView;
    private Button mbackwardButton;
    private Button mSettingButton;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private PopupWindow popupWindow;
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        MobSDK.init(this);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mbackwardButton = (Button) findViewById(R.id.button_backward);
        mSettingButton = (Button)findViewById(R.id.button_setting);
        mTitleTextView.setText("证书详情");
        mbackwardButton.setVisibility(View.INVISIBLE);
        mSettingButton.setText("分享");

        mSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cutAndSaveImage(v);
                showShare2();
                //showPopwindow();
            }
        });

        //String cert ="{\"certID\":\"2013061367895\";\"name\":\"zhangsan\";\"publisher\":\"SEU\";\"image:0ff2384rhfdjaso8jhshdwqeouowqdcmweunq38ryvn98\";schoolsign:i657saiojio}";
       /* String plainText = "{\"certID\":\"2013061367895\";\"name\":\"zhangsan\";\"publisher\":\"SEU\";\"image:0ff2384rhfdjaso8jjsdjhfshlkjdddddddddddddddddddddhljkdhljahjsdljhhhhhhhhhhhhhhhhhhhhhhhhhhakjshdlkajhlskdjhlaksjhahdlhfgalfdjdshhjjjjjjjjjjjjjjjjjjjjjssssssssssssssssssssssssssjhshdwqeouowqdcmweunq38ryvn98\";schoolsign:ywuquwguy}";

        try {
            //把证书和学校签名分开，证书哈希加密之后，带上学校签名，学生签名，uuid。四个数据sm4加密之后分享文本给用户

            //待改进：链接分享，点击出现页面
            String[] certarray=plainText.split(";schoolsign");
            String cert=certarray[0]+"}";
            String studentsign = smEncryptService.sm2SignCert(cert);
            System.out.println(cert);
            String hashcert= smEncryptService.sm3CreateHash(cert);

            String newcert=hashcert+";studentsign:"+studentsign+";schoolsign"+certarray[1].substring(0,certarray[1].length()-1)+";uuid:"+uuid;
            System.out.println(newcert);
            String encriptCert=smEncryptService.sm4EncryptCert(newcert);
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, encriptCert);
            startActivity(Intent.createChooser(textIntent, "分享"));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }


    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("分享");

        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");

        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/home/lixiuxiu/Desktop/login_pg.png");
        oks.setImageUrl("http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg");


        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
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
        //verifyStoragePermissions(this);
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




}
