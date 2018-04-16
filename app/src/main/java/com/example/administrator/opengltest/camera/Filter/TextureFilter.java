package com.example.administrator.opengltest.camera.Filter;

import android.content.res.Resources;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;

import com.example.administrator.opengltest.util.EasyGlUtils;

import java.nio.ByteBuffer;

/**
 * Created by ytz on 2018/4/12.
 */

public class TextureFilter extends BaseFilter {

    private CameraFilter mFilter;
    private int width=0;
    private int height=0;

    private int[] fFrame = new int[1];
    private int[] fTexture = new int[1];
    private int[] mCameraTexture=new int[1];

    private SurfaceTexture mSurfaceTexture;
    private float[] mCoordOM=new float[16];

    private ByteBuffer tBuffer;

    public TextureFilter(Resources mRes) {
        super(mRes);
    }

    public void setCoordMatrix(float[] matrix){
        mFilter.setCoordMatrix(matrix);
    }

    public SurfaceTexture getTexture(){
        return mSurfaceTexture;
    }

    @Override
    public void setFlag(int flag) {
        mFilter.setFlag(flag);
    }

    @Override
    public void setMatrix(float[] matrix) {
        mFilter.setMatrix(matrix);
    }

    @Override
    public int getOutputTexture() {
        return fTexture[0];
    }

    @Override
    protected void onCreate() {
        mFilter.create();
        createOesTexture();
        mSurfaceTexture=new SurfaceTexture(mCameraTexture[0]);
    }

    @Override
    protected void onSizeChanged(int width, int height) {
        mFilter.setSize(width, height);
        if(this.width!=width||this.height!=height){
            deleteFrameBuffer();
            GLES20.glGenFramebuffers(1, fFrame, 0);
            EasyGlUtils.genTexturesWithParameter(1, fTexture,0,GLES20.GL_RGBA,width,height);
        }
    }

    private void deleteFrameBuffer() {
        GLES20.glDeleteFramebuffers(1, fFrame, 0);
        GLES20.glDeleteTextures(1, fTexture,0);
    }

    private void createOesTexture(){
        GLES20.glGenTextures(1,mCameraTexture,0);
    }
}
