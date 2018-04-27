package com.iamlarry.qvideoplayer.listener;


import java.io.File;

/**
 * @author larryycliu on 2018/4/24.
 * 截屏保存结果
 */

public interface IQVPVideoShotSaveListener {
    void result(boolean success, File file);
}
