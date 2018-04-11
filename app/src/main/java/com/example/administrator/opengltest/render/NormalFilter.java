package com.example.administrator.opengltest.render;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ytz on 2018/3/8.
 */

public class NormalFilter implements GLSurfaceView.Renderer {

    private int mProgram;
    private int glHPosition;
    private int glHTexture;
    private int glHCoordinate;
    private int glHMatrix;
    private int hIsHalf;
    private int glHUxy;
    private Bitmap bitmap;

    private FloatBuffer bPos;
    private FloatBuffer bCoord;

    private int textureId;
    private boolean isHalf;

    private float uXY;
    private String vertexShader = "attribute vec4 vPosition;\n" +
            "attribute vec2 vCoordinate;\n" +
            "uniform mat4 vMatrix;\n" +
            "\n" +
            "varying vec2 aCoordinate;\n" +
            "varying vec4 aPos;\n" +
            "varying vec4 gPosition;\n" +
            "\n" +
            "void main(){\n" +
            "    gl_Position=vMatrix*vPosition;\n" +
            "    aPos=vPosition;\n" +
            "    aCoordinate=vCoordinate;\n" +
            "    gPosition=vMatrix*vPosition;\n" +
            "}";
    private String fragmentShader = "precision mediump float;\n" +
            "\n" +
            "uniform sampler2D vTexture;\n" +
            "uniform int vChangeType;\n" +
            "uniform vec3 vChangeColor;\n" +
            "uniform int vIsHalf;\n" +
            "uniform float uXY;\n" +
            "\n" +
            "varying vec4 gPosition;\n" +
            "\n" +
            "varying vec2 aCoordinate;\n" +
            "varying vec4 aPos;\n" +
            "\n" +
            "void modifyColor(vec4 color){\n" +
            "    color.r=max(min(color.r,1.0),0.0);\n" +
            "    color.g=max(min(color.g,1.0),0.0);\n" +
            "    color.b=max(min(color.b,1.0),0.0);\n" +
            "    color.a=max(min(color.a,1.0),0.0);\n" +
            "}\n" +
            "\n" +
            "void main(){\n" +
            "    vec4 nColor=texture2D(vTexture,aCoordinate);\n" +
            "    if(aPos.x>0.0||vIsHalf==0){\n" +
            "        if(vChangeType==1){\n" +
            "            float c=nColor.r*vChangeColor.r+nColor.g*vChangeColor.g+nColor.b*vChangeColor.b;\n" +
            "            gl_FragColor=vec4(c,c,c,nColor.a);\n" +
            "        }else if(vChangeType==2){\n" +
            "            vec4 deltaColor=nColor+vec4(vChangeColor,0.0);\n" +
            "            modifyColor(deltaColor);\n" +
            "            gl_FragColor=deltaColor;\n" +
            "        }else if(vChangeType==3){\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x-vChangeColor.r,aCoordinate.y-vChangeColor.r));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x-vChangeColor.r,aCoordinate.y+vChangeColor.r));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x+vChangeColor.r,aCoordinate.y-vChangeColor.r));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x+vChangeColor.r,aCoordinate.y+vChangeColor.r));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x-vChangeColor.g,aCoordinate.y-vChangeColor.g));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x-vChangeColor.g,aCoordinate.y+vChangeColor.g));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x+vChangeColor.g,aCoordinate.y-vChangeColor.g));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x+vChangeColor.g,aCoordinate.y+vChangeColor.g));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x-vChangeColor.b,aCoordinate.y-vChangeColor.b));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x-vChangeColor.b,aCoordinate.y+vChangeColor.b));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x+vChangeColor.b,aCoordinate.y-vChangeColor.b));\n" +
            "            nColor+=texture2D(vTexture,vec2(aCoordinate.x+vChangeColor.b,aCoordinate.y+vChangeColor.b));\n" +
            "            nColor/=13.0;\n" +
            "            gl_FragColor=nColor;\n" +
            "        }else if(vChangeType==4){\n" +
            "            float dis=distance(vec2(gPosition.x,gPosition.y/uXY),vec2(vChangeColor.r,vChangeColor.g));\n" +
            "            if(dis<vChangeColor.b){\n" +
            "                nColor=texture2D(vTexture,vec2(aCoordinate.x/2.0+0.25,aCoordinate.y/2.0+0.25));\n" +
            "            }\n" +
            "            gl_FragColor=nColor;\n" +
            "        }else{\n" +
            "            gl_FragColor=nColor;\n" +
            "        }\n" +
            "    }else{\n" +
            "        gl_FragColor=nColor;\n" +
            "    }\n" +
            "}";
    private float[] mViewMatrix=new float[16];
    private float[] mProjectMatrix=new float[16];
    private float[] mMVPMatrix=new float[16];

    private final float[] sPos={
            -1.0f,1.0f,
            -1.0f,-1.0f,
            1.0f,1.0f,
            1.0f,-1.0f
    };

    private final float[] sCoord={
            0.0f,0.0f,
            0.0f,1.0f,
            1.0f,0.0f,
            1.0f,1.0f,
    };



    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(1.0f,1.0f,1.0f,1.0f);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);

        ByteBuffer bb=ByteBuffer.allocateDirect(sPos.length*4);
        bb.order(ByteOrder.nativeOrder());
        bPos=bb.asFloatBuffer();
        bPos.put(sPos);
        bPos.position(0);
        ByteBuffer cc=ByteBuffer.allocateDirect(sCoord.length*4);
        cc.order(ByteOrder.nativeOrder());
        bCoord=cc.asFloatBuffer();
        bCoord.put(sCoord);
        bCoord.position(0);

        int vertex = loadShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        int fragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertex);
        GLES20.glAttachShader(mProgram, fragment);
        GLES20.glLinkProgram(mProgram);

        glHPosition=GLES20.glGetAttribLocation(mProgram,"vPosition");
        glHCoordinate=GLES20.glGetAttribLocation(mProgram,"vCoordinate");
        glHTexture=GLES20.glGetUniformLocation(mProgram,"vTexture");
        glHMatrix=GLES20.glGetUniformLocation(mProgram,"vMatrix");
        hIsHalf=GLES20.glGetUniformLocation(mProgram,"vIsHalf");
        glHUxy=GLES20.glGetUniformLocation(mProgram,"uXY");

    }

    public static int loadShader(int shaderType,String source){
        int shader= GLES20.glCreateShader(shaderType);
        if(0!=shader){
            GLES20.glShaderSource(shader,source);
            GLES20.glCompileShader(shader);
        }
        return shader;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
