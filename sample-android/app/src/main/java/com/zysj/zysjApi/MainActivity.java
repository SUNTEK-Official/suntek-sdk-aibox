package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zysj.zysjApi.customization.CustomizationActivity;

import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button systemStatusbarControl;
    private Button screenDirectionControl;
    private Button screenBacklightControl;
    private Button scheduledPowerOnAndOff;
    private Button screenShot;
    private Button restoreFactorySettings;
    private Button userGpioControl;
    private Button settingEthernetParameters;
    private Button settingWifiParameters;
    private Button wirelessAdbDebugControl;
    private Button setSystemTime;
    private Button deviceKernelInfo;
    private Button btn_mcu;
    private Button customization;
    private ZysjSystemManager mzysjSystemManager;
    private ScheduledThreadPoolExecutor intervalExecutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String appVersion = getResources().getString(R.string.app_version);
        String title = getResources().getString(R.string.app_name);
        String space = "_";
        setTitle(title + space + appVersion + getVersion());

        systemStatusbarControl = findViewById(R.id.systemStatusbarControl);
        screenDirectionControl = findViewById(R.id.screenDirectionControl);
        screenBacklightControl = findViewById(R.id.screenBacklightControl);
        scheduledPowerOnAndOff = findViewById(R.id.scheduledPowerOnAndOff);
        screenShot = findViewById(R.id.screenShot);
        restoreFactorySettings = findViewById(R.id.restoreFactorySettings);
        userGpioControl = findViewById(R.id.userGpioControl);
        settingEthernetParameters = findViewById(R.id.settingEthernetParameters);
        settingWifiParameters = findViewById(R.id.settingWifiParameters);
        wirelessAdbDebugControl = findViewById(R.id.wirelessAdbDebugControl);
        setSystemTime = findViewById(R.id.setSystemTime);
        deviceKernelInfo = findViewById(R.id.deviceKernelInfo);
        btn_mcu = findViewById(R.id.btn_mcu);
        customization = findViewById(R.id.customization);

        systemStatusbarControl.setOnClickListener(this);
        screenDirectionControl.setOnClickListener(this);
        screenBacklightControl.setOnClickListener(this);
        scheduledPowerOnAndOff.setOnClickListener(this);
        screenShot.setOnClickListener(this);
        restoreFactorySettings.setOnClickListener(this);
        userGpioControl.setOnClickListener(this);
        settingEthernetParameters.setOnClickListener(this);
        settingWifiParameters.setOnClickListener(this);
        wirelessAdbDebugControl.setOnClickListener(this);
        setSystemTime.setOnClickListener(this);
        deviceKernelInfo.setOnClickListener(this);
        btn_mcu.setOnClickListener(this);
        customization.setOnClickListener(this);


        initWatchDog();
    }

    private void initWatchDog() {
        int timeout = mzysjSystemManager.getWatchDogTimeout();
        int status = mzysjSystemManager.getWatchDogStatus();
        Log.e("Main","setWatchDogConfigToCh34--->" + (status == 1 ? "开" : "关") + "  timeout :" + timeout + "秒");
        if (status != 1){
            int result = mzysjSystemManager.setWatchDogConfigToCh34(180, true);
            Log.e("Main","setWatchDogConfigToCh34 result--->"+result);
        }

        intervalExecutor = new ScheduledThreadPoolExecutor(1);
        intervalExecutor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                int feedResult = mzysjSystemManager.feedWatchDog();
                Log.e("Main","feedWatchDog result--->"+feedResult);
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.systemStatusbarControl:
                startActivity(new Intent(this, SystemStatusbarControlActivity.class));
                break;

            case R.id.screenDirectionControl:
                startActivity(new Intent(this, ScreenDirectionControlActivity.class));
                break;

            case R.id.screenBacklightControl:
                startActivity(new Intent(this, ScreenBacklightControlActivity.class));
                break;

            case R.id.scheduledPowerOnAndOff:
                startActivity(new Intent(this, ScheduledPowerOnAndOffActivity.class));
                break;

            case R.id.screenShot:
                startActivity(new Intent(this, ScreenShotActivity.class));
                break;

            case R.id.restoreFactorySettings:
                startActivity(new Intent(this, RestoreFactorySettingsActivity.class));
                break;

            case R.id.userGpioControl:
                startActivity(new Intent(this, UserGpioControlActivity.class));
                break;

            case R.id.settingEthernetParameters:
                ConnectivityManager connectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressLint("MissingPermission") NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
                startActivity(new Intent(this, SettingEthernetParametersActivity.class));
                break;

            case R.id.settingWifiParameters:
                ConnectivityManager conn = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressLint("MissingPermission") NetworkInfo manager = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                startActivity(new Intent(this, SettingWifiParametersActivity.class));
                break;

            case R.id.wirelessAdbDebugControl:
                startActivity(new Intent(this, WirelessAdbDebugControlActivity.class));
                break;

            case R.id.setSystemTime:
                startActivity(new Intent(this, SetSystemTimeActivity.class));
                break;

            case R.id.deviceKernelInfo:
                //startActivity(new Intent(this, DeviceKernelInfoControlActivity.class));
                @SuppressLint("WrongConstant") ZysjSystemManager mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
                String getDeviceKernelInfo = mzysjSystemManager.zYgetDeviceKernelInfo();
                Toast.makeText(MainActivity.this, "设备内核信息：" + getDeviceKernelInfo, Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_mcu:
                startActivity(new Intent(this, McuActivity.class));
                break;

            case R.id.customization:
                startActivity(new Intent(this, CustomizationActivity.class));
                break;
            default:
                break;
        }
    }

    private String getVersion() {
        String versionName = null;
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}