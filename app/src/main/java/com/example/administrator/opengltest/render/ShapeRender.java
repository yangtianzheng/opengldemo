package com.example.administrator.opengltest.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.View;

/**
 * Created by ytz on 2018/3/5.
 */

public abstract class ShapeRender implements GLSurfaceView.Renderer{

    public int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
