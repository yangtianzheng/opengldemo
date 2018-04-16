package com.example.administrator.opengltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.opengltest.ui.CarameActivity;
import com.example.administrator.opengltest.ui.EGLBackEnvActivity;
import com.example.administrator.opengltest.ui.FGLActivity;
import com.example.administrator.opengltest.ui.OBJActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDraw;
    private Button btnCaram;
    private Button btnFBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDraw = findViewById(R.id.btn_draw);
        btnDraw.setOnClickListener(this);
        btnCaram = findViewById(R.id.btn_crama);
        btnFBO = findViewById(R.id.btn_fbo);
        btnFBO.setOnClickListener(this);
        btnCaram.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_draw:{
                Intent intent = new Intent(this, FGLActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_crama:{
                Intent intent = new Intent(this, CarameActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_fbo:{
                Intent intent = new Intent(this, EGLBackEnvActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }
}
