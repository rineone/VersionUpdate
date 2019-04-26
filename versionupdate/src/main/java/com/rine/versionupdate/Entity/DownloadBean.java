package com.rine.versionupdate.Entity;

import java.io.File;

/**
 * 下载实例
 * @version 2019/1/23
 */
public class DownloadBean {
    private long total;
    private long bytesReaded;
    private int progress;
    private boolean isSuccess;
    /**下载进度**/
    private long cusDownLen;
    public  DownloadBean(long total, long bytesReaded,int progress, boolean isSuccess) {
        this.total = total;
        this.bytesReaded = bytesReaded;
        this.progress = progress;
        this.isSuccess = isSuccess;
    }
    public DownloadBean( boolean isSuccess){
        this.isSuccess = isSuccess;
    }
    public DownloadBean( boolean isSuccess,long bytesReaded,long total ){
        this.bytesReaded = bytesReaded;
        this.total = total;
        this.isSuccess = isSuccess;
    }
    public DownloadBean( long bytesReaded,long total){
        this.bytesReaded = bytesReaded;
        this.total = total;
    }



    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getBytesReaded() {
        return bytesReaded;
    }

    public void setBytesReaded(long bytesReaded) {
        this.bytesReaded = bytesReaded;
    }
}

