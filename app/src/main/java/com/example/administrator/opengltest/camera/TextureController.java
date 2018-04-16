package com.example.administrator.opengltest.camera;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.example.administrator.opengltest.ui.Camera2Acrtivity;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ytz on 2018/4/12.
 */

public class TextureController implements GLSurfaceView.Renderer{

    private Object surface;

    private GLView mGLView;
    private Context mContext;

    private Camera2Acrtivity.Camera2Renderer mRenderer;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

    private class GLView extends GLSurfaceView{

        public GLView(Context context) {
            super(context);
            init();
        }

        private void init(){
            getHolder().addCallback(null);
            setEGLWindowSurfaceFactory(new EGLWindowSurfaceFactory() {
                @Override
                public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
                    return egl.eglCreateWindowSurface(display,config,surface,null);
                }

                @Override
                public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
                    egl.eglDestroySurface(display, surface);
                }
            });
            setEGLContextClientVersion(2);
            setRenderer(TextureController.this);
            setRenderMode(RENDERMODE_WHEN_DIRTY);
            setPreserveEGLContextOnPause(true);
        }

        public void attachedToWindow(){
            super.onAttachedToWindow();
        }

        public void detachedFromWindow(){
            super.onDetachedFromWindow();
        }
    }
}
