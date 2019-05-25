package com.rine.versionupdate.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rine.versionupdate.Contract.UpdateAppPresenter;
import com.rine.versionupdate.R;
import com.rine.versionupdate.utils.LogUtils;

/**
 * dialog自定义
 * @version 1.0（2019/1/22）
 * @author rine
 */
public class AlertDialogUpdate {
    private Context mContext;
    private Dialog dialog;

    private int mIcLauncher;
    /**外部导入布局**/
    private int layout;
    /**最外层**/
    private LinearLayout linMain;
    private int linMainId;
    /**标题**/
    private TextView tvTitle;
    private int tvTitleId;
    /**内容**/
    private TextView tvMsg;
    private int tvMsgId;
    /**确认**/
    private TextView tvConfirm;
    private int tvConfirmId;
    /**取消**/
    private TextView tvCancel;
    private int tvCancelId;
    /**关闭**/
    private TextView tvClose;
    private int tvCloseId;
    /**进度条量**/
    private TextView tvSpeed;
    private int tvSpeedId;
    /**进度条**/
    private ProgressBar pbForcedUpdate;
    private int pbForcedUpdateId;
    /**额外按钮1，以防万一**/
    private TextView tvExtra1;
    private int tvExtra1Id;
    /**额外按钮2，以防万一**/
    private TextView tvExtra2;
    private int tvExtra2Id;
    //    private ImageView img_line;
    private Display display;
    private UpdateAppPresenter mPresenter;


    public AlertDialogUpdate(Context mContext ,int mLayout, UpdateAppPresenter mPresenter) {
        this.mContext = mContext;
        this.layout = mLayout;
        this.mPresenter = mPresenter;
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialogUpdate builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContext).inflate(
                layout, null);

        // 获取自定义Dialog布局中的控件
        linMain = (LinearLayout) view.findViewById(linMainId);
        tvTitle = (TextView) view.findViewById(tvTitleId);
        tvMsg = (TextView) view.findViewById(tvMsgId);
        tvConfirm = (TextView) view.findViewById(tvConfirmId);
        tvCancel = (TextView) view.findViewById(tvCancelId);
        pbForcedUpdate = (ProgressBar) view.findViewById(pbForcedUpdateId);
        tvClose = (TextView) view.findViewById(tvCloseId);
        tvSpeed = (TextView) view.findViewById(tvSpeedId);
        tvExtra1 = (TextView) view.findViewById(tvExtra1Id);
        tvExtra2 = (TextView) view.findViewById(tvExtra2Id);
        dialog.setCanceledOnTouchOutside(false);//点击对话框外禁止取消窗口
        dialog.setContentView(view);
        if (pbForcedUpdate!=null){
            pbForcedUpdate.setVisibility(View.GONE);
        }
        if (tvSpeed!=null){
            tvSpeed.setVisibility(View.GONE);
        }
        if (linMain!=null){
            int linM=linMain.getWidth();
            // 调整dialog背景大小
            linMain.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        return this;
    }

    public AlertDialogUpdate setStyle(int Style){
        // 定义Dialog布局和参数
        if (Style == 0){
            dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        }else{
            dialog = new Dialog(mContext, Style);
        }
        return this;
    }

    public AlertDialogUpdate setGraly(boolean isCenter){
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //此处相当于布局文件中的Android:layout_gravity属性
        if (isCenter){
            param.gravity = Gravity.CENTER;
        }else{
            param.gravity = Gravity.LEFT;
        }
        if (tvMsg!=null){
            tvMsg.setLayoutParams(param);
        }
        return this;
    }


    /**
     * 取消键
     * @param cancel
     * @return
     */
    public AlertDialogUpdate setCancel(String cancel) {
        if (tvCancel!=null){
            if ("".equals(cancel)) {
                tvCancel.setText(mContext.getResources().getString(R.string.alertDialogCancle));
            } else {
                tvCancel.setText(cancel);
            }
        }
        return this;
    }

    /**
     * 确认键
     * @param confirm
     * @return
     */
    public AlertDialogUpdate setConfirm(String confirm) {
        if (tvConfirm!=null){
            if ("".equals(confirm)) {
                tvConfirm.setText(mContext.getResources().getString(R.string.alertDialogConfirm));
            } else {
                tvConfirm.setText(confirm);
            }
        }
        return this;
    }

    /**
     * 设置标题
     * @param title
     * @return
     */
    public AlertDialogUpdate setTitle(String title) {
        if (tvTitle!=null){
            if ("".equals(title)) {
                tvTitle.setText(mContext.getResources().getString(R.string.alertDialogTitle));
            } else {
                tvTitle.setText(title);
            }
        }
        return this;
    }


    /**
     * 设置内容
     * @param msg
     * @return
     */
    public AlertDialogUpdate setMsg(String msg) {
        if (tvMsg!=null){
            if ("".equals(msg)) {
                tvMsg.setText(mContext.getResources().getString(R.string.alertDialogContent));
            } else {
                tvMsg.setText(msg);
            }
        }
        return this;
    }




    /**
     * 确认是否点击确认关闭dialog
     * @param cancel
     * @return
     */
    public AlertDialogUpdate setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 确认
     * @param isDismiss
     * @param listener
     * @return
     */
    public AlertDialogUpdate setPositiveButton( final boolean isDismiss,final View.OnClickListener listener ) {
        if (tvConfirm!=null){
            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    if (isDismiss){
//                        dismiss();
                        dialog.dismiss();
                    }
                }
            });
        }
        return this;
    }

    /**
     * 取消
     * @param listener
     * @return
     */
    public AlertDialogUpdate setNegativeButton(final View.OnClickListener listener) {
        if (tvCancel!=null){
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 关闭
     * @param listener
     * @return
     */
    public AlertDialogUpdate setCloseButton(final View.OnClickListener listener) {
        if (tvClose!=null){
            tvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view);
                    dismiss();
                }
            });
        }
        return this;
    }


    /**
     * 确认隐藏或显示
     * @param mIsTvConfirmVisible
     * @return
     */
    public AlertDialogUpdate setTvConfirmVisible(boolean mIsTvConfirmVisible){
        if (tvConfirm!=null){
            if (mIsTvConfirmVisible){
                tvConfirm.setVisibility(View.VISIBLE);
            }else{
                tvConfirm.setVisibility(View.GONE);
            }
        }
        return this;
    }


    /**
     * 取消隐藏或显示
     * @param mIsTvCancelVisible
     * @return
     */
    public AlertDialogUpdate setTvCancelVisible(boolean mIsTvCancelVisible){
        LogUtils.getInstance().Logi("显示或隐藏取消键");
        if (tvCancel!=null){
            if (mIsTvCancelVisible){
                tvCancel.setVisibility(View.VISIBLE);
            }else{
                tvCancel.setVisibility(View.GONE);
            }
        }
        return this;
    }


    /**
     * 关闭隐藏或显示
     * @param mIsTvCloseVisible
     * @return
     */
    public AlertDialogUpdate setTvCloseVisible(boolean mIsTvCloseVisible){
        if (tvClose!=null){
            if (mIsTvCloseVisible){
                tvClose.setVisibility(View.VISIBLE);
            }else{
                tvClose.setVisibility(View.GONE);
            }
        }
        return this;
    }



    /**
     * 关闭隐藏或显示
     * @param mIsTvMsgVisible
     * @return
     */
    public AlertDialogUpdate setTvMsgVisible(boolean mIsTvMsgVisible){
        if (tvMsg!=null){
            if (mIsTvMsgVisible){
                tvMsg.setVisibility(View.VISIBLE);
            }else{
                tvMsg.setVisibility(View.GONE);
            }
        }
        return this;
    }



    /**
     * 进度条
     * @param percent 百分比
     * @param speed 进度
     * @param max 最大容量
     * @return
     */
    public AlertDialogUpdate setPbUpdate(int percent,String speed,String max){
        if (pbForcedUpdate==null){
            return this;
        }
        if (tvSpeed==null){
            return this;
        }
        pbForcedUpdate.setVisibility(View.VISIBLE);
        tvSpeed.setVisibility(View.VISIBLE);
        pbForcedUpdate.setProgress(percent);
        tvSpeed.setText(speed + " / " + max);

        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        mPresenter.clear(mContext);
        dialog.dismiss();
    }


    public TextView getTvConfirm() {
        return tvConfirm;
    }

    public TextView getTvMsg() {
        return tvMsg;
    }

    public TextView getTvClose() {
        return tvClose;
    }

    public TextView getTvCancel() {
        return tvCancel;
    }

    public int getLinMainId() {
        return linMainId;
    }

    public void setLinMainId(int linMainId) {
        this.linMainId = linMainId;
    }

    public int getTvTitleId() {
        return tvTitleId;
    }

    public void setTvTitleId(int tvTitleId) {
        this.tvTitleId = tvTitleId;
    }

    public int getTvMsgId() {
        return tvMsgId;
    }

    public void setTvMsgId(int tvMsgId) {
        this.tvMsgId = tvMsgId;
    }

    public int getTvConfirmId() {
        return tvConfirmId;
    }

    public void setTvConfirmId(int tvConfirmId) {
        this.tvConfirmId = tvConfirmId;
    }

    public int getTvCancelId() {
        return tvCancelId;
    }

    public void setTvCancelId(int tvCancelId) {
        this.tvCancelId = tvCancelId;
    }

    public int getTvCloseId() {
        return tvCloseId;
    }

    public void setTvCloseId(int tvCloseId) {
        this.tvCloseId = tvCloseId;
    }

    public int getTvSpeedId() {
        return tvSpeedId;
    }

    public void setTvSpeedId(int tvSpeedId) {
        this.tvSpeedId = tvSpeedId;
    }

    public int getPbForcedUpdateId() {
        return pbForcedUpdateId;
    }

    public void setPbForcedUpdateId(int pbForcedUpdateId) {
        this.pbForcedUpdateId = pbForcedUpdateId;
    }

    public int getTvExtra1Id() {
        return tvExtra1Id;
    }

    public void setTvExtra1Id(int tvExtra1Id) {
        this.tvExtra1Id = tvExtra1Id;
    }

    public int getTvExtra2Id() {
        return tvExtra2Id;
    }

    public void setTvExtra2Id(int tvExtra2Id) {
        this.tvExtra2Id = tvExtra2Id;
    }
}

