package com.rine.versionupdate;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.rine.versionupdate.Contract.UpdateAppContract;
import com.rine.versionupdate.Contract.UpdateAppPresenter;
import com.rine.versionupdate.Linstener.UpdateAppDownListener;
import com.rine.versionupdate.Linstener.UpdateAppListener;
import com.rine.versionupdate.widget.AlertDialogUpdate;


/**
 * App更新
 * @author rine
 */
public class UpdateApp implements UpdateAppContract.View {

    private Context mContext; /**外部导入布局**/
    /**是否自定义界面**/
    private boolean isCusLayout = false;
    private int mLayout;
    private int mStyle;
    /**Apk链接**/
    private String mApkUrl;
    /**最外层**/
    private int mLinMainId;
    /**标题**/
    private int mTvTitleId;
    /**内容**/
    private int mTvMsgId;
    /**确认**/
    private int mTvConfirmId;
    /**取消**/
    private int mTvCancleId;
    /**关闭**/
    private int mTvCloseId;
    /**进度条量**/
    private int mTvSpeedId;
    /**进度条**/
    private int mPbForcedUpdateId;
    /**额外按钮1，以防万一**/
    private int mTvExtra1Id;
    /**额外按钮2，以防万一**/
    private int mTvExtra2Id;
    /**APK名，默认为apk**/
    private String mApkName = "apk";
    /**APK名+版本号**/
    private String mApkNameVersion = "apkV";
    /**APK名标题（用于nofit），默认为apk**/
    private String mApkNameTitle = "标题";
    /**成功toast**/
    private String mToastSuccess;
    /**失败toast**/
    private String mToastFails;
    /**标题**/
    private String mTitle;
    /**内容**/
    private String mMsg;
    /**确认按钮名**/
    private String mConfirm="确认";
    /**取消按钮名**/
    private String mCancel="取消";
    /**内容是否居中**/
    private boolean isCenter = true;
    /**点击确认是否关闭dialog**/
    private boolean dialogConfirmDiss;
    /**按返回键Dialog是否退出，false不能退出**/
    private boolean dialogCancelable;
    /**是否显示在通知栏上**/
    private boolean mIsShowNofit = true;
    /**是否显示在弹窗上**/
    private boolean mIsShowPb = false;
    /**确认按钮是否显示**/
    private boolean mIsTvConfirmVisible = true;
    /**取消按钮是否显示**/
    private boolean mIsTvCancelVisible = true;
    /**关闭按钮是否显示**/
    private boolean mIsTvCloseVisible = false;
    /**内容是否显示**/
    private boolean mIsTvMsgVisible = true;
    /**包名**/
    private String mApkPackageName="";
    private int mIcLauncher = R.mipmap.ic_launcher2;
    private UpdateAppListener updateAppListener;
    private UpdateAppDownListener updateAppDownListener;
    private UpdateAppPresenter mPresenter;
    public static UpdateApp from(Context mContext , int mLayout){
        return new UpdateApp(mContext,mLayout);
    }

    public static UpdateApp from(Context mContext ){
        return new UpdateApp(mContext);
    }

    private UpdateApp(Context mContext , int mLayout){
        this.mContext = mContext;
        this.mLayout = mLayout;
    }

    private UpdateApp(Context mContext){
        if (this.mContext==null){
            this.mContext = mContext;
        }
    }

    public UpdateApp setApkInfo(String mApkName,String mApkNameVersion,String mApkNameTitle,String mApkPackageName){
        this.mApkName = mApkName;
        this.mApkNameTitle = mApkNameTitle;
        this.mApkPackageName = mApkPackageName;
        this.mApkNameVersion = mApkNameVersion;
        return this;
    }

    public UpdateApp setIsCusLayout(boolean isCusLayout){
        this.isCusLayout = isCusLayout;
        return this;
    }

    public UpdateApp setStyle(int mStyle){
        this.mStyle = mStyle;
        return this;
    }

    public UpdateApp setIcLauncher(int mIcLauncher){
        this.mIcLauncher = mIcLauncher;
        return this;
    }

    public UpdateApp setSuccessToast(String mToastSuccess){
        this.mToastSuccess = mToastSuccess;
        return this;
    }

    public UpdateApp setFailToast(String mToastFails){
        this.mToastFails = mToastFails;
        return this;
    }


    public UpdateApp setGraly(boolean isCenter){
        this.isCenter = isCenter;
        return this;
    }

    public UpdateApp setTvConfirmVisible(boolean mIsTvConfirmVisible){
        this.mIsTvConfirmVisible = mIsTvConfirmVisible;
        return this;
    }

    public UpdateApp setTvCancelVisible(boolean mIsTvCancelVisible){
        this.mIsTvCancelVisible = mIsTvCancelVisible;
        return this;
    }

    public UpdateApp setTvCloseVisible(boolean mIsTvCloseVisible){
        this.mIsTvCloseVisible = mIsTvCloseVisible;
        return this;
    }

    public UpdateApp setTvMsgVisible(boolean mIsTvMsgVisible){
        this.mIsTvMsgVisible = mIsTvMsgVisible;
        return this;
    }

    public UpdateApp setmTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public UpdateApp setmConfirm(String mConfirm) {
        this.mConfirm = mConfirm;
        return this;
    }

    public UpdateApp setmCancel(String mCancel) {
        this.mCancel = mCancel;
        return this;
    }

    public UpdateApp setmIsShowNofit(boolean mIsShowNofit) {
        this.mIsShowNofit = mIsShowNofit;
        return this;
    }

    public UpdateApp setmIsShowPb(boolean mIsShowPb) {
        this.mIsShowPb = mIsShowPb;
        return this;
    }



    public UpdateApp setmMsg(String mMsg) {
        this.mMsg = mMsg;
        return this;
    }

    /**
     * 按键监听
     * @param updateAppListener
     * @return
     */
    public UpdateApp setUpdateAppListener(UpdateAppListener updateAppListener) {
        this.updateAppListener = updateAppListener;
        return this;
    }

    /**
     * 下载监听
     * @param updateAppDownListener
     * @return
     */
    public UpdateApp setUpdateAppDownListener(UpdateAppDownListener updateAppDownListener) {
        this.updateAppDownListener = updateAppDownListener;
        return this;
    }

    public UpdateApp setLinMainId(int mLinMainId) {
        this.mLinMainId = mLinMainId;
        return this;
    }
    public UpdateApp setApkUrl(String mApkUrl){
        this.mApkUrl = mApkUrl;
        return this;
    }

    public UpdateApp setTvTitleId(int mTvTitleId) {
        this.mTvTitleId = mTvTitleId;
        return this;
    }

    public UpdateApp setTvMsgId(int mTvMsgId) {
        this.mTvMsgId = mTvMsgId;
        return this;
    }

    public UpdateApp setTvConfirmId(int mTvConfirmId) {
        this.mTvConfirmId = mTvConfirmId;
        return this;
    }

    public UpdateApp setTvCancleId(int mTvCancleId) {
        this.mTvCancleId = mTvCancleId;
        return this;
    }

    public UpdateApp setTvCloseId(int mTvCloseId) {
        this.mTvCloseId = mTvCloseId;
        return this;
    }

    public UpdateApp setTvSpeedId(int mTvSpeedId) {
        this.mTvSpeedId = mTvSpeedId;
        return this;
    }

    public UpdateApp setPbForcedUpdateId(int mPbForcedUpdateId) {
        this.mPbForcedUpdateId = mPbForcedUpdateId;
        return this;
    }

    public UpdateApp setTvExtra1Id(int mTvExtra1Id) {
        this.mTvExtra1Id = mTvExtra1Id;
        return this;
    }

    public UpdateApp setTvExtra2Id(int mTvExtra2Id) {
        this.mTvExtra2Id = mTvExtra2Id;
        return this;
    }

    public UpdateApp setDialogConfirmDiss(boolean dialogConfirmDiss) {
        this.dialogConfirmDiss = dialogConfirmDiss;
        return this;
    }


    public UpdateApp setDialogCancelable(boolean dialogCancelable){
        this.dialogCancelable = dialogCancelable;
        return this;
    }

    /**
     *  如果有显示通知栏，在退出程序前加上这个。保证退出程序后关闭通知栏
     */
    public void clear(){
        if (mPresenter==null){
            mPresenter = new UpdateAppPresenter(this);
        }
        mPresenter.clear(mContext);
    }

    public void show(){
        mPresenter = new UpdateAppPresenter(this);

        if (!isCusLayout){
            mLayout = R.layout.dialog_confirm;
            mLinMainId = R.id.lin_main_bg;
            mStyle = R.style.DialogActivityTheme;
            mTvTitleId = 0;
            mTvCancleId = R.id.dialog_confirm_cancle;
            mTvCloseId = 0;
            mTvConfirmId = R.id.dialog_confirm_sure;
            mTvMsgId = R.id.dialog_confirm_title;
        }
        final AlertDialogUpdate alertDialogUpdate  = new AlertDialogUpdate(mContext,mLayout,mPresenter);
        alertDialogUpdate.setLinMainId(mLinMainId);
        alertDialogUpdate.setTvTitleId(mTvTitleId);
        alertDialogUpdate.setTvCancelId(mTvCancleId);
        alertDialogUpdate.setTvCloseId(mTvCloseId);
        alertDialogUpdate.setTvConfirmId(mTvConfirmId);
        alertDialogUpdate.setTvExtra1Id(mTvExtra1Id);
        alertDialogUpdate.setTvExtra2Id(mTvExtra2Id);
        alertDialogUpdate.setTvMsgId(mTvMsgId);
        alertDialogUpdate.setStyle(mStyle);
        alertDialogUpdate.setTvSpeedId(mTvSpeedId);
        alertDialogUpdate.setPbForcedUpdateId(mPbForcedUpdateId);
        alertDialogUpdate.builder();
        alertDialogUpdate.setNegativeButton(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (updateAppListener!=null) {
                    updateAppListener.updateAppCancle();
                    mPresenter.clear(mContext);
                }
            }
        });
        alertDialogUpdate.setPositiveButton(dialogConfirmDiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startDown(mContext,mIsShowNofit,mIsShowPb
                        ,alertDialogUpdate,mApkUrl,mIcLauncher,mApkNameVersion,mApkNameTitle,mApkPackageName,mToastFails,mToastSuccess);
                if (updateAppListener!=null) {
                    updateAppListener.updateAppConfirm(alertDialogUpdate.getTvConfirm(),alertDialogUpdate.getTvCancel(),alertDialogUpdate.getTvClose(),alertDialogUpdate.getTvMsg());
                }
            }
        });
        alertDialogUpdate.setCloseButton(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (updateAppListener!=null) {
                    updateAppListener.updateAppClose();
                    mPresenter.clear(mContext);
                }
            }
        });
        alertDialogUpdate.setGraly(isCenter);
        alertDialogUpdate.setCancel(mCancel);
        alertDialogUpdate.setConfirm(mConfirm);
        alertDialogUpdate.setTitle(mTitle);
        alertDialogUpdate.setTvCancelVisible(mIsTvCancelVisible);
        alertDialogUpdate.setTvCloseVisible(mIsTvCloseVisible);
        alertDialogUpdate.setTvConfirmVisible(mIsTvConfirmVisible);
        alertDialogUpdate.setTvMsgVisible(mIsTvMsgVisible);
        alertDialogUpdate.setMsg(mMsg);
        alertDialogUpdate.setCancelable(dialogCancelable);
        alertDialogUpdate.show();

    }

    /**
     * 启动到应用商店app详情界面（oppo,vivo,小米,魅族都可以跳转过去）
     *
     * @param appPkg  目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public void launchAppDetail(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void toast(String mess) {
        if (updateAppListener!=null){
            updateAppListener.updateAppToast(mess);
        }
    }

    @Override
    public void downAppPrepare() {
        if (updateAppDownListener!=null){
            updateAppDownListener.updateAppDownPrepare();
        }
    }

    @Override
    public void downAppSuccess() {
        mPresenter.clear(mContext);
        if (updateAppDownListener!=null){
            updateAppDownListener.updateAppDownSuccess();
        }
    }

    @Override
    public void downAppFail(String e) {
        if (updateAppDownListener!=null){
            updateAppDownListener.updateAppDownFail(e);
        }
    }

    @Override
    public void insertAppFail() {
        if (updateAppDownListener!=null){
            updateAppDownListener.updateAppInsertFail();
        }
    }

    @Override
    public void getProgress(int progress) {
        if (updateAppDownListener!=null){
            updateAppDownListener.updateAppDownGetProgress(progress);
        }
    }

    @Override
    public void getNotificationManager(NotificationManager notificationManager, int nofitDownAppCode) {
        if (updateAppDownListener!=null){
            updateAppDownListener.updateAppDowngetNotiMan(notificationManager,nofitDownAppCode);
        }
    }
}
