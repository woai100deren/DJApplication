package com.live;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.alivc.live.pusher.AlivcAudioAACProfileEnum;
import com.alivc.live.pusher.AlivcFpsEnum;
import com.alivc.live.pusher.AlivcLivePushBGMListener;
import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushError;
import com.alivc.live.pusher.AlivcLivePushErrorListener;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePushNetworkListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.AlivcPreviewOrientationEnum;
import com.alivc.live.pusher.AlivcQualityModeEnum;
import com.alivc.live.pusher.AlivcResolutionEnum;
import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.live.floatwindowpermission.FloatWindowManager;

/**
 * 直播间
 */
public class LiveRoomActivity extends BaseActivity {
    public static final int CAPTURE_PERMISSION_REQUEST_CODE = 0x1123;
    public static final int OVERLAY_PERMISSION_REQUEST_CODE = 0x1124;


    private AlivcLivePusher mAlivcLivePusher;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private Context mContext;
    private SurfaceView faceSurfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        faceSurfaceView = (SurfaceView) findViewById(R.id.faceSurfaceView);

        mContext = this;

        initPushConfig();
        alivcLivePusher();
    }

    /**
     * 初始化推流配置类
     */
    private void initPushConfig(){
        //基本配置
        mAlivcLivePushConfig = new AlivcLivePushConfig();//初始化推流配置类
        mAlivcLivePushConfig.setResolution(AlivcResolutionEnum.RESOLUTION_540P);//分辨率540P，最大支持720P
        mAlivcLivePushConfig.setFps(AlivcFpsEnum.FPS_20); //建议用户使用20fps
        mAlivcLivePushConfig.setEnableBitrateControl(true); // 打开码率自适应，默认为true
        mAlivcLivePushConfig.setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT); // 默认为竖屏，可设置home键向左或向右横屏。
        mAlivcLivePushConfig.setAudioProfile(AlivcAudioAACProfileEnum.AAC_LC);//设置音频编码模式
        mAlivcLivePushConfig.setEnableBitrateControl(true);// 打开码率自适应，默认为true

        //码控配置
        //QM_RESOLUTION_FIRST 清晰度优先模式。SDK内部会对码率参数进行配置，优先保障推流视频的清晰度。
        //QM_FLUENCY_FIRST 流畅度优先模式。SDK内部会对码率参数进行配置，优先保障推流视频的流畅度。
        //QM_CUSTOM 自定义模式。SDK会根据开发者设置的码率进行配置。
        mAlivcLivePushConfig.setQualityMode(AlivcQualityModeEnum.QM_RESOLUTION_FIRST);

        //分辨率自适应配置
        mAlivcLivePushConfig.setEnableAutoResolution(true); // 打开分辨率自适应，默认为false

        //设置开启录屏权限
//        mAlivcLivePushConfig.setMediaProjectionPermissionResultData(resultData)
        AlivcLivePushConfig.setMediaProjectionPermissionResultData(null);
    }

    /**
     * 推流
     */
    private void alivcLivePusher(){
//        mAlivcLivePusher = new AlivcLivePusher();
//        mAlivcLivePusher.init(mContext, mAlivcLivePushConfig);

        if (mAlivcLivePusher == null) {
            //if(VideoRecordViewManager.permission(getApplicationContext()))
            if(FloatWindowManager.getInstance().applyFloatWindow(mContext))
            {
                startScreenCapture();
            }
        } else {
            stopPushWithoutSurface();
        }
    }

    /**
     * 开始录屏直播检测与申请
     */
    @TargetApi(21)
    private void startScreenCapture() {
        if (Build.VERSION.SDK_INT >= 21) {
            MediaProjectionManager mediaProjectionManager =
                    (MediaProjectionManager) getApplication().getSystemService(
                            Context.MEDIA_PROJECTION_SERVICE);
            this.startActivityForResult(
                    mediaProjectionManager.createScreenCaptureIntent(), CAPTURE_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "录屏需要5.0版本以上", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 开始录屏直播
     */
    private void startPushWithoutSurface(String url) {
        mAlivcLivePusher = new AlivcLivePusher();
        try {
            mAlivcLivePusher.init(getApplicationContext(), mAlivcLivePushConfig);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        mAlivcLivePusher.setLivePushInfoListener(new AlivcLivePushInfoListener() {
            @Override
            public void onPreviewStarted(AlivcLivePusher pusher) {

            }

            @Override
            public void onPreviewStoped(AlivcLivePusher pusher) {

            }

            @Override
            public void onPushStarted(AlivcLivePusher pusher) {

            }

            @Override
            public void onPushPauesed(AlivcLivePusher pusher) {

            }

            @Override
            public void onPushResumed(AlivcLivePusher pusher) {

            }

            @Override
            public void onPushStoped(AlivcLivePusher pusher) {

            }

            @Override
            public void onPushRestarted(AlivcLivePusher pusher) {

            }

            @Override
            public void onFirstFramePreviewed(AlivcLivePusher pusher) {

            }

            @Override
            public void onDropFrame(AlivcLivePusher pusher, int countBef, int countAft) {

            }

            @Override
            public void onAdjustBitRate(AlivcLivePusher pusher, int curBr, int targetBr) {

            }

            @Override
            public void onAdjustFps(AlivcLivePusher pusher, int curFps, int targetFps) {

            }
        });

        mAlivcLivePusher.startPreview(null);
        mAlivcLivePusher.startPush(url);


        /*切换前后摄像头*/
        mAlivcLivePusher.switchCamera();
        /*开启/关闭闪光灯，在前置摄像头时开启闪关灯无效*/
        mAlivcLivePusher.setFlash(true);
        /*焦距调整，即可实现采集画面的缩放功能。缩放范围为[0,getMaxZoom()]。*/
        mAlivcLivePusher.setZoom(0);
        /*设置是否自动对焦*/
        mAlivcLivePusher.setAutoFocus(true);
        /*镜像设置。镜像相关接口有两个，PushMirror推流镜像和PreviewMirror预览镜像。PushMirror设置仅对播放画面生效，PreviewMirror仅对预览画面生效，两者互不影响。*/
        mAlivcLivePusher.setPreviewMirror(false);
        mAlivcLivePusher.setPushMirror(false);

        mAlivcLivePusher.startCamera(faceSurfaceView);//开启摄像头预览

        mAlivcLivePusher.setCaptureVolume(100);//mCaptureVolume
        /*设置耳返开关。耳返功能主要应用于KTV场景。打开耳返后，插入耳机将在耳机中听到主播说话声音。关闭后，插入耳机无法听到人声。未插入耳机的情况下，耳返不起作用。*/
        mAlivcLivePusher.setBGMEarsBack(true);
    }

    /**
     * 停止录屏直播并关闭window
     */
    private void stopPushWithoutSurface() {
//        VideoRecordViewManager.removeVideoRecordCameraWindow(getApplicationContext());
//        VideoRecordViewManager.removeVideoRecordWindow(getApplicationContext());
        if (mAlivcLivePusher != null) {
            try {
                mAlivcLivePusher.stopCamera();
                mAlivcLivePusher.stopPush();
                mAlivcLivePusher.stopPreview();
                mAlivcLivePusher.destroy();
                mAlivcLivePusher.setLivePushInfoListener(null);
                mAlivcLivePusher = null;
            } catch (Exception e) {

            }
        }
    }

    /**
     * 推流的监听
     */
    private void pushListener(){
        /**
         * 设置推流错误事件
         *
         * @param errorListener 错误监听器
         */
        mAlivcLivePusher.setLivePushErrorListener(new AlivcLivePushErrorListener() {
            @Override
            public void onSystemError(AlivcLivePusher livePusher, AlivcLivePushError error) {
                if(error != null) {
                    //添加UI提示或者用户自定义的错误处理
                }
            }
            @Override
            public void onSDKError(AlivcLivePusher livePusher, AlivcLivePushError error) {
                if(error != null) {
                    //添加UI提示或者用户自定义的错误处理
                }
            }
        });

        /**
         * 设置推流通知事件
         *
         * @param infoListener 通知监听器
         */
        mAlivcLivePusher.setLivePushInfoListener(new AlivcLivePushInfoListener() {
            @Override
            public void onPreviewStarted(AlivcLivePusher pusher) {
                //预览开始通知
            }
            @Override
            public void onPreviewStoped(AlivcLivePusher pusher) {
                //预览结束通知
            }
            @Override
            public void onPushStarted(AlivcLivePusher pusher) {
                //推流开始通知
            }
            @Override
            public void onPushPauesed(AlivcLivePusher pusher) {
                //推流暂停通知
            }
            @Override
            public void onPushResumed(AlivcLivePusher pusher) {
                //推流恢复通知
            }
            @Override
            public void onPushStoped(AlivcLivePusher pusher) {
                //推流停止通知
            }
            @Override
            public void onPushRestarted(AlivcLivePusher pusher) {
                //推流重启通知
            }
            @Override
            public void onFirstFramePreviewed(AlivcLivePusher pusher) {
                //首帧渲染通知
            }
            @Override
            public void onDropFrame(AlivcLivePusher pusher, int countBef, int countAft) {
                //丢帧通知
            }
            @Override
            public void onAdjustBitRate(AlivcLivePusher pusher, int curBr, int targetBr) {
                //调整码率通知
            }
            @Override
            public void onAdjustFps(AlivcLivePusher pusher, int curFps, int targetFps) {
                //调整帧率通知
            }
        });

        /**
         * 设置网络通知事件
         *
         * @param infoListener 通知监听器
         */
        mAlivcLivePusher.setLivePushNetworkListener(new AlivcLivePushNetworkListener() {
            @Override
            public void onNetworkPoor(AlivcLivePusher pusher) {
                //网络差通知
            }
            @Override
            public void onNetworkRecovery(AlivcLivePusher pusher) {
                //网络恢复通知
            }
            @Override
            public void onReconnectStart(AlivcLivePusher pusher) {
                //重连开始通知
            }
            @Override
            public void onReconnectFail(AlivcLivePusher pusher) {
                //重连失败通知
            }
            @Override
            public void onReconnectSucceed(AlivcLivePusher pusher) {
                //重连成功通知
            }
            @Override
            public void onSendDataTimeout(AlivcLivePusher pusher) {
                //发送数据超时通知
            }
            @Override
            public void onConnectFail(AlivcLivePusher pusher) {
                //连接失败通知
            }

            @Override
            public String onPushURLAuthenticationOverdue(AlivcLivePusher alivcLivePusher) {
                return null;
            }

            @Override
            public void onSendMessage(AlivcLivePusher alivcLivePusher) {

            }
        });

        /**
         * 设置背景音乐播放通知事件
         *
         * @param pushBGMListener 通知监听器
         */
        mAlivcLivePusher.setLivePushBGMListener(new AlivcLivePushBGMListener() {
            @Override
            public void onStarted() {
                //播放开始通知
            }
            @Override
            public void onStoped() {
                //播放停止通知
            }
            @Override
            public void onPaused() {
                //播放暂停通知
            }
            @Override
            public void onResumed() {
                //播放恢复通知
            }

            /**
             * 播放进度事件
             *
             * @param progress 播放进度
             */
            @Override
            public void onProgress(final long progress, final long duration) {
            }
            @Override
            public void onCompleted () {
                //播放结束通知
            }
            @Override
            public void onDownloadTimeout () {
                //播放器超时事件，在这里处理播放器重连并且seek到播放位置
            }
            @Override
            public void onOpenFailed () {
                //流无效通知，在这里提示流不可访问
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPushWithoutSurface();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_PERMISSION_REQUEST_CODE: {
                if (resultCode == Activity.RESULT_OK) {
                    mAlivcLivePushConfig.setMediaProjectionPermissionResultData(data);
                    if (mAlivcLivePushConfig.getMediaProjectionPermissionResultData() != null) {
                        if (mAlivcLivePusher == null) {
                            startPushWithoutSurface("rtmp://video-center-bj.alivecdn.com/AppName/StreamName?vhost=youxiads.com&auth_key=1530019809-0-0-f10e159370510a07a74d0b6ce8489dbf");
                        } else {
                            stopPushWithoutSurface();
                        }
                    }
                }
            }
            break;
            default:
                break;
        }
    }
}
