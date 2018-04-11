package com.example.administrator.opengltest.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ytz on 2018/3/7.
 */

public class Cylinder implements GLSurfaceView.Renderer {

    private int mProgram;
    private FloatBuffer vertexBuffer;
    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];
    private int n=360;  //切割份数
    private float height=2.0f;  //圆锥高度
    private float radius=1.0f;  //圆锥底面半径
    private float[] colors={1.0f,1.0f,1.0f,1.0f};
    private int vSize;

    private String vertexShaderCode = "uniform mat4 vMatrix;\n" +
            "varying vec4 vColor;\n" +
            "attribute vec4 vPosition;\n" +
            "\n" +
            "void main(){\n" +
            "    gl_Position=vMatrix*vPosition;\n" +
            "    if(vPosition.z!=0.0){\n" +
            "        vColor=vec4(0.0,0.0,0.0,1.0);\n" +
            "    }else{\n" +
            "        vColor=vec4(0.9,0.9,0.9,1.0);\n" +
            "    }\n" +
            "}";

    private String fragmentShaderCode = "precision mediump float;\n" +
            "varying vec4 vColor;\n" +
            "\n" +
            "void main(){\n" +
            "    gl_FragColor=vColor;\n" +
            "}";

    private OvalRender ovalRenderBottom = new OvalRender();
    private OvalRender ovalRenderTop = new OvalRender();

    public int loadShader(int type, String shaderCode){
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        ArrayList<Float> pos=new ArrayList<>();
        float angDegSpan=360f/n;
        for(float i=0;i<360+angDegSpan;i+=angDegSpan){
            pos.add((float) (radius*Math.sin(i*Math.PI/180f)));
            pos.add((float)(radius*Math.cos(i*Math.PI/180f)));
            pos.add(height);
            pos.add((float) (radius*Math.sin(i*Math.PI/180f)));
            pos.add((float)(radius*Math.cos(i*Math.PI/180f)));
            pos.add(0.0f);
        }
        float[] d=new float[pos.size()];
        for (int i=0;i<d.length;i++){
            d[i]=pos.get(i);
        }
        vSize=d.length/3;
        ByteBuffer buffer=ByteBuffer.allocateDirect(d.length*4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer=buffer.asFloatBuffer();
        vertexBuffer.put(d);
        vertexBuffer.position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);
        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram, vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram, fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);

        ovalRenderBottom.onSurfaceCreated(gl, config);
        ovalRenderTop.height = height;
        ovalRenderTop.onSurfaceCreated(gl, config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        //计算宽高比
        float ratio=(float)width/height;
        //设置透视投影
        Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
        //设置相机位置
        Matrix.setLookAtM(mViewMatrix, 0, 1.0f, -10.0f, -4.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        //计算变换矩阵
        Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);

        int mMatrix=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        GLES20.glUniformMatrix4fv(mMatrix,1,false,mMVPMatrix,0);
        int mPositionHandle=GLES20.glGetAttribLocation(mProgram,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle,3,GLES20.GL_FLOAT,false,0,vertexBuffer);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,vSize);
        GLES20.glDisableVertexAttribArray(mPositionHandle);

        ovalRenderTop.setmMVPMatrix(mMVPMatrix);
        ovalRenderTop.onDrawFrame(gl);

        ovalRenderBottom.setmMVPMatrix(mMVPMatrix);
        ovalRenderBottom.onDrawFrame(gl);
    }
}
