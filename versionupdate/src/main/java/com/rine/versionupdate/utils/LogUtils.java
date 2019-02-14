package com.rine.versionupdate.utils;

import android.util.Log;

public class LogUtils {
    private static LogUtils instance;
    private boolean isLog  = true;
    public static LogUtils getInstance() {
        if (instance == null) {
            synchronized (LogUtils.class) {
                if (instance == null) {
                    instance = new LogUtils();
                }
            }
        }
        return instance;
    }

    private LogUtils() {
    }

    public void Logi(String mess){
        if (isLog){
            Log.i("AppDown",mess);
        }
    }
}