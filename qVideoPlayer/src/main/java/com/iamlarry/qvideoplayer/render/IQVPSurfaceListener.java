package com.iamlarry.qvideoplayer.render;

import android.view.Surface;

/**
 * @author larryycliu on 2018/4/24.
 * Surface 状态变化回调
 */

public interface IQVPSurfaceListener {
    void onSurfaceAvailable(Surface surface);

    void onSurfaceSizeChanged(Surface surface, int width, int height);

    boolean onSurfaceDestroyed(Surface surface);

    void onSurfaceUpdated(Surface surface);
}
