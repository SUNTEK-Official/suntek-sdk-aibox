package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduledPowerOnAndOffActivity extends AppCompatActivity {
    public EditText open_time, open_date, close_date, close_time;
    public Button sure, set_close, set_open, cancel_close, cancel_open, cancel_open_close, reboot_sys, shutdown_sys;
    private ZysjSystemManager mzysjSystemManager;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_power_on_and_off);
        mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
        open_time = (EditText) findViewById(R.id.open_sys_time);
        open_date = (EditText) findViewById(R.id.open_sys_data);
        close_date = (EditText) findViewById(R.id.close_sys_data);
        close_time = (EditText) findViewById(R.id.close_sys_time);

        set_close = (Button) findViewById(R.id.set_close);
        set_open = (Button) findViewById(R.id.set_open);
        cancel_close = (Button) findViewById(R.id.cancel_close);
        cancel_open = (Button) findViewById(R.id.cancel_open);
        cancel_open_close = (Button) findViewById(R.id.cancel_open_close);
        sure = (Button) findViewById(R.id.sure);
        reboot_sys = (Button) findViewById(R.id.reboot_sys);
        shutdown_sys = (Button) findViewById(R.id.shutdown_sys);

        reboot_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mzysjSystemManager.zYRebootSys();
            }
        });

        shutdown_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mzysjSystemManager.zYShutdownSys();
            }
        });

        set_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSysData();
                closeSysTime();
                String closeDate = close_date.getText().toString();
                String closeTime = close_time.getText().toString();
                int result = mzysjSystemManager.setTimeToRtc(1, closeDate, closeTime);
                System.out.println("========关机时间返回值========"+result);
                if (result == 0) {
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "关机设置成功", Toast.LENGTH_LONG).show();
                } else {
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
                    options(result);
                }
            }
        });
        set_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSysData();
                openSysTime();
                String openTime = open_time.getText().toString();
                String openDate = open_date.getText().toString();
                int result = mzysjSystemManager.setTimeToRtc(2, openDate, openTime);
                System.out.println("========开机时间返回值========"+result);
                if (result == 0) {
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "开机设置成功", Toast.LENGTH_LONG).show();
                } else {
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
                    options(result);
                }
            }
        });
        cancel_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_date.setText(null);
                close_time.setText(null);
                int result = mzysjSystemManager.setTimeToRtc(4, "", "");
                System.out.println("========清除关机时间返回值========"+result);
                if (result == 0) {
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "关机时间清除成功", Toast.LENGTH_LONG).show();
                } else {
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
                    options(result);
                }
            }
        });
        cancel_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_date.setText(null);
                open_time.setText(null);
                int result = mzysjSystemManager.setTimeToRtc(5, "", "");
                System.out.println("========清除开机时间返回值========"+result);
                if (result == 0) {
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "开机时间清除成功", Toast.LENGTH_LONG).show();
                } else {
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
                    options(result);
                }
            }
        });
        cancel_open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close_date.setText(null);
                close_time.setText(null);
                open_date.setText(null);
                open_time.setText(null);
                int result = mzysjSystemManager.setTimeToRtc(3, "", "");
                System.out.println("========清除开关机时间返回值========"+result);
                if (result == 0) {
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "关机时间清除成功", Toast.LENGTH_LONG).show();
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "开机时间清除成功", Toast.LENGTH_LONG).show();
                } else {
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
                    options(result);
                }
            }
        });

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String openTime = open_time.getText().toString().trim();
                String openDate = open_date.getText().toString().trim();
                String closeDate = close_date.getText().toString();
                String closeTime = close_time.getText().toString();
                // TODO Auto-generated method stub
                int resultClose = mzysjSystemManager.setTimeToRtc(1, closeDate, closeTime);
                int resultOpen = mzysjSystemManager.setTimeToRtc(2, openDate, openTime);
                if (resultOpen == 0 && resultClose == 0) {
                    Toast.makeText(ScheduledPowerOnAndOffActivity.this, "开机与关机时间设置成功", Toast.LENGTH_LONG).show();
                } else {
 // Toast.makeText(ScheduledPowerOnAndOffActivity.this, " ", Toast.LENGTH_LONG).show();
                    options(resultClose);
                    options(resultOpen);
                    System.out.println("resultClose:" + resultClose);
                    System.out.println("resultOpen:" + resultOpen);
                }
            }
        });
    }

    private void openSysData() {
        Date openSysData = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String openSysDataFormat = sdf.format(openSysData);
        open_date.setText(openSysDataFormat);
    }

    private void openSysTime() {
        Date openSysTime = new Date(new Date().getTime() + 600000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String openSysTimeFormat = sdf.format(openSysTime);
        open_time.setText(openSysTimeFormat);
    }

    private void closeSysData() {
        Date closeSysData = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String closeSysDataFormat = sdf.format(closeSysData);
        close_date.setText(closeSysDataFormat);
    }

    private void closeSysTime() {
        Date closeSysTime = new Date(new Date().getTime() + 300000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String closeSysTimeFormat = sdf.format(closeSysTime);
        close_time.setText(closeSysTimeFormat);
    }

    private void options(int cmd) {
        switch (cmd) {
            case -1:
                Toast.makeText(ScheduledPowerOnAndOffActivity.this, "参数不在1~5的范围内", Toast.LENGTH_LONG).show();
                break;
            case -2:
                Toast.makeText(ScheduledPowerOnAndOffActivity.this, "设置的日期为空或时间为空", Toast.LENGTH_LONG).show();
                break;
            case -3:
                Toast.makeText(ScheduledPowerOnAndOffActivity.this, "设置的时间小于系统当前时间", Toast.LENGTH_LONG).show();
                break;
            case -4:
                Toast.makeText(ScheduledPowerOnAndOffActivity.this, "关机时间与开机时间间隔小于3分钟", Toast.LENGTH_LONG).show();
                break;
        }
    }
}