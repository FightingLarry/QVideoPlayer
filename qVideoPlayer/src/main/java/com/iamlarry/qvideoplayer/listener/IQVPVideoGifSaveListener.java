package com.iamlarry.qvideoplayer.listener;

import java.io.File;

/**
 * @author larryycliu on 2018/4/24.
 * Gif图创建的监听
 */

public interface IQVPVideoGifSaveListener {

    void process(int curPosition, int total);

    void result(boolean success, File file);
}
