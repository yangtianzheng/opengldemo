package com.example.administrator.opengltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.opengltest.ui.FGLActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDraw = findViewById(R.id.btn_Draw);
        btnDraw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Draw:
                Intent intent = new Intent(this, FGLActivity.class);
                startActivity(intent);
                break;
        }
    }
}
