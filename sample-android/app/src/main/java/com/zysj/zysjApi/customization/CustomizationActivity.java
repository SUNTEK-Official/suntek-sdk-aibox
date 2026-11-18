package com.zysj.zysjApi.customization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ZysjSystemManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zysj.zysjApi.MainActivity;
import com.zysj.zysjApi.McuActivity;
import com.zysj.zysjApi.R;
import com.zysj.zysjApi.RestoreFactorySettingsActivity;
import com.zysj.zysjApi.ScheduledPowerOnAndOffActivity;
import com.zysj.zysjApi.ScreenBacklightControlActivity;
import com.zysj.zysjApi.ScreenDirectionControlActivity;
import com.zysj.zysjApi.ScreenShotActivity;
import com.zysj.zysjApi.SetSystemTimeActivity;
import com.zysj.zysjApi.SettingEthernetParametersActivity;
import com.zysj.zysjApi.SettingWifiParametersActivity;
import com.zysj.zysjApi.SystemStatusbarControlActivity;
import com.zysj.zysjApi.UserGpioControlActivity;
import com.zysj.zysjApi.WirelessAdbDebugControlActivity;

public class CustomizationActivity extends Activity implements View.OnClickListener {

    ZysjSystemManager manager;
    Context mContext;
    boolean tethering;

    private Button install_APK;
    private Button set_ethernet_tethering;
    private Button get_ethernet_tethering;
    private Button update_OTA;
    private Button get_Cpu_Usage;
    private Button get_Npu_Usage;
    private Button get_Memory_Usage;
    private Button get_Firmware_Versions;
    private Button set_SerialNo;
    private Button get_SerialNo;
    private Button set_Default_Launcher;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
        manager = (ZysjSystemManager) getSystemService("zysj");
        mContext = this;

        install_APK = findViewById(R.id.install_APK);
        set_ethernet_tethering = findViewById(R.id.set_ethernet_tethering);
        get_ethernet_tethering = findViewById(R.id.get_ethernet_tethering);
        get_Cpu_Usage = findViewById(R.id.get_Cpu_Usage);
        get_Npu_Usage = findViewById(R.id.get_Npu_Usage);
        get_Memory_Usage = findViewById(R.id.get_Memory_Usage);
        get_Firmware_Versions = findViewById(R.id.get_Firmware_Versions);
        set_SerialNo = findViewById(R.id.set_SerialNo);
        get_SerialNo = findViewById(R.id.get_SerialNo);
        set_Default_Launcher = findViewById(R.id.set_Default_Launcher);

        install_APK.setOnClickListener(this);
        set_ethernet_tethering.setOnClickListener(this);
        get_ethernet_tethering.setOnClickListener(this);
        get_Cpu_Usage.setOnClickListener(this);
        get_Npu_Usage.setOnClickListener(this);
        get_Memory_Usage.setOnClickListener(this);
        get_Firmware_Versions.setOnClickListener(this);
        set_SerialNo.setOnClickListener(this);
        get_SerialNo.setOnClickListener(this);
        set_Default_Launcher.setOnClickListener(this);

        tethering = manager.getEthernetTethering();
        if (tethering) {
            set_ethernet_tethering.setText("点击关闭以太网络共享");
        } else {
            set_ethernet_tethering.setText("点击打开以太网络共享");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.install_APK:
                showEditTextDialog(1, "输入需要安装的APK文件路径");
                break;

            case R.id.set_ethernet_tethering:
                if (tethering) {
                    manager.setEthernetTethering(false);
                    set_ethernet_tethering.setText("点击打开以太网络共享");
                    tethering = false;
                } else {
                    manager.setEthernetTethering(true);
                    set_ethernet_tethering.setText("点击关闭以太网络共享");
                    tethering = true;
                }
                break;

            case R.id.get_ethernet_tethering:
                boolean tethering = manager.getEthernetTethering();
                Toast.makeText(mContext, tethering ? "开关已开启" : "开关已关闭", Toast.LENGTH_LONG).show();
                break;

            case R.id.get_Cpu_Usage:
                String cpuUsage = manager.getCpuUsage();
                Toast.makeText(mContext, cpuUsage, Toast.LENGTH_LONG).show();
                break;

            case R.id.get_Npu_Usage:
                String npuUsage = manager.getNpuUsage();
                Toast.makeText(mContext, npuUsage, Toast.LENGTH_LONG).show();
                break;

            case R.id.get_Memory_Usage:
                String memoryUsage = manager.getMemoryUsage();
                Toast.makeText(mContext, memoryUsage, Toast.LENGTH_LONG).show();
                break;

            case R.id.get_Firmware_Versions:
                String versions = manager.getFirmwareVersions();
                Toast.makeText(mContext, versions, Toast.LENGTH_LONG).show();
                break;

            case R.id.set_SerialNo:
                showEditTextDialog(8, "输入需要写入的序列号");
                break;

            case R.id.get_SerialNo:
                String serialNo = manager.getSerialNo();
                Toast.makeText(mContext, serialNo, Toast.LENGTH_LONG).show();
                break;

            case R.id.set_Default_Launcher:
                showEditTextDialog(10, "输入需要设置成Launcher桌面的应用包名");
                break;
        }
    }


    public void showdialog(int id, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (id) {
                            case 2:

                        }
                    }
                });
        Dialog mDialog = builder.create();
        //mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mDialog.show();
    }

    Dialog mDialog = null;

    private void showEditTextDialog(int id, String message) {
        final EditText editText = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(message)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString();
                        switch (id) {
                            case 1:
                                Toast.makeText(mContext, manager.installAPK(text) == 0 ? "成功" : "失败", Toast.LENGTH_LONG).show();
                                break;
                            case 8:
                                Toast.makeText(mContext, manager.setSerialNo(text) == 0 ? "成功" : "失败", Toast.LENGTH_LONG).show();
                                break;
                            case 10:
                                manager.setDefaultLauncher(text);
                                break;
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialog.dismiss();
                        finish();
                    }
                });
        builder.setCancelable(false);
        mDialog = builder.create();
        //mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mDialog.show();
    }
}
