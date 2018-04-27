package com.iamlarry.qvideoplayer.listener;

/**
 * @author larryycliu on 2018/4/24.
 */
public interface IQVPMediaPlayerListener {
    void onPrepared();

    void onAutoCompletion();

    void onCompletion();

    void onBufferingUpdate(int percent);

    void onSeekComplete();

    void onError(int what, int extra);

    void onInfo(int what, int extra);

    void onVideoSizeChanged();

    void onBackFullscreen();

    void onVideoPause();

    void onVideoResume();

    void onVideoResume(boolean seek);
}
