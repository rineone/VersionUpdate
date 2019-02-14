package com.rine.versionupdate.Linstener;

import android.widget.TextView;

public interface UpdateAppListener {
    /**
     * 这个属性是为了一些Toast是自定义的。所以要这样
     * @param mess
     */
    void updateAppToast(String mess);
    void updateAppCancle();
    void updateAppConfirm(TextView tvConfirm,TextView tvCancel,TextView tvClose,TextView tvMsg);
    void updateAppClose();
}
