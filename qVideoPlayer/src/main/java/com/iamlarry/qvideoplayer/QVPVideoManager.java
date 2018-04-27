package com.iamlarry.qvideoplayer;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.iamlarry.qvideoplayer.listener.IQVPMediaPlayerListener;
import com.iamlarry.qvideoplayer.utils.CommonUtil;
import com.iamlarry.qvideoplayer.view.QVPVideoPlayer;

import tv.danmaku.ijk.media.player.IjkLibLoader;

import static com.iamlarry.qvideoplayer.utils.CommonUtil.hideNavKey;


/**
 * @author larryycliu on 2018/4/24.
 * 视频管理，单例
 */

public class QVPVideoManager extends QVPVideoBaseManager {

    public static final int SMALL_ID = R.id.small_id;

    public static final int FULLSCREEN_ID = R.id.full_id;

    public static String TAG = "QVPVideoManager";

    @SuppressLint("StaticFieldLeak")
    private static QVPVideoManager videoManager;

    /***
     * @param libLoader 是否使用外部动态加载so
     * */
    private QVPVideoManager(IjkLibLoader libLoader) {
        init(libLoader);
    }

    /**
     * 单例管理器
     */
    public static synchronized QVPVideoManager instance() {
        if (videoManager == null) {
            videoManager = new QVPVideoManager(ijkLibLoader);
        }
        return videoManager;
    }

    /**
     * 同步创建一个临时管理器
     */
    public static synchronized QVPVideoManager tmpInstance(IQVPMediaPlayerListener listener) {
        QVPVideoManager qvpIdeoManager = new QVPVideoManager(ijkLibLoader);
        qvpIdeoManager.buffterPoint = videoManager.buffterPoint;
        qvpIdeoManager.optionModelList = videoManager.optionModelList;
        qvpIdeoManager.cacheFile = videoManager.cacheFile;
        qvpIdeoManager.playTag = videoManager.playTag;
        qvpIdeoManager.mMapHeadData = videoManager.mMapHeadData;
        qvpIdeoManager.currentVideoWidth = videoManager.currentVideoWidth;
        qvpIdeoManager.currentVideoHeight = videoManager.currentVideoHeight;
        qvpIdeoManager.context = videoManager.context;
        qvpIdeoManager.lastState = videoManager.lastState;
        qvpIdeoManager.playPosition = videoManager.playPosition;
        qvpIdeoManager.timeOut = videoManager.timeOut;
        qvpIdeoManager.videoType = videoManager.videoType;
        qvpIdeoManager.needMute = videoManager.needMute;
        qvpIdeoManager.needTimeOutOther = videoManager.needTimeOutOther;
        qvpIdeoManager.setListener(listener);
        return qvpIdeoManager;
    }

    /**
     * 替换管理器
     */
    public static synchronized void changeManager(QVPVideoManager gsyVideoManager) {
        videoManager = gsyVideoManager;
    }


    /**
     * 退出全屏，主要用于返回键
     *
     * @return 返回是否全屏
     */
    @SuppressWarnings("ResourceType")
    public static boolean backFromWindowFull(Context context) {
        boolean backFrom = false;
        ViewGroup vp = (ViewGroup) (CommonUtil.scanForActivity(context)).findViewById(Window.ID_ANDROID_CONTENT);
        View oldF = vp.findViewById(FULLSCREEN_ID);
        if (oldF != null) {
            backFrom = true;
            hideNavKey(context);
            if (QVPVideoManager.instance().lastListener() != null) {
                QVPVideoManager.instance().lastListener().onBackFullscreen();
            }
        }
        return backFrom;
    }

    /**
     * 页面销毁了记得调用是否所有的video
     */
    public static void releaseAllVideos() {
        if (QVPVideoManager.instance().listener() != null) {
            QVPVideoManager.instance().listener().onCompletion();
        }
        QVPVideoManager.instance().releaseMediaPlayer();
    }


    /**
     * 暂停播放
     */
    public static void onPause() {
        if (QVPVideoManager.instance().listener() != null) {
            QVPVideoManager.instance().listener().onVideoPause();
        }
    }

    /**
     * 恢复播放
     */
    public static void onResume() {
        if (QVPVideoManager.instance().listener() != null) {
            QVPVideoManager.instance().listener().onVideoResume();
        }
    }


    /**
     * 恢复暂停状态
     *
     * @param seek 是否产生seek动作,直播设置为false
     */
    public static void onResume(boolean seek) {
        if (QVPVideoManager.instance().listener() != null) {
            QVPVideoManager.instance().listener().onVideoResume(seek);
        }
    }

    /**
     * 当前是否全屏状态
     *
     * @return 当前是否全屏状态， true代表是。
     */
    @SuppressWarnings("ResourceType")
    public static boolean isFullState(Activity activity) {
        ViewGroup vp = (ViewGroup) (CommonUtil.scanForActivity(activity)).findViewById(Window.ID_ANDROID_CONTENT);
        final View full = vp.findViewById(FULLSCREEN_ID);
        QVPVideoPlayer gsyVideoPlayer = null;
        if (full != null) {
            gsyVideoPlayer = (QVPVideoPlayer) full;
        }
        return gsyVideoPlayer != null;
    }

}