package com.example.administrator.opengltest.render;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.view.View;

import com.example.administrator.opengltest.Filter.AFilter;
import com.example.administrator.opengltest.Filter.ColorFilter;
import com.example.administrator.opengltest.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ytz on 2018/4/11.
 */

public class SGLRender implements GLSurfaceView.Renderer {

    private AFilter mFilter;
    private Bitmap bitmap;
    private int width,height;
    private EGLConfig config;

    public SGLRender(View mView){
        mFilter=new ColorFilter(mView.getContext(), ColorFilter.Filter.MAGN);
        bitmap = BitmapFactory.decodeResource(mView.getContext().getResources(), R.drawable.test);
        mFilter.setBitmap(bitmap);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.config=config;
        mFilter.onSurfaceCreated(gl, config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.width=width;
        this.height=height;
        mFilter.onSurfaceChanged(gl, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.onDrawFrame(gl);
    }
}
