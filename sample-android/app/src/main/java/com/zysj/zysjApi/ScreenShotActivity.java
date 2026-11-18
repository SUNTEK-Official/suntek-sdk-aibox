package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenShotActivity extends AppCompatActivity {
    public Button shot;
    private ZysjSystemManager mzysjSystemManager;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        mzysjSystemManager = (ZysjSystemManager)getSystemService("zysj");
        shot = (Button) findViewById(R.id.shot);
        shot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String result = mzysjSystemManager.zYShotScreen("/mnt/sdcard/Pictures","t01.png",0);
                System.out.println("===result==="+result);
                options(Integer.parseInt(result));
            }
        });
    }

    private void options(int cmd) {
        switch (cmd) {
            case -1:
                Toast.makeText(ScreenShotActivity.this, "存储路径为空", Toast.LENGTH_LONG).show();
                break;
            case -2:
                Toast.makeText(ScreenShotActivity.this, "存储文件名称为空", Toast.LENGTH_LONG).show();
                break;
            case -3:
                Toast.makeText(ScreenShotActivity.this, "屏幕参数小于0", Toast.LENGTH_LONG).show();
                break;
            case -4:
                Toast.makeText(ScreenShotActivity.this, "截图失败，文件不存在", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(ScreenShotActivity.this, "截图成功", Toast.LENGTH_LONG).show();
        }
    }
}