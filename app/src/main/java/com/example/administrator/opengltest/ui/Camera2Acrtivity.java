package com.example.administrator.opengltest.ui;

import android.Manifest;
import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.administrator.opengltest.R;
import com.example.administrator.opengltest.camera.FrameCallback;
import com.example.administrator.opengltest.camera.TextureController;

import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.hardware.camera2.CameraDevice.TEMPLATE_PREVIEW;

/**
 * Created by ytz on 2018/4/12.
 */

public class Camera2Acrtivity extends Activity implements FrameCallback{

    private SurfaceView mSurfaceView;
    private TextureController mController;
    private Camera2Renderer mRenderer;
    private int cameraId = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        mSurfaceView = (SurfaceView)findViewById(R.id.mSurface);
    }

    @Override
    public void onFrame(byte[] bytes, long time) {

    }

     public class Camera2Renderer implements GLSurfaceView.Renderer {
        CameraDevice mDevice;
        CameraManager mCameraManager;
        private HandlerThread mThread;
        private Handler mHandler;
        private Size mPreviewSize;

        Camera2Renderer() {
            mCameraManager = (CameraManager)getSystemService(CAMERA_SERVICE);
            mThread = new HandlerThread("camera2 ");
            mThread.start();
            mHandler = new Handler(mThread.getLooper());
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            if(mDevice!=null){
                mDevice.close();
                mDevice=null;
            }
//            try {
//                CameraCharacteristics c=mCameraManager.getCameraCharacteristics(cameraId+"");
//                StreamConfigurationMap map=c.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//                Size[] sizes=map.getOutputSizes(SurfaceHolder.class);
//                mPreviewSize=sizes[0];
//                mController.setDataSize(mPreviewSize.getHeight(),mPreviewSize.getWidth());
//                mCameraManager.openCamera(cameraId + "", new CameraDevice.StateCallback() {
//                    @Override
//                    public void onOpened(CameraDevice camera) {
//                        mDevice=camera;
//                        try {
//                            Surface surface=new Surface(mController
//                                    .getTexture());
//                            final CaptureRequest.Builder builder=mDevice.createCaptureRequest
//                                    (TEMPLATE_PREVIEW);
//                            builder.addTarget(surface);
//                            mController.getTexture().setDefaultBufferSize(
//                                    mPreviewSize.getWidth(),mPreviewSize.getHeight());
//                            mDevice.createCaptureSession(Arrays.asList(surface), new
//                                    CameraCaptureSession.StateCallback() {
//                                        @Override
//                                        public void onConfigured(CameraCaptureSession session) {
//                                            try {
//                                                session.setRepeatingRequest(builder.build(), new CameraCaptureSession.CaptureCallback() {
//                                                    @Override
//                                                    public void onCaptureProgressed(CameraCaptureSession session, CaptureRequest request, CaptureResult partialResult) {
//                                                        super.onCaptureProgressed(session, request, partialResult);
//                                                    }
//
//                                                    @Override
//                                                    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
//                                                        super.onCaptureCompleted(session, request, result);
//                                                        mController.requestRender();
//                                                    }
//                                                },mHandler);
//                                            } catch (CameraAccessException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onConfigureFailed(CameraCaptureSession session) {
//
//                                        }
//                                    },mHandler);
//                        } catch (CameraAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onDisconnected(CameraDevice camera) {
//                        mDevice=null;
//                    }
//
//                    @Override
//                    public void onError(CameraDevice camera, int error) {
//
//                    }
//                }, mHandler);
//            } catch (SecurityException |CameraAccessException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void onDestroy() {
            if(mDevice!=null){
                mDevice.close();
                mDevice=null;
            }
        }
    }
}
