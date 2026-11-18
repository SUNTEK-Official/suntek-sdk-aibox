package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ZysjSystemManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SettingWifiParametersActivity extends Activity implements View.OnClickListener {
    private ZysjSystemManager mzysjSystemManager;

    private TextView wifiIP;
    private TextView wifiMAC;
    private TextView wifiNetMask;
    private TextView wifiGateWay;
    private TextView wifiDns1;
    private TextView wifiDns2;

    private Button closeWifi;
    private Button openWifi;
    private Button setWifiStatic;
    private Button setWifiDynamic;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_wifi_parameters);

        mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
        wifiIP = findViewById(R.id.wifiIP);
        wifiMAC = findViewById(R.id.wifiMAC);
        wifiNetMask = findViewById(R.id.wifiNetMask);
        wifiGateWay = findViewById(R.id.wifiGateWay);
        wifiDns1 = findViewById(R.id.wifiDns1);
        wifiDns2 = findViewById(R.id.wifiDns2);
        /*closeWifi = findViewById(R.id.closeWifi);
        openWifi = findViewById(R.id.openWifi);*/
        setWifiStatic = findViewById(R.id.setWifiStatic);
        setWifiDynamic = findViewById(R.id.setWifiDynamic);

        /*closeWifi.setOnClickListener(this);
        openWifi.setOnClickListener(this);*/
        setWifiStatic.setOnClickListener(this);
        setWifiDynamic.setOnClickListener(this);

        checkAndShowWifiInfo();
    }

    private void checkAndShowWifiInfo() {
        ConnectivityManager conn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo manager = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!manager.isConnected()) {
            Toast.makeText(this, "请等待WIFI重连后再切换模式", Toast.LENGTH_SHORT).show();
        } else {
            getWifiInfo();
        }
    }

    private void getWifiInfo() {
        wifiIP.setText(mzysjSystemManager.zYgetWifiIp());
        wifiMAC.setText(mzysjSystemManager.zYgetWifiMacAddress());
        wifiNetMask.setText(mzysjSystemManager.zYgetWifiNetMask());
        wifiGateWay.setText(mzysjSystemManager.zYgetWifiGateway());
        wifiDns1.setText(mzysjSystemManager.zYgetWifiDns1());
        wifiDns2.setText(mzysjSystemManager.zYgetWifiDns2());
    }

    @Override
    public void onClick(View v) {
        ConnectivityManager conn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo manager = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        switch (v.getId()) {
            case R.id.setWifiStatic:
                if (!manager.isConnected()) {
                    Toast.makeText(this, "请等待WIFI重连后再切换模式", Toast.LENGTH_SHORT).show();
                    break;
                }
                Map<String, String> mapStatic = new HashMap<>();
                mapStatic.put("ip", "192.0.2.10");
                mapStatic.put("netmask", "255.255.255.0");
                mapStatic.put("gateway", "192.0.2.10");
                mapStatic.put("dns1", "192.0.2.10");
                mapStatic.put("dns2", "8.8.8.8");
                getWifiInfo();
                mzysjSystemManager.zYsetWifiParams(1, mapStatic);
                break;

            case R.id.setWifiDynamic:
                if (!manager.isConnected()) {
                    Toast.makeText(this, "请等待WIFI重连后再切换模式", Toast.LENGTH_SHORT).show();
                    break;
                }
                getWifiInfo();
                mzysjSystemManager.zYsetWifiParams(0, null);
                break;

            default:
                break;
        }
        wifiIP.postDelayed(this::checkAndShowWifiInfo, 5000);
    }
}
