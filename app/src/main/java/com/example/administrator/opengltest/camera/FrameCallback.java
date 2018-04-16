package com.example.administrator.opengltest.camera;

/**
 * Created by ytz on 2018/4/12.
 */

public interface FrameCallback {
    void onFrame(byte[] bytes, long time);
}
