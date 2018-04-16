package com.example.administrator.opengltest.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.opengltest.R;
import com.example.administrator.opengltest.ui.view.CameraView;
import com.example.administrator.opengltest.util.PermissionUtils;

/**
 * Created by ytz on 2018/4/12.
 */

public class CarameActivity extends Activity {

    private CameraView mCameraView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("OpenGL Test");
        PermissionUtils.askPermission(this, new String[]{Manifest.permission.CAMERA, Manifest
                .permission.WRITE_EXTERNAL_STORAGE}, 10, initViewRunnable);
    }

    private Runnable initViewRunnable = new Runnable() {
        @Override
        public void run() {
            setContentView(R.layout.activity_camera);
            mCameraView = (CameraView) findViewById(R.id.mCameraView);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode == 10, grantResults, initViewRunnable,
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CarameActivity.this, "没有获得必要的权限", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCameraView.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("切换摄像头").setTitle("切换摄像头").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String name = item.getTitle().toString();
        if (name.equals("切换摄像头")) {
            mCameraView.switchCamera();
        }
        return super.onOptionsItemSelected(item);
    }
}
