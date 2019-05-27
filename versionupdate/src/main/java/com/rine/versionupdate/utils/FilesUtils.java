package com.rine.versionupdate.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

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
    public static String getApkFile() {
        String apkFile = "/apk/" ;
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

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     * @param   filePath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param filePath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }
    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        file = new File(filePath+getApkFile());
        if (  !file.isDirectory()) {
            makeRootDirectory(filePath+getApkFile());
        }
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }
}