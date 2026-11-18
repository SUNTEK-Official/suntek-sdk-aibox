package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SilentinstallationActivity extends Activity{
    Button installApkPathApkName,installApkPath;
    ZysjSystemManager manager =null;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silentinstallation);
        manager = (ZysjSystemManager)getSystemService("zysj");
        installApkPathApkName = (Button)findViewById(R.id.install_apkPath_apkName);
        //installApkPath = (Button)findViewById(R.id.install_apkPath);
        installApkPathApkName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int result = manager.zYSilentInstallation("mnt/sdcard/","test.apk");
                //Log.d("lbb", "onClick: result-->"+result);
            }
        });
        /*installApkPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = manager.slientInstall("mnt/sdcard/");
                Log.d("lbb", "onClick: result-->"+result);
            }
        });*/
    }
}