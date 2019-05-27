package com.rine.versionupdatedemo;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rine.versionupdate.Linstener.UpdateAppDownListener;
import com.rine.versionupdate.Linstener.UpdateAppListener;
import com.rine.versionupdate.UpdateApp;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = findViewById(R.id.bt_dialgo);
        Button bt2 = findViewById(R.id.bt_dialgo2);
        Button btDown = findViewById(R.id.bt_down);
        mContext = this;
//        final String apkPath = "https://image.taoerxue.com/taoerxueV2.3.7.1801112.apk";
        final String apkPath = "http://wlq.wlqpt.cn/api/cargo/download";


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateApp.from(mContext)
                        .setApkInfo("app","appV1.1.1.1801112","app名",getPackageName())
                        .setApkUrl(apkPath)
                        .setIsCusLayout(false)  //默认为false
                        .setGraly(true)    //默认为true
                        .setIsDuanDian(true)
                        .setmMsg("更新内容未知哦哦哦哦哦哦哦哦")
                        .setFailToast("下载失败！")
                        .setSuccessToast("下载成功！")
                        .show();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateApp.from(mContext,R.layout.view_alertdialog5)
                .setApkInfo("app","appV1.1.1.1801112","app名",getPackageName())
                .setApkUrl("https://image.taoerxue.com/taoerxueV2.3.7.1801112.apk")
                .setIsCusLayout(true)
                .setIcLauncher(R.mipmap.ic_launcher2)
                        .setLinMainId(R.id.lLayout_bg)
                        .setTvTitleId(R.id.txt_title)
                        .setTvCancleId(R.id.btn_neg)
                        .setTvConfirmId(R.id.btn_pos)
                        .setTvMsgId(R.id.txt_msg)
                        .setPbForcedUpdateId(R.id.pb_forced_update)
                        .setTvSpeedId(R.id.tv_speed)
                        .setmCancel("取消1")
                        .setmConfirm("确定1")
                        .setmIsShowNofit(true)  //默认为true
                        .setmIsShowPb(false)    //默认为false
                        .setmTitle("这是一个更新")
                        .setmMsg("更新内容")
                        .setUpdateAppDownListener(new UpdateAppDownListener() {
                            @Override
                            public void updateAppDownPrepare() {
                                Toast.makeText(mContext,"开始下载！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateAppDownSuccess() {
//                                Toast.makeText(mContext,"下载成功！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateAppDownFail(String e) {
                                Toast.makeText(mContext,"下载失败！",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateAppInsertFail() {

                            }

                            @Override
                            public void updateAppDownGetProgress(int progress) {
                                //显示进度
                            }

                            @Override
                            public void updateAppDowngetNotiMan(NotificationManager notificationManager, int nofitDownAppCode) {
                                //为了在退出APP时消除nofti
                            }
                        })
                .setUpdateAppListener(new UpdateAppListener() {
                    @Override
                    public void updateAppToast(String mess) {
                        Toast.makeText(mContext,mess,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void updateAppCancle() {

                    }

                    @Override
                    public void updateAppConfirm(TextView tvConfirm, TextView tvCancel, TextView tvClose, TextView tvMsg) {

                    }

                    @Override
                    public void updateAppClose() {

                    }
                })
                .show();
            }
        });

        btDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateApp.from(mContext,0).launchAppDetail(getPackageName(),"");
            }
        });
    }

  /**
     * 启动到应用商店app详情界面（oppo,vivo,小米,魅族都可以跳转过去）
     *
     * @param appPkg  目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
