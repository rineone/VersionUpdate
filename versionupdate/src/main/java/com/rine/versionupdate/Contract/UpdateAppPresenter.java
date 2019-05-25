package com.rine.versionupdate.Contract;


import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.rine.versionupdate.Service.UpdataAppService;
import com.rine.versionupdate.utils.FilesUtils;
import com.rine.versionupdate.utils.LogUtils;
import com.rine.versionupdate.utils.StringUtil;
import com.rine.versionupdate.widget.AlertDialogUpdate;

import java.io.File;

import static android.os.Process.killProcess;

/**
 * @author RINE
 * @version 1.0(2019 / 1 / 23)
 * 更新逻辑类
 */
public class UpdateAppPresenter  implements UpdateAppContract.Presenter {
    private UpdateAppContract.View view;
    private ServiceConnection conn;
    private Intent intent;
    private boolean isBind = false;
    private String mApkPackageName;
    public UpdateAppPresenter(UpdateAppContract.View view) {
        this.view = view;
    }


    @Override
    public void startDown(final Context context,final boolean isDuandian, final boolean isShowNofit, final boolean isShowPb
            , final AlertDialogUpdate alertDialogUpdate, final String url
            , final int mIcLauncher , final String mApkNameVersion, final String mApkNameTitle, final String mApkPackageName1
            , final String toastFail,final String toastSuccess) {
        final File file = new File(FilesUtils.getInstance().getAppCacheDir(context), FilesUtils.getInstance().apkFile(mApkNameVersion));
        if (conn == null){
            //如果弹窗不显示，则把弹窗取消了
            if (!isShowPb){
                dismissNoCancle(alertDialogUpdate);
            }
            LogUtils.getInstance().Logi("开始下载");
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mApkPackageName = mApkPackageName1;
                    UpdataAppService.DownloadBinder binder = (UpdataAppService.DownloadBinder) service;
                    UpdataAppService myService = binder.getService(context,isDuandian,isShowNofit,isShowPb,alertDialogUpdate,file);
                    myService.startUpdateService(url,mIcLauncher,mApkNameVersion,mApkNameTitle, new UpdataAppService.DownloadCallback() {
                        @Override
                        public void onPrepare() {
                            view.downAppPrepare();
                        }

                        @Override
                        public void onNotificationManager(NotificationManager notificationManager, int nofitDownAppCode) {
                            view.getNotificationManager(notificationManager,nofitDownAppCode);
                        }

                        @Override
                        public void onProgress(int progress) {
                            view.getProgress(progress);
                        }

                        @Override
                        public void onComplete() {
                            installApp(isShowPb,context,mApkNameVersion);
                            view.downAppSuccess();
                            if (!StringUtil.isNullOrEmpty(toastSuccess)){
                                Toast.makeText(context,toastSuccess,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(String e) {
                            view.downAppFail(e);
                            dismiss(alertDialogUpdate);
                            if (!StringUtil.isNullOrEmpty(toastFail)){
                                Toast.makeText(context,toastFail,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void toast(String mess) {
                            view.toast(mess);
                        }
                    });

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    //意味中断，较小发生，酌情处理
                }
            };
            intent = new Intent(context,UpdataAppService.class);
            isBind = context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 会把服务diss掉
     * @param alertDialogUpdate
     */
    private void dismiss( AlertDialogUpdate alertDialogUpdate){
        if (alertDialogUpdate!=null){
            alertDialogUpdate.dismiss();
        }
    }

    /**
     * 不会把服务diss掉
     * @param alertDialogUpdate
     */
    private void dismissNoCancle( AlertDialogUpdate alertDialogUpdate){
        if (alertDialogUpdate!=null){
            alertDialogUpdate.dismissNoCancle();
        }
    }
    /**
     * 是否存在APK
     * @return
     */
    private boolean isHaveApk(Context mContext,String apkName){
        boolean haveApk = false;
        File apkFile = new File(FilesUtils.getInstance().getAppCacheDir(mContext), FilesUtils.getInstance().apkFile(apkName));
        LogUtils.getInstance().Logi("安装APP");
        if (apkFile.exists()) {
        }else{
        }
        return haveApk;
    }

    /**
     * 安装APP
     */
    private void installApp(boolean isShowPbService,Context mContext,String apkName) {
//      开始执行安装
        File apkFile = new File(FilesUtils.getInstance().getAppCacheDir(mContext), FilesUtils.getInstance().apkFile(apkName));
        LogUtils.getInstance().Logi("安装APP");
        if (apkFile.exists()){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//          版本大于 N ，开始使用 fileProvider 进行安装
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(
                        mContext
//                        , "com.rine.versionupdatedemo.fileprovider"
                        , mApkPackageName + ".fileprovider"

                        , apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
//          正常进行安装
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            mContext.startActivity(intent);
        }else{
            view.insertAppFail();
        }

        if (isShowPbService){
            //直接强制安装
            killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void clear(Context context) {
        try {
            if (conn!=null){
                if (isBind){
                    isBind = false;
                    context.unbindService(conn);
                    if (intent != null){
                        context.stopService(intent);
                        intent = null;
                    }
                }
            }
        }catch (Exception e){

        }

    }
}
