package com.rine.versionupdate.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.rine.versionupdate.Entity.DownloadBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FilesUtils {
    public static String photoFile = "/phone/";
    private static FilesUtils instance = null;

    public static FilesUtils getInstance() {
        if (instance == null) {
            synchronized (FilesUtils.class) {
                if (instance == null) {
                    instance = new FilesUtils();
                }
            }
        }
        return instance;
    }

    public String apkFile(String apk) {
        String apkFile = "/apk/" + apk + ".apk";
        return apkFile;
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
    public String getAppCacheDir(Context context) {
        String storageRootPath = null;
        try {
            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
            if (context.getExternalCacheDir() != null) {
                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(storageRootPath)) {
            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
            storageRootPath = Environment.getExternalStorageDirectory() + "/" + context.getPackageName();
        }

        return storageRootPath;
    }

    public DownloadBean getRealFileName(Context context,DownloadBean downloadBean, File file) {
        long downloadLength = 0;
        long contentLength = downloadBean.getTotal();
        String fileName = file.getName();
        String fileNameOther = "";
        if (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
            downloadLength = file.length();
        }
        LogUtils.getInstance().Logi("fileName:"+fileName);
        //之前下载过,需要重新来一个文件
        int i = 1;
        while (downloadLength <= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");

            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            LogUtils.getInstance().Logi("fileNameOther:"+fileNameOther);
            File newFile = new File(getAppCacheDir(context), fileNameOther);
            //删除原文件
            if (file.exists()){
                file.delete();
            }
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }
        String oldPath = getAppCacheDir(context) + "/" + fileNameOther;
        String newPath = getAppCacheDir(context) + "/" + fileName;
        LogUtils.getInstance().Logi("oldPaht:"+oldPath);
        LogUtils.getInstance().Logi("newPath:"+newPath);
        //设置改变过的文件名/大小
        renameFile(oldPath,newPath);
        downloadBean.setBytesReaded(downloadLength);
        return downloadBean;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    private void renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }

    /**
     * 获取指定文件的大小
     *
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) {

        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);//使用FileInputStream读入file的数据流
                size = fis.available();//文件的大小
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
        }
        return size;
    }
}