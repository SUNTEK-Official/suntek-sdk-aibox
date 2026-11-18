package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DeviceKernelInfoControlActivity extends AppCompatActivity {

    private ZysjSystemManager mzysjSystemManager;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
        String getDeviceKernelInfo = mzysjSystemManager.zYgetDeviceKernelInfo();
        Toast.makeText(DeviceKernelInfoControlActivity.this, "设备内核信息：" + getDeviceKernelInfo, Toast.LENGTH_LONG).show();
    }
}
