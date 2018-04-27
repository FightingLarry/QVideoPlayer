package com.iamlarry.qvideoplayer;

import android.content.res.Configuration;
import android.view.View;

import com.iamlarry.qvideoplayer.builder.QVPVideoOptionBuilder;
import com.iamlarry.qvideoplayer.listener.QVPSampleCallBackI;
import com.iamlarry.qvideoplayer.utils.OrientationUtils;
import com.iamlarry.qvideoplayer.video.QVPADVideoPlayer;
import com.iamlarry.qvideoplayer.view.QVPBaseVideoPlayer;
import com.iamlarry.qvideoplayer.view.QVPVideoPlayer;
import com.iamlarry.qvideoplayer.view.QVPVideoView;

/**
 * @author larryycliu on 2018/4/24.
 * 详情AD模式播放页面基础类
 */
public abstract class QVPBaseADActivityDetail<T extends QVPBaseVideoPlayer, R extends QVPADVideoPlayer> extends QVPBaseActivityDetail<T> {

    protected OrientationUtils mADOrientationUtils;

    @Override
    public void initVideo() {
        super.initVideo();
        //外部辅助的旋转，帮助全屏
        mADOrientationUtils = new OrientationUtils(this, getGSYADVideoPlayer());
        //初始化不打开外部的旋转
        mADOrientationUtils.setEnable(false);
        if (getGSYADVideoPlayer().getFullscreenButton() != null) {
            getGSYADVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //直接横屏
                    showADFull();
                    clickForFullScreen();
                }
            });
        }
    }

    /**
     * 选择builder模式
     */
    @Override
    public void initVideoBuilderMode() {
        super.initVideoBuilderMode();
        getGSYADVideoOptionBuilder()
                .setVideoAllCallBack(new QVPSampleCallBackI() {

                    @Override
                    public void onStartPrepared(String url, Object... objects) {
                        super.onStartPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        mADOrientationUtils.setEnable(getDetailOrientationRotateAuto());
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        //广告结束，释放
                        getGSYADVideoPlayer().getCurrentPlayer().release();
                        getGSYADVideoPlayer().onVideoReset();
                        getGSYADVideoPlayer().setVisibility(View.GONE);
                        //开始播放原视频，根据是否处于全屏状态判断
                        getGSYVideoPlayer().getCurrentPlayer().startAfterPrepared();
                        if (getGSYADVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
                            getGSYADVideoPlayer().removeFullWindowViewOnly();
                            if (!getGSYVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
                                showFull();
                                getGSYVideoPlayer().setSaveBeforeFullSystemUiVisibility(getGSYADVideoPlayer().getSaveBeforeFullSystemUiVisibility());
                            }
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        //退出全屏逻辑
                        if (mADOrientationUtils != null) {
                            mADOrientationUtils.backToProtVideo();
                        }
                        if (getGSYVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
                            getGSYVideoPlayer().onBackFullscreen();
                        }
                    }

                })
                .build(getGSYADVideoPlayer());
    }

    /**
     * 正常视频内容的全屏显示
     */
    @Override
    public void showFull() {
        if (orientationUtils.getIsLand() != 1) {
            //直接横屏
            orientationUtils.resolveByClick();
        }
        getGSYVideoPlayer().startWindowFullscreen(this, hideActionBarWhenFull(), hideStatusBarWhenFull());
    }

    @Override
    public void onBackPressed() {
        if (mADOrientationUtils != null) {
            mADOrientationUtils.backToProtVideo();
        }
        if (QVPVideoADManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        QVPVideoADManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        QVPVideoADManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QVPVideoADManager.releaseAllVideos();
        if (mADOrientationUtils != null)
            mADOrientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //如果旋转了就全屏
        boolean backUpIsPlay = isPlay;
        if (!isPause && getGSYADVideoPlayer().getVisibility() == View.VISIBLE) {
            if (isADStarted()) {
                isPlay = false;
                getGSYADVideoPlayer().getCurrentPlayer().onConfigurationChanged(this, newConfig, mADOrientationUtils, hideActionBarWhenFull(), hideStatusBarWhenFull());
            }
        }
        super.onConfigurationChanged(newConfig);
        isPlay = backUpIsPlay;
    }

    @Override
    public void onStartPrepared(String url, Object... objects) {
        super.onStartPrepared(url, objects);
    }

    @Override
    public void onPrepared(String url, Object... objects) {
        super.onPrepared(url, objects);
        if (isNeedAdOnStart()) {
            startAdPlay();
        }
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        QVPVideoPlayer gsyVideoPlayer = (QVPVideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }

    @Override
    public void clickForFullScreen() {

    }

    protected boolean isADStarted() {
        return getGSYADVideoPlayer().getCurrentPlayer().getCurrentState() >= 0 &&
                getGSYADVideoPlayer().getCurrentPlayer().getCurrentState() != QVPVideoView.CURRENT_STATE_NORMAL
                && getGSYADVideoPlayer().getCurrentPlayer().getCurrentState() != QVPVideoView.CURRENT_STATE_AUTO_COMPLETE;
    }

    /**
     * 显示播放广告
     */
    public void startAdPlay() {
        getGSYADVideoPlayer().setVisibility(View.VISIBLE);
        getGSYADVideoPlayer().startPlayLogic();
        if (getGSYVideoPlayer().getCurrentPlayer().isIfCurrentIsFullscreen()) {
            showADFull();
            getGSYADVideoPlayer().setSaveBeforeFullSystemUiVisibility(getGSYVideoPlayer().getSaveBeforeFullSystemUiVisibility());
        }
    }

    /**
     * 广告视频的全屏显示
     */
    public void showADFull() {
        if (mADOrientationUtils.getIsLand() != 1) {
            mADOrientationUtils.resolveByClick();
        }
        getGSYADVideoPlayer().startWindowFullscreen(QVPBaseADActivityDetail.this, hideActionBarWhenFull(), hideStatusBarWhenFull());
    }

    public abstract R getGSYADVideoPlayer();

    /**
     * 配置AD播放器
     */
    public abstract QVPVideoOptionBuilder getGSYADVideoOptionBuilder();

    /**
     * 是否播放开始广告
     */
    public abstract boolean isNeedAdOnStart();
}
