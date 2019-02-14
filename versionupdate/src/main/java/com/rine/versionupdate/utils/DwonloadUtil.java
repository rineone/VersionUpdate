package com.rine.versionupdate.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class DwonloadUtil {
    private static DwonloadUtil instance;

    public static DwonloadUtil getInstance() {
        if (instance == null) {
            synchronized (DwonloadUtil.class) {
                if (instance == null) {
                    instance = new DwonloadUtil();
                }
            }
        }
        return instance;
    }

    public void initDownload(String downloadUrl, String title, Context context,String apkName) {
        /**封装下载请求*/
        // 创建下载请求
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        /*
         * 设置在通知栏是否显示下载通知(下载进度), 有 3 个值可选:
         * VISIBILITY_VISIBLE:                   下载过程中可见, 下载完后自动消失 (默认)
         * VISIBILITY_VISIBLE_NOTIFY_COMPLETED:  下载过程中和下载完成后均可见
         * VISIBILITY_HIDDEN:                    始终不显示通知
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 设置通知的标题和描述
        request.setTitle(title);
        request.setDescription(title + "更新");
        /*
         * 设置允许使用的网络类型, 可选值:
         * NETWORK_MOBILE:      移动网络
         * NETWORK_WIFI:        WIFI网络
         * NETWORK_BLUETOOTH:   蓝牙网络
         * 默认为所有网络都允许
         */
        //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);

        // 添加请求头
        // request.addRequestHeader("User-Agent", "Chrome Mozilla/5.0");

        // 设置下载文件的保存位置
        File saveFile = new File(FilesUtils.getInstance().getAppCacheDir(context), FilesUtils.getInstance().apkFile(apkName));
        //如果文件存在，则删除
        if (saveFile.exists()){
            saveFile.delete();
        }
        request.setDestinationUri(Uri.fromFile(saveFile));

        /*
         * 2. 获取下载管理器服务的实例, 添加下载任务
         */
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

// 将下载请求加入下载队列, 返回一个下载ID
        long downloadId = manager.enqueue(request);
        query(downloadId,context);
    }

    public void query(long downloadId,Context context) {
        // 获取下载管理器服务的实例
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        // 创建一个查询对象
        DownloadManager.Query query = new DownloadManager.Query();
        // 根据 下载ID 过滤结果
        query.setFilterById(downloadId);
        // 还可以根据状态过滤结果
        // query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL);
        // 执行查询, 返回一个 Cursor (相当于查询数据库)
        Cursor cursor = manager.query(query);

        if (!cursor.moveToFirst()) {
            cursor.close();
            return;
        }

        // 下载ID
        long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
        // 下载请求的状态
        int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
        // 下载文件在本地保存的路径（Android 7.0 以后 COLUMN_LOCAL_FILENAME 字段被弃用, 需要用 COLUMN_LOCAL_URI 字段来获取本地文件路径的 Uri）
        String localFilename = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
        // 已下载的字节大小
        long downloadedSoFar = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
        // 下载文件的总字节大小
        long totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
        cursor.close();


        /*
         * 判断是否下载成功，其中状态 status 的值有 5 种:
         *     DownloadManager.STATUS_SUCCESSFUL:   下载成功
         *     DownloadManager.STATUS_FAILED:       下载失败
         *     DownloadManager.STATUS_PENDING:      等待下载
         *     DownloadManager.STATUS_RUNNING:      正在下载
         *     DownloadManager.STATUS_PAUSED:       下载暂停
         */
        if (status == DownloadManager.STATUS_SUCCESSFUL) {
            /*
             * 特别注意: 查询获取到的 localFilename 才是下载文件真正的保存路径，在创建
             * 请求时设置的保存路径不一定是最终的保存路径，因为当设置的路径已是存在的文件时，
             * 下载器会自动重命名保存路径，例如: .../demo-1.apk, .../demo-2.apk
             */
            installApp(context,localFilename);
        }
    }

    /**
     * 安装APP
     */
    public void installApp(Context context, String fileName) {
//      开始执行安装
        String localFile = fileName.replace("file:/","");
        File apkFile = new File(localFile);

        if (apkFile.exists()){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//          版本大于 N ，开始使用 fileProvider 进行安装
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(
                        context
                        , "com.taoerxue.children.fileprovider"
                        , apkFile);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
//          正常进行安装
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent);
        }else{
//            ToastUtil.showToast("DwonloadUtil",  context,"安装失败！");
        }

    }
} 