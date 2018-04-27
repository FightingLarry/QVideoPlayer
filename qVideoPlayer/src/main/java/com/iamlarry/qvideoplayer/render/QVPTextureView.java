package com.iamlarry.qvideoplayer.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.iamlarry.qvideoplayer.listener.IQVPVideoShotListener;
import com.iamlarry.qvideoplayer.listener.IQVPVideoShotSaveListener;
import com.iamlarry.qvideoplayer.utils.Debuger;
import com.iamlarry.qvideoplayer.utils.FileUtils;
import com.iamlarry.qvideoplayer.utils.MeasureHelper;

import java.io.File;

/**
 * @author larryycliu on 2018/4/24.
 * 用于显示video的，做了横屏与竖屏的匹配，还有需要rotation需求的
 */

public class QVPTextureView extends TextureView implements TextureView.SurfaceTextureListener, IQVPRenderView, MeasureHelper.MeasureFormVideoParamsListener {

    private IQVPSurfaceListener mIQVPSurfaceListener;

    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

    private MeasureHelper measureHelper;

    private Surface mSurface;

    public QVPTextureView(Context context) {
        super(context);
        init();
    }

    public QVPTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        measureHelper = new MeasureHelper(this, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHelper.prepareMeasure(widthMeasureSpec, heightMeasureSpec, (int) getRotation());
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);
        if (mIQVPSurfaceListener != null) {
            mIQVPSurfaceListener.onSurfaceAvailable(mSurface);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (mIQVPSurfaceListener != null) {
            mIQVPSurfaceListener.onSurfaceSizeChanged(mSurface, width, height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        //清空释放
        if (mIQVPSurfaceListener != null) {
            mIQVPSurfaceListener.onSurfaceDestroyed(mSurface);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //如果播放的是暂停全屏了
        if (mIQVPSurfaceListener != null) {
            mIQVPSurfaceListener.onSurfaceUpdated(mSurface);
        }
    }

    @Override
    public IQVPSurfaceListener getIGSYSurfaceListener() {
        return mIQVPSurfaceListener;
    }

    @Override
    public void setIGSYSurfaceListener(IQVPSurfaceListener surfaceListener) {
        setSurfaceTextureListener(this);
        mIQVPSurfaceListener = surfaceListener;
    }

    @Override
    public int getSizeH() {
        return getHeight();
    }

    @Override
    public int getSizeW() {
        return getWidth();
    }

    /**
     * 暂停时初始化位图
     */
    @Override
    public Bitmap initCover() {
        Bitmap bitmap = Bitmap.createBitmap(
                getSizeW(), getSizeH(), Bitmap.Config.RGB_565);
        return getBitmap(bitmap);

    }

    /**
     * 暂停时初始化位图
     */
    @Override
    public Bitmap initCoverHigh() {
        Bitmap bitmap = Bitmap.createBitmap(
                getSizeW(), getSizeH(), Bitmap.Config.ARGB_8888);
        return getBitmap(bitmap);

    }


    /**
     * 获取截图
     *
     * @param shotHigh 是否需要高清的
     */
    @Override
    public void taskShotPic(IQVPVideoShotListener IQVPVideoShotListener, boolean shotHigh) {
        if (shotHigh) {
            IQVPVideoShotListener.getBitmap(initCoverHigh());
        } else {
            IQVPVideoShotListener.getBitmap(initCover());
        }
    }

    /**
     * 保存截图
     *
     * @param high 是否需要高清的
     */
    @Override
    public void saveFrame(final File file, final boolean high, final IQVPVideoShotSaveListener IQVPVideoShotSaveListener) {
        IQVPVideoShotListener IQVPVideoShotListener = new IQVPVideoShotListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                if (bitmap == null) {
                    IQVPVideoShotSaveListener.result(false, file);
                } else {
                    FileUtils.saveBitmap(bitmap, file);
                    IQVPVideoShotSaveListener.result(true, file);
                }
            }
        };
        if (high) {
            IQVPVideoShotListener.getBitmap(initCoverHigh());
        } else {
            IQVPVideoShotListener.getBitmap(initCover());
        }

    }


    @Override
    public View getRenderView() {
        return this;
    }

    @Override
    public void onRenderResume() {
        Debuger.printfLog(getClass().getSimpleName() + " not support onRenderResume now");
    }

    @Override
    public void onRenderPause() {
        Debuger.printfLog(getClass().getSimpleName() + " not support onRenderPause now");
    }

    @Override
    public void releaseRenderAll() {
        Debuger.printfLog(getClass().getSimpleName() + " not support releaseRenderAll now");
    }

    @Override
    public void setRenderMode(int mode) {
        Debuger.printfLog(getClass().getSimpleName() + " not support setRenderMode now");
    }

    @Override
    public void setRenderTransform(Matrix transform) {
        setTransform(transform);
    }


    @Override
    public void setVideoParamsListener(MeasureHelper.MeasureFormVideoParamsListener listener) {
        mVideoParamsListener = listener;
    }

    @Override
    public int getCurrentVideoWidth() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getCurrentVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getCurrentVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getVideoSarDen();
        }
        return 0;
    }


    /**
     * 添加播放的view
     */
    public static QVPTextureView addTextureView(Context context, ViewGroup textureViewContainer, int rotate,
                                                final IQVPSurfaceListener gsySurfaceListener,
                                                final MeasureHelper.MeasureFormVideoParamsListener videoParamsListener) {
        if (textureViewContainer.getChildCount() > 0) {
            textureViewContainer.removeAllViews();
        }
        QVPTextureView QVPTextureView = new QVPTextureView(context);
        QVPTextureView.setIGSYSurfaceListener(gsySurfaceListener);
        QVPTextureView.setVideoParamsListener(videoParamsListener);
        QVPTextureView.setRotation(rotate);
        QVPRenderView.addToParent(textureViewContainer, QVPTextureView);
        return QVPTextureView;
    }
}