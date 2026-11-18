package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SystemStatusbarControlActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ZysjSystemManager manager;
    Button showStatus, hideStatus, getStatus, showNavigation, hideNavigation, getNavigation;

    @SuppressLint({"WrongConstant", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_statusbar_control);
        mContext = getApplication();
        manager = (ZysjSystemManager) mContext.getSystemService("zysj");
        showStatus = findViewById(R.id.showStatus);
        hideStatus = findViewById(R.id.hideStatus);
        getStatus = findViewById(R.id.getStatus);
        showNavigation = findViewById(R.id.showNavigation);
        hideNavigation = findViewById(R.id.hideNavigation);
        getNavigation = findViewById(R.id.getNavigation);

        showStatus.setOnClickListener(this);
        hideStatus.setOnClickListener(this);
        getStatus.setOnClickListener(this);
        showNavigation.setOnClickListener(this);
        hideNavigation.setOnClickListener(this);
        getNavigation.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.showStatus: {
                    int statusBar = manager.zYSystemStatusBar(0);
                    if (statusBar == 0) {
                        Toast.makeText(mContext, "启用成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "启用失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.hideStatus: {
                    int statusBar = manager.zYSystemStatusBar(1);
                    if (statusBar == 0) {
                        Toast.makeText(mContext, "禁用成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "禁用失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.getStatus: {
                    int statusBar = manager.zYSystemStatusBar(2);
                    if (statusBar == 0) {
                        Toast.makeText(mContext, "状态栏已启用", Toast.LENGTH_SHORT).show();
                    } else if (statusBar == 1) {
                        Toast.makeText(mContext, "状态栏已禁用", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "获取状态栏失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.showNavigation: {
                    int navigationBar = manager.zYSystemNavigationBar(0);
                    if (navigationBar == 0) {
                        Toast.makeText(mContext, "启用成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "启用失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.hideNavigation: {
                    int navigationBar = manager.zYSystemNavigationBar(1);
                    if (navigationBar == 0) {
                        Toast.makeText(mContext, "禁用成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "禁用失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case R.id.getNavigation: {
                    int navigationBar = manager.zYSystemNavigationBar(2);
                    if (navigationBar == 0) {
                        Toast.makeText(mContext, "导航栏已启用", Toast.LENGTH_SHORT).show();
                    } else if (navigationBar == 1) {
                        Toast.makeText(mContext, "导航栏已禁用", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "获取导航栏失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            }
        }catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), getString(R.string.interface_exception), Toast.LENGTH_SHORT).show();
        }
    }


    class ShThread extends Thread {
        @Override
        public void run() {
            super.run();

        }
    }
}