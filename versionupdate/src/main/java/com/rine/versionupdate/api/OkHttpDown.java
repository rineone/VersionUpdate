package com.rine.versionupdate.api;

import android.content.Context;

import com.rine.versionupdate.Entity.DownloadBean;
import com.rine.versionupdate.Service.UpdataAppService;
import com.rine.versionupdate.utils.FilesUtils;
import com.rine.versionupdate.utils.LogUtils;
import com.rine.versionupdate.utils.RxBus;
import com.rine.versionupdate.utils.StringUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;
import okio.Okio;
import okio.Sink;
import okio.Source;

import static java.lang.Thread.sleep;

/**
 * APK下载
 * @author wu
 * 2018-8-1
 */
public class OkHttpDown {
    private boolean isBreakDown = false;
    private OkHttpClient client;
    private InputStream is = null;
    private UpdataAppService.DownloadCallback callback;
    private FileOutputStream fos = null;
    /** 重新下载次数**/
    private int reDownSize  = 0;
    //重新下载总次数
    private int reMainDownSize  = 0;
    private boolean isDuandian;
    private Context mContext;
    private String mApkNameVersion;
    public OkHttpDown( UpdataAppService.DownloadCallback callback,boolean mIsDuandian) {
        isDuandian = mIsDuandian;
        if (client == null){
            client = new OkHttpClient();
        }
        if (callback != null){
            this.callback = callback;
        }
    }

    public OkHttpClient getClient(){
        return client;
    }

    /**
     * apk下载
     * @param url
     * @return
     */
    public boolean downApp(final Context context, final String url, final String apkNameVersion, final long totalLen,final long cusDownLen) {
        DownloadBean downloadBean = new DownloadBean(0,totalLen);
        try {
            mContext = context;
            mApkNameVersion = apkNameVersion;
            String RANGE = "";
            final File file = new File(FilesUtils.getInstance().getAppCacheDir(context), FilesUtils.getInstance().apkFile(mApkNameVersion));
            //当下载纪录为0时，才去清除APK
            //判断父文件夹是否存在，不存在则创建
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdir();
            }else{
                if (isDuandian){
                    if (totalLen == 0){
                    }else{
                        long fileLen = file.length();
                        if (fileLen!=cusDownLen){
                            //如果下载的长度和文件长度不匹配则重新下载
                            deleteFile(mApkNameVersion,1,"");
                            fileLen = 0;
                            RANGE ="";
                        }else{
                            //  downloadBean = FilesUtils.getInstance().getRealFileName(context,downloadBean,file);
                            //找到了文件,代表已经下载过,则获取其长度
                            RANGE ="bytes=" + fileLen + "-" + totalLen;
                        }
                        downloadBean.setBytesReaded(fileLen);
                    }
                }else{
                    if (totalLen == 0){
                    }else{
                        deleteFile(mApkNameVersion,1,"");
                        RANGE ="";
                        downloadBean.setBytesReaded(1);
                    }

                }
            }
            if (totalLen!=0){
                sleep(1000);
            }
            //访问网络操作
            Request request = new Request.Builder().url(url)
                    .addHeader("RANGE", RANGE)
                    .build();
            final double totalRead = downloadBean.getBytesReaded();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    netErrorSend2((long) totalRead,totalLen);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.body() == null) {
                        netErrorSend2((long) totalRead,totalLen);
                        return;
                    }
                    if (isDuandian){
                        writeFile(context,file,response,mApkNameVersion,totalRead,totalLen);
                    }else{
                        writeFile(context,file,response,mApkNameVersion,0,totalLen);
                    }
                }
            });
            return true;
        }catch (Exception e){
            netErrorSend2( downloadBean.getBytesReaded(),totalLen);
            setCallbackFail(e.toString());
            return false;
        }
    }

    /**
     * 文件是否存在
     */
    private boolean isFile(File file,long total){
        try {
            long fileLen = file.length();
            if (fileLen!=total){
                //如果存在的文件的长度和总长度不匹配则重新下载
                deleteFile(mApkNameVersion,1,"");
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }
    /**
     * 读取文件
     *
     * @param response
     */
    private void writeFile(Context context,File file,Response response,String mApkNameVersion,double totalRead, long totalLen) {
        double progress = 0;
        //控制流量数
        int proInt;
        if (reDownSize<10){
            reDownSize ++;
            is = response.body().byteStream();
            double total = 0;
            try {
                //如果网络问题则停留1秒
                if (totalRead!=0){
                    sleep(1000);
                }
                //如果是再次连接的，总长度为第一次获取的值
                if (totalLen!=0){
                    total = totalLen;
                }else {
                    total = response.body().contentLength();
                }
                /**阀门，控制流**/
                int faLong = 1024*100;
                byte[] bytes = new byte[faLong];
                int len = 0;

                //一开始检测其网上的文件大小和本地的文件大小是否一样，如果一样则直接成功，如果不一样则删除
                double fileLens = (double)FilesUtils.getFileSize(file);
                if (totalRead == 0 && fileLens == total){
                    progress = 100;
                    RxBus.getDefault().send(new DownloadBean((int)total, (int)total, (int)progress,true));
                }else{
                    if (totalRead == 0){
                        deleteFile(mApkNameVersion,1,"");
                    }
                    fos = new FileOutputStream(file,true);
                    //初始化流量
                    double min1 = totalRead/1024/1024 ;
                    double max1 =  total/1024/1024 ;
                    proInt =(int) ((min1 / max1) * 100);

                    while ((len = is.read(bytes)) != -1) {
                        if (isBreakDown){
                            break;
                        }
                        fos.write(bytes, 0, len);
                        totalRead = totalRead + len;
//                    sleep(10);
                        double min = totalRead/1024/1024 ;
                        double max =  total/1024/1024 ;
                        progress = (min / max) * 100 ;
                        int progressInt = (int)progress;
                        if (progressInt!=proInt){
                            proInt = progressInt;
                            RxBus.getDefault().send(new DownloadBean((int)total, (int)totalRead,progressInt,true));
                        }
                    }
                }
                if (is != null) {
                    is.close();
                }
                if (fos!=null){
                    fos.flush();
                    fos.close();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }   catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.flush();
                        fos.close();
                    }
                    is = null;
                    fos = null;
                    if ( (int)progress<100) {
                        //用户手动退出
                        if (isBreakDown){
                            RxBus.getDefault().send(new DownloadBean(false,0,0));
                        }else{
                            //网络问题退出
                            netErrorSend(totalRead,total,totalLen);
                        }
                    }

                } catch (IOException e) {
                    RxBus.getDefault().send(new DownloadBean(false,0,0));
                    e.printStackTrace();
                }
            }
        }else {
            //失败大于5次则直接下载错误
            reDownSize = 0;
            RxBus.getDefault().send(new DownloadBean(false,0,0));
        }

    }

    public void closeDowm(){
        isBreakDown = true;
    }


    /**
     * 网络错误Send
     */
    private void netErrorSend(double totalRead, double total,long totalLen){
        if (totalLen==0){
            RxBus.getDefault().send(new DownloadBean(false,(long)totalRead,(long)total));
            return;
        }
        RxBus.getDefault().send(new DownloadBean(false,(long)totalRead,totalLen));
    }

    /**
     * 网络错误Send
     */
    private void netErrorSend2(long totalRead,  long total){
        if (totalRead==0){
            RxBus.getDefault().send(new DownloadBean(false, 0,0 ));
            return;
        }
        if (reDownSize<=10){
            reDownSize++;
            RxBus.getDefault().send(new DownloadBean(false, totalRead,total));
        }else {
            //失败大于reDownSize次则直接下载错误
            reDownSize = 0;
            RxBus.getDefault().send(new DownloadBean(false,0,0));
        }

    }

    /**
     * 下载错误
     * @param e
     */
    private void setCallbackFail(String e){
        if (callback!=null){
            callback.onFail(e);
        }
    }

    /**
     * 删除文件目录
     */
    private void deleteFile( String mApkNameVersion,int state,String e){
        try {
            String file = FilesUtils.getInstance().getAppCacheDir(mContext)+ FilesUtils.getInstance().getApkFile();
            FilesUtils.deleteDirectory(file);
            FilesUtils.makeFilePath(FilesUtils.getInstance().getAppCacheDir(mContext), FilesUtils.getInstance().apkFile(mApkNameVersion));
        }catch (Exception ex){
            RxBus.getDefault().send(new DownloadBean(false,0,0));
        }
    }
}