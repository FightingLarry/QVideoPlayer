package com.iamlarry.qvideoplayer.view;

import android.content.Context;
import android.util.AttributeSet;

import com.iamlarry.qvideoplayer.QVPVideoManager;
import com.iamlarry.qvideoplayer.video.base.IQVPVideoViewBridge;

import tv.danmaku.ijk.media.player.IjkLibLoader;

/**
 * @author larryycliu on 2018/4/24.
 * 兼容的空View，目前用于 GSYVideoManager的设置
 */

public abstract class QVPVideoPlayer extends QVPBaseVideoPlayer {

    public QVPVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public QVPVideoPlayer(Context context) {
        super(context);
    }

    public QVPVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QVPVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置自定义so包加载类，必须在setUp之前调用
     * 不然setUp时会第一次实例化GSYVideoManager
     */
    public void setIjkLibLoader(IjkLibLoader libLoader) {
        QVPVideoManager.setIjkLibLoader(libLoader);
    }

    @Override
    public IQVPVideoViewBridge getGSYVideoManager() {
        return QVPVideoManager.instance();
    }

    @Override
    protected boolean backFromFull(Context context) {
        return QVPVideoManager.backFromWindowFull(context);
    }

    @Override
    protected void releaseVideos() {
        QVPVideoManager.releaseAllVideos();
    }

//    @Override
//    protected HttpProxyCacheServer getProxy(Context context, File file) {
//        return QVPVideoManager.getProxy(context, file);
//    }

    @Override
    protected int getFullId() {
        return QVPVideoManager.FULLSCREEN_ID;
    }

    @Override
    protected int getSmallId() {
        return QVPVideoManager.SMALL_ID;
    }

}