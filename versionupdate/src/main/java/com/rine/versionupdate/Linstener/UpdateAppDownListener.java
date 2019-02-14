package com.rine.versionupdate.Linstener;

import android.app.NotificationManager;

/**
 * 下载APP进度
 * @author Rine
 * @version 1.0(2019/1/24)
 */
public interface UpdateAppDownListener {
    void updateAppDownPrepare();
    void updateAppDownSuccess();
    void updateAppDownFail(String e);
    void updateAppInsertFail();
    void updateAppDownGetProgress(int progress);
    void updateAppDowngetNotiMan(NotificationManager notificationManager, int nofitDownAppCode) ;
}
