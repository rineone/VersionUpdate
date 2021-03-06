package com.rine.versionupdate.Contract;


import android.app.NotificationManager;
import android.content.Context;

import com.rine.versionupdate.widget.AlertDialogUpdate;

/**
 * @author RINE
 * @version 1.0(2019 / 1 / 23)
 * view 及 功能 接口
 */
public class UpdateAppContract {
    public interface View {
        void toast(String mess);
        void downAppPrepare();
        void downAppSuccess();
        void downAppFail(String e);
        void insertAppFail();
        void getProgress(int progress);
        void getNotificationManager(NotificationManager notificationManager, int NofitDownAppCode);
    }

    interface Presenter{
        /**
         *
         * @param context
         * * @param isDuandian 是否断点
         * @param isShowNofit 是否显示在通知栏上
         * @param isShowPb 是否显示在弹窗上
         * @param alertDialogUpdate
         * @param url
         * @param mIcLauncher
         * @param mApkNameVersion
         * @param mApkNameTitle APK在通知栏上显示的标题
         * @param mApkPackageName 包名
         */
        void startDown(Context context,boolean isDuandian,boolean isShowNofit, boolean isShowPb
                ,AlertDialogUpdate alertDialogUpdate,String url,int mIcLauncher,String mApkNameVersion,String mApkNameTitle,String mApkPackageName
                , String toastFail, String toastSuccess);
        void clear(Context context);
    }
}