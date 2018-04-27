package com.iamlarry.qvideoplayer.video.base;

import android.view.Surface;

import com.iamlarry.qvideoplayer.listener.IQVPMediaPlayerListener;

import java.util.Map;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * @author larryycliu on 2018/4/24.
 * Manager 与 View之间的接口
 */

public interface IQVPVideoViewBridge {

    IQVPMediaPlayerListener listener();

    IQVPMediaPlayerListener lastListener();

    void setListener(IQVPMediaPlayerListener listener);

    void setLastListener(IQVPMediaPlayerListener lastListener);

    String getPlayTag();

    void setPlayTag(String playTag);

    int getPlayPosition();

    void setPlayPosition(int playPosition);

    void prepare(final String url, final Map<String, String> mapHeadData, boolean loop, float speed);

    IMediaPlayer getMediaPlayer();

    void releaseMediaPlayer();

    void setCurrentVideoHeight(int currentVideoHeight);

    void setCurrentVideoWidth(int currentVideoWidth);

    int getCurrentVideoWidth();

    int getCurrentVideoHeight();

    void setDisplay(Surface holder);

    void releaseSurface(Surface surface);

    void setSpeed(float speed, boolean soundTouch);

    int getLastState();

    void setLastState(int lastState);

}
