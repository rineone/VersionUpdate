package com.rine.versionupdatedemo;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rine.versionupdate.Linstener.UpdateAppDownListener;
import com.rine.versionupdate.Linstener.UpdateAppListener;
import com.rine.versionupdate.UpdateApp;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt = findViewById(R.id.bt_dialgo);
        mContext = this;
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateApp.from(mContext,R.layout.view_alertdialog5)
                .setApkInfo("taoerxue","taoerxueV2.3.7.1801112","淘儿学",getPackageName())
                .setApkUrl("https://image.taoerxue.com/taoerxueV2.3.7.1801112.apk")
                .setIcLauncher(R.mipmap.ic_launcher1)
                        .setLinMainId(R.id.lLayout_bg)
                        .setTvTitleId(R.id.txt_title)
                        .setTvCancleId(R.id.btn_neg)
                        .setTvConfirmId(R.id.btn_pos)
                        .setTvMsgId(R.id.txt_msg)
                        .setPbForcedUpdateId(R.id.pb_forced_update)
                        .setTvSpeedId(R.id.tv_speed)
                        .setmCancel("取消1")
                        .setmConfirm("确定1")
                        .setmIsShowNofit(true)
                        .setmIsShowPb(false)
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
    }
}
