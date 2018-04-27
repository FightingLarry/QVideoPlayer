package com.iamlarry.qvideoplayer.player;

import android.content.Context;
import android.os.Message;

import com.iamlarry.qvideoplayer.model.VideoOptionModel;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * @author larryycliu on 2018/4/24.
 * 播放器差异管理接口
 */

public interface IPlayerManager {

    IMediaPlayer getMediaPlayer();

    void initVideoPlayer(Context context, Message message, List<VideoOptionModel> optionModelList);

    void showDisplay(Message msg);

    void setSpeed(float speed, boolean soundTouch);

    void setNeedMute(boolean needMute);

    void releaseSurface();

    void release();

}
