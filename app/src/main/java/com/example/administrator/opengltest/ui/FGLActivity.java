package com.example.administrator.opengltest.ui;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.opengltest.render.ConeRender;
import com.example.administrator.opengltest.render.CubeRender;
import com.example.administrator.opengltest.render.Cylinder;
import com.example.administrator.opengltest.render.OvalRender;
import com.example.administrator.opengltest.render.SqureRender;
import com.example.administrator.opengltest.render.Triangle;
import com.example.administrator.opengltest.render.TriangleColorRender;
import com.example.administrator.opengltest.render.TriangleWithCameraRender;

/**
 * Created by ytz on 2018/3/5.
 */

public class FGLActivity extends Activity {

    private GLSurfaceView glSurfaceView;
    private GLSurfaceView.Renderer renderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        renderer = new ConeRender();
        glSurfaceView.setEGLConfigChooser(8, 8, 8,8, 16,0);
        glSurfaceView.setRenderer(renderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(glSurfaceView);
    }
;
    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
