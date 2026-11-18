package com.zysj.zysjApi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private String[] Permission = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.SET_PREFERRED_APPLICATIONS
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new View(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                requestPermission();
            } else {
                Intent intent = new Intent();
                ComponentName componentName=new ComponentName("com.android.settings","com.android.settings.Settings$AppManageExternalStorageActivity");
                intent.setComponent(componentName);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10086);
                Log.e("SplashActivity","SplashActivity....startActivityForResult...........");
            }
        }else {
            requestPermission();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("SplashActivity","SplashActivity....onDestroy...........");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10086 && resultCode == RESULT_CANCELED){
//            ProcessPhoenix.triggerRebirth(App.getInstance());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
 // Settings$AppManageExternalStorageActivity resultCode false
                if (Environment.isExternalStorageManager()) {
                    Log.e("SplashActivity","Splash onActivityResult get permission success");
                    requestPermission();
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                    Log.e("SplashActivity","Splash onActivityResult restart");
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("CheckResult")
    private void requestPermission() {
        Log.e("SplashActivity","requestPermission");
        ZysjSystemManager manager = (ZysjSystemManager)this.getSystemService("zysj");
        //manager.setDefaultLauncher(BuildConfig.APPLICATION_ID);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private boolean hasApkInstalled(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        android.content.pm.ApplicationInfo info;
        try {
            info = getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
