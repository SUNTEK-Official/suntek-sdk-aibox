package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScreenBacklightControlActivity extends AppCompatActivity {
    public SeekBar setBackLight;
    public TextView getBackLight;
    private static final int BACKLIGHT = 0;
    private ZysjSystemManager mzysjSystemManager;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_backlight_control);
        mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
        setBackLight = (SeekBar) findViewById(R.id.setBackLight);
        getBackLight = (TextView) findViewById(R.id.getBackLight);

        setBackLight.setMax(245);
        setBackLight.setProgress(mzysjSystemManager.zYgetBackLight());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getBackLight();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            setBackLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mzysjSystemManager.zYsetBackLight(setBackLight.getProgress());
                    getBackLight.setText("当前背光值：" + setBackLight.getProgress() + "  / 245 ");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            super.handleMessage(msg);
        }
    };

    private void getBackLight() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg0 = new Message().obtain();
                    getBackLight.setText("当前背光值：" + mzysjSystemManager.zYgetBackLight() + "  / 245 ");
                    msg0.what = BACKLIGHT;
                    mHandler.sendMessage(msg0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
