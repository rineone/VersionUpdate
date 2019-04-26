package com.rine.versionupdate.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.rine.versionupdate.Entity.DownloadBean;
import com.rine.versionupdate.R;
import com.rine.versionupdate.api.OkHttpDown;
import com.rine.versionupdate.utils.DwonloadUtil;
import com.rine.versionupdate.utils.FilesUtils;
import com.rine.versionupdate.utils.LogUtils;
import com.rine.versionupdate.utils.NotificationUtils;
import com.rine.versionupdate.utils.RxBus;
import com.rine.versionupdate.utils.StringUtil;
import com.rine.versionupdate.widget.AlertDialogUpdate;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 更新APP服务
 * @version 1.0(2019/1/23)
 * @author rine
 */
public class UpdataAppService extends Service {

    private DownloadCallback callback;
    private final int nofitDownAppCode = 0x1;
    private NotificationCompat.Builder builder_25;
    private Notification.Builder builder_26;
    private NotificationManager notificationManager;
    private File filePaths;
    private CompositeDisposable cd = new CompositeDisposable();
    private DownloadBinder binder = new DownloadBinder();
    /**是否显示进度条**/
    protected boolean isShowPbService;
    /**是否显示Nofit进度条**/
    protected boolean isShowNofitService;
    private AlertDialogUpdate alertDialogUpdateService;
    private Context mContext;
    private OkHttpDown okHttpDown;
    private String apkUrl;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /**
     * 和activity通讯的binder
     */
    public class DownloadBinder extends Binder {
        public UpdataAppService getService(Context context,boolean isShowNofit, boolean isShowPb, AlertDialogUpdate alertDialogUpdate,File filePath){
            isShowPbService = isShowPb;
            filePaths = filePath;
            isShowNofitService = isShowNofit;
            alertDialogUpdateService = alertDialogUpdate;
            mContext = context;
            return UpdataAppService.this;
        }
    }

    /**
     * 解除绑定时销毁资源
     * @param conn
     */
    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        notificationManager.cancelAll();
        cd.dispose();
        cd.clear();
        cd = null;
        notificationManager = null;
        builder_25 = null;
        builder_26 = null;
    }

    public void startUpdateService(  String url, int mIcLauncher, String mApkNameVersion, String mApkNameTitle ,DownloadCallback callback) {
        this.callback = callback;
        String code = StringUtil.getAppVersionName(2,mContext);
        if (alertDialogUpdateService == null){
            isShowNofitService = false;
        }
        apkUrl = url;
//        DwonloadUtil.getInstance().initDownload(apkUrl,"下载",mContext,"cag1","cago2");
        handleUpdate(mIcLauncher,mApkNameVersion,mApkNameTitle,code);
    }


    private void handleUpdate(  int mIcLauncher,String mApkNameVersion,String apkTitle,String code) {

        //判断是否显示在通知栏上
        if (isShowNofitService) {
            //先判断Android自带的通知栏是否可用
//            if (canDownloadState(mContext)){
//                DwonloadUtil.getInstance().initDownload(url,apkTitle,mContext,mApkNameVersion);
//            }else{
            notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            if (StringUtil.getApi() >= 19){
                if (NotificationUtils.getInstance().isNotificationEnabled(mContext)){
                    NotificationUtils notificationUtils = new NotificationUtils(mContext);
                    if (Build.VERSION.SDK_INT>=26){
                        notificationUtils.createNotificationChannel(notificationManager);
                        builder_26 =  notificationUtils.getChannelNotification(mIcLauncher);
                        Notification notification = builder_26.build();
                        notificationManager.notify( nofitDownAppCode,notification);
                    }else{
                        builder_25 = notificationUtils.getNotification_25(mIcLauncher);
                        Notification notification = builder_25.build();
                        notificationManager.notify( nofitDownAppCode,notification);
                    }
                    callback.onNotificationManager(notificationManager,nofitDownAppCode);
                }else{
                    callback.toast(mContext.getResources().getString(R.string.Notification_null));
                    NotificationUtils.getInstance().toSetting(mContext);
                }
            }else{
                callback.toast(mContext.getResources().getString(R.string.Notification_null));
                NotificationUtils.getInstance().toSetting(mContext);
                //19以下不能判断是否有通知权限
            }
        }
        //下载
        okHttpDown = getOkHttpDown(okHttpDown);
        subscribeEvent(mApkNameVersion);
        okHttpDown.downApp(mContext,apkUrl,mApkNameVersion,0,0) ;

    }


    /**
     *  判断是否为空，不为空，则清除下载
     * @param okHttpDown1
     * @return
     */
    private OkHttpDown getOkHttpDown(OkHttpDown okHttpDown1){
        if (okHttpDown1 == null){
            okHttpDown1 = new OkHttpDown(callback);
        }else {
            okHttpDown1.closeDowm();
            okHttpDown1 = new OkHttpDown(callback);
        }
        return okHttpDown1;
    }

    /**
     * 下载更新实时进度
     */
    private void subscribeEvent(final String mApkNameVersion) {
        final String downloading = mContext.getResources().getString(R.string.down_loading);
        callback.onPrepare();
        LogUtils.getInstance().Logi("失败返回3："+mApkNameVersion);
        RxBus.getDefault().toObservable(DownloadBean.class)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.getInstance().Logi("触发");
                        cd.add(d);
                    }
                    @Override
                    public void onNext(DownloadBean downloadBean) {
                        try {
                            LogUtils.getInstance().Logi("返回结果");
                            if (!downloadBean.isSuccess()){
                                LogUtils.getInstance().Logi("失败返回0："+downloadBean.getBytesReaded());
                                if (downloadBean.getTotal()!=0){
                                    okHttpDown.downApp(mContext,apkUrl,mApkNameVersion,downloadBean.getTotal(),downloadBean.getBytesReaded());
                                    return;
                                }else{
                                    LogUtils.getInstance().Logi("失败返回1：" );
                                    if (isShowNofitService){
                                        notificationManager.cancel( nofitDownAppCode);
                                    }
                                    if (isShowPbService){
                                        alertDialogUpdateService.dismiss();
                                    }
                                    cd.dispose();
                                    callback.onFail("数据错误");
                                    return;
                                }
                            }
                            int progress = downloadBean.getProgress() ;
                            callback.onProgress(progress);
                            if (progress == 100){
                                if (downloadBean.getBytesReaded() < downloadBean.getTotal()){
                                    progress = 99;
                                } else{
                                    progress = 100;
                                }
                            }
                            if (isShowNofitService){
                                if (notificationManager !=null){
                                    if (Build.VERSION.SDK_INT>=26){
                                        builder_26.setContentText(downloading+String.valueOf(progress)+"%")
                                                //取消震动
                                                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                                                .setProgress(100,progress,false);
                                        notificationManager.notify( nofitDownAppCode, builder_26.build());
                                    }else {
                                        builder_25 .setContentText(downloading+String.valueOf(progress)+"%")
                                                //取消震动
                                                .setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE)
                                                .setProgress(100,progress,false);
                                        notificationManager.notify( nofitDownAppCode, builder_26.build());
                                    }
                                }
                            }
                            if (isShowPbService){
                                DecimalFormat df = new DecimalFormat("0.00");
                                double downIngSzie = (double)downloadBean.getBytesReaded()/1024/1024;
                                double downMaxSize = (double)downloadBean.getTotal()/1024/1024;
                                alertDialogUpdateService.setPbUpdate( progress,df.format(downIngSzie)+"M", df.format(downMaxSize)+"M");
                            }

                            if (progress >= 100){


                                if (isShowNofitService){
                                    notificationManager.cancel( nofitDownAppCode);
                                }
                                if (isShowPbService){
                                    alertDialogUpdateService.dismiss();
                                }
                                cd.dispose();
                                if (FilesUtils.getFileSize(filePaths) != downloadBean.getTotal()){
                                    callback.onFail("下载过程失败!");
                                    return;
                                }
                                callback.onComplete();
                            }

                        }catch (Exception e){
                            callback.onFail(e.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                       callback.onFail(e.toString());
                    }

                    @Override
                    public void onComplete() {
//                        subscribeEvent();
                    }
                });
    }




    /**
     * 判断Download是否可用。
     * @param ctx
     * @return
     */
    private static boolean canDownloadState(Context ctx) {
        try {
            int state = ctx.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 销毁时清空一下对notify对象的持有
     */
    @Override
    public void onDestroy() {
        if (cd!=null){
            cd.dispose();
            cd.clear();
        }
        if (okHttpDown!=null){
            okHttpDown.closeDowm();
        }
        okHttpDown = null;
        notificationManager = null;
        super.onDestroy();
    }


    /**
     * 定义一下回调方法
     */
    public interface DownloadCallback{
        void onPrepare();
        void onNotificationManager(NotificationManager notificationManager,int nofitDownAppCode);
        void onProgress(int progress);
        void onComplete();
        void onFail(String e);
        void toast(String mess);
    }


    private String getApkUrl(String url){
        String apkUrl = "";
        return apkUrl;
    }
}
