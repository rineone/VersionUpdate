# SuperCalendar
## ChangeLog
* 发布1.6.3 现在版本，修复点击取消不能更新的问题
* 发布1.6.0 旧版本，修复下载过程中卡顿问题
* 发布1.5.3 旧版本，修复页面问题
* 发布1.5.2 旧版本，增加是否断点更新字段 .setIsDuanDian(false)，默认为false
* 发布1.5.1 旧版本，增快下载速度
* 发布1.5.0 旧版本，增大下载阀门
* 发布1.4 旧版本，修复部分BUG
* 发布1.3.0 旧版本，增加功能
* 发布1.2.0 初始版本

## 简介
* 这是一个APP更新插件，其中包含自定义Layout更新页面和默认更新页面
## 消息介绍
1.默认更新
```java
 UpdateApp.from(mContext)
                        .setApkInfo("app","appV1.1.1.1801112","app名",getPackageName())
                        .setApkUrl(apkPath)
                        .setIsCusLayout(false)  //默认为false
                        .setGraly(true)    //默认为true
                        .setmMsg("更新内容未知哦哦哦哦哦哦哦哦")
                        .setFailToast("下载失败！")
                        .setSuccessToast("下载成功！")
                        .show();
```



 2.自定义更新及其扩展监听
 ```java
  UpdateApp.from(mContext,R.layout.view_alertdialog5)
                 .setApkInfo("app","appV1.1.1.1801112","app名",getPackageName())
                 .setApkUrl("https://image.taoerxue.com/taoerxueV2.3.7.1801112.apk")
                 .setIsCusLayout(true)
                 .setIcLauncher(R.mipmap.ic_launcher2)
                         .setLinMainId(R.id.lLayout_bg)
                         .setTvTitleId(R.id.txt_title)
                         .setTvCancleId(R.id.btn_neg)
                         .setTvConfirmId(R.id.btn_pos)
                         .setTvMsgId(R.id.txt_msg)
                         .setPbForcedUpdateId(R.id.pb_forced_update)
                         .setTvSpeedId(R.id.tv_speed)
                         .setmCancel("取消1")
                         .setmConfirm("确定1")
                         .setmIsShowNofit(true)  //默认为true
                         .setmIsShowPb(false)    //默认为false
                         .setmTitle("这是一个更新")
                         .setmMsg("更新内容")
                         .setUpdateAppDownListener(new UpdateAppDownListener() {
                             @Override
                             public void updateAppDownPrepare() {
                                 Toast.makeText(mContext,"开始下载！",Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void updateAppDownSuccess() {
 //                                Toast.makeText(mContext,"下载成功！",Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void updateAppDownFail(String e) {
                                 Toast.makeText(mContext,"下载失败！",Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void updateAppInsertFail() {

                             }

                             @Override
                             public void updateAppDownGetProgress(int progress) {
                                 //显示进度
                             }

                             @Override
                             public void updateAppDowngetNotiMan(NotificationManager notificationManager, int nofitDownAppCode) {
                                 //为了在退出APP时消除nofti
                             }
                         })
                 .setUpdateAppListener(new UpdateAppListener() {
                     @Override
                     public void updateAppToast(String mess) {
                         Toast.makeText(mContext,mess,Toast.LENGTH_SHORT).show();
                     }

                     @Override
                     public void updateAppCancle() {

                     }

                     @Override
                     public void updateAppConfirm(TextView tvConfirm, TextView tvCancel, TextView tvClose, TextView tvMsg) {

                     }

                     @Override
                     public void updateAppClose() {

                     }
                 })
                 .show();
  ```
