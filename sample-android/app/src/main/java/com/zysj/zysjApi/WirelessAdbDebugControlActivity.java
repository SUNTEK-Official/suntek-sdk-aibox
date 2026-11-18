package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WirelessAdbDebugControlActivity extends Activity implements View.OnClickListener {
    public Button open_adb;
    public Button close_adb;
    private ZysjSystemManager mzysjSystemManager;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wireless_adb_debug_control);
        mzysjSystemManager = (ZysjSystemManager)getSystemService("zysj");
        open_adb = (Button)findViewById(R.id.open);
        close_adb = (Button)findViewById(R.id.close);
        open_adb.setOnClickListener(this);
        close_adb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open:
                mzysjSystemManager.zYsetAdbWiress(true);
                ToastUtil.zysjToast(WirelessAdbDebugControlActivity.this, "无线ADB打开成功");
                break;
            case R.id.close:
                mzysjSystemManager.zYsetAdbWiress(false);
                //mzysjSystemManager.ZYRebootSys();
                ToastUtil.zysjToast(WirelessAdbDebugControlActivity.this, "无线ADB关闭成功");
                break;

            default:
                break;
        }
    }
}