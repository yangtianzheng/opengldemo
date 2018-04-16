package com.example.administrator.opengltest.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.opengltest.R;
import com.example.administrator.opengltest.camera.Filter.ObjFilter;
import com.example.administrator.opengltest.camera.Obj3D;
import com.example.administrator.opengltest.render.FBORender;
import com.example.administrator.opengltest.util.Gl2Utils;
import com.example.administrator.opengltest.util.ObjReader;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ytz on 2018/4/16.
 */

public class OBJActivity  extends AppCompatActivity{

    private ObjFilter mRender;
    private ImageView mImage;
    private GLSurfaceView mGLView;

    private Obj3D obj;

    private int mBmpWidth,mBmpHeight;
    private String mImgPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbo);
        mGLView= (GLSurfaceView)findViewById(R.id.mGLView);
        mGLView.setEGLContextClientVersion(2);
        mRender=new ObjFilter(getResources());
        obj=new Obj3D();
        try {
            ObjReader.read(getAssets().open("3dres/hat.obj"),obj);
            mRender.setObj3D(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mGLView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                mRender.create();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                mRender.onSizeChanged(width, height);
                float[] matrix= Gl2Utils.getOriginalMatrix();
                Matrix.scaleM(matrix,0,0.2f,0.2f*width/height,0.2f);
                mRender.setMatrix(matrix);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                Matrix.rotateM(mRender.getMatrix(),0,0.3f,0,1,0);
                mRender.draw();
            }
        });
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
