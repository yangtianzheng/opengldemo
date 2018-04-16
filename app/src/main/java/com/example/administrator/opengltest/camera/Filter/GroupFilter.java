package com.example.administrator.opengltest.camera.Filter;

import android.content.res.Resources;
import android.opengl.GLES20;

import com.example.administrator.opengltest.util.MatrixUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by ytz on 2018/4/12.
 */

public class GroupFilter extends BaseFilter {

    private Queue<BaseFilter> mFilterQueue;
    private List<BaseFilter> mFilters;
    private int width=0, height=0;
    private int size=0;

    private int fTextureSize = 2;
    private int[] fFrame = new int[1];
    private int[] fRender = new int[1];
    private int[] fTexture = new int[fTextureSize];
    private int textureIndex=0;

    public GroupFilter(Resources mRes) {
        super(mRes);
        mFilters=new ArrayList<>();
        mFilterQueue=new ConcurrentLinkedQueue<>();
    }

    public void addFilter(final BaseFilter filter){
        //绘制到frameBuffer上和绘制到屏幕上的纹理坐标是不一样的
        //Android屏幕相对GL世界的纹理Y轴翻转
        MatrixUtils.flip(filter.getMatrix(),false,true);
        mFilterQueue.add(filter);
    }

    public boolean removeFilter(BaseFilter filter){
        boolean b=mFilters.remove(filter);
        if(b){
            size--;
        }
        return b;
    }

    public BaseFilter removeFilter(int index){
        BaseFilter f=mFilters.remove(index);
        if(f!=null){
            size--;
        }
        return f;
    }

    public void clearAll(){
        mFilterQueue.clear();
        mFilters.clear();
        size=0;
    }

    @Override
    protected void onCreate() {

    }

    @Override
    protected void onSizeChanged(int width, int height) {
        this.width=width;
        this.height=height;
        updateFilter();
        createFrameBuffer();
    }

    @Override
    public int getOutputTexture(){
        return size==0?getTextureId():fTexture[(textureIndex-1)%2];
    }

    private void updateFilter(){
        BaseFilter f;
        while ((f=mFilterQueue.poll())!=null){
            f.create();
            f.setSize(width,height);
            mFilters.add(f);
            size++;
        }
    }

    private void createFrameBuffer(){
        GLES20.glGenFramebuffers(1, fFrame, 0);
        GLES20.glGenRenderbuffers(1, fRender, 0);
        genTextures();
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fFrame[0]);
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, fRender[0]);
        GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, width,
                height);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                GLES20.GL_TEXTURE_2D, fTexture[0], 0);
        GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT,
                GLES20.GL_RENDERBUFFER, fRender[0]);
        unBindFrame();
    }

    private void genTextures() {
        GLES20.glGenTextures(fTextureSize, fTexture, 0);
        for (int i = 0; i < fTextureSize; i++) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fTexture[i]);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height,
                    0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        }
    }

    private void unBindFrame() {
        GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }
}
