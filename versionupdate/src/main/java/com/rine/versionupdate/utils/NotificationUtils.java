package com.rine.versionupdate.utils;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.rine.versionupdate.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 通知栏方法
 * @author rine
 * @version 1.0(2019/1/25)
 */
public class NotificationUtils {
    private static NotificationUtils instance;
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    private static NotificationManager manager;
    public  final String id = "channel_1";
    public  final String name = "channel_name_1";

    private Context context;

    public static NotificationUtils getInstance() {
        if (instance == null) {
            synchronized (NotificationUtils.class) {
                if (instance == null) {
                    instance = new NotificationUtils();
                }
            }
        }
        return instance;
    }

    private NotificationUtils() {
    }

    public NotificationUtils(Context context){
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(NotificationManager manager){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
        manager.createNotificationChannel(channel);
    }

    public NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content){
        return new Notification.Builder(context, id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            getManager().notify(1,notification);
        }else{
            Notification notification = getNotification_25(title, content).build();
            getManager().notify(1,notification);
        }
    }


    public NotificationCompat.Builder getNotification_25(int mIcLauncher){
        return new NotificationCompat.Builder(context)
                .setSmallIcon(mIcLauncher)
                .setContentTitle("开始下载")
                //禁止用户点击删除按钮删除
                .setAutoCancel(false)
                .setPriority(Notification.PRIORITY_MAX)// 设置该通知优先级
                //取消右上角的时间显示
                .setShowWhen(false)
//                .setOngoing(true)
                .setVibrate(new long[]{0})
                .setSound(null)
                .setProgress(100,0,false)
                .setContentText("下载中...0%");
//                //禁止滑动删除
//                .setOngoing(true);添加这个在更新通知栏的时候一直震动
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  Notification.Builder getChannelNotification(int mIcLauncher){
        return new Notification.Builder(context, id)
                .setSmallIcon(mIcLauncher)
                .setContentTitle("开始下载")
                //禁止用户点击删除按钮删除
                .setAutoCancel(false)
                .setSmallIcon(mIcLauncher)
                .setPriority(Notification.PRIORITY_MAX)// 设置该通知优先级
                //取消右上角的时间显示
                .setShowWhen(false)
//                .setOngoing(true)
                .setVibrate(new long[]{0})
                .setSound(null)
                .setProgress(100,0,false)
                .setContentText("下载中...0%");
//                //禁止滑动删除
//                .setOngoing(true);添加这个在更新通知栏的时候一直震动
//
    }


    @SuppressLint("NewApi")
    public boolean isNotificationEnabled(Context context) {

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void toSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        context.startActivity(localIntent);
    }
}