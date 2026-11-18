package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenDirectionControlActivity extends AppCompatActivity implements View.OnClickListener {

    public Button rotation;
    public Button open;
    private ZysjSystemManager mzysjSystemManager;
    private int i=0;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_direction_control);
        mzysjSystemManager = (ZysjSystemManager)getSystemService("zysj");
        rotation = (Button)findViewById(R.id.rotation);
        open = (Button)findViewById(R.id.bt_open);
        rotation.setOnClickListener(this);
        open.setOnClickListener(this);
    }
    int rotate = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rotation:
                int rotation = mzysjSystemManager.zYgetScreenDirection();
                if (rotation==3){
                    mzysjSystemManager.zYsetScreenDirection(0);
                }else{
                    mzysjSystemManager.zYsetScreenDirection(rotation+1);
                }
                break;
            case R.id.bt_open:
                int rotate = mzysjSystemManager.zYgetScreenDirection();

                Toast.makeText(ScreenDirectionControlActivity.this, rotate+"", Toast.LENGTH_LONG).show();
                //ToastUtil.zysjToast(ScreenDirectionControlActivity.this, (rotate++)+"");
                break;
        }
    }
}