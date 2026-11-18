package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Map;

public class SettingEthernetParametersActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    //public Spinner eth;
    private ZysjSystemManager mzysjSystemManager;
    private RadioGroup rg_eth;
    private RadioButton rb_eth0;
    private RadioButton rb_eth1;
    private TextView ethIP;
    private TextView ethMAC;
    private TextView ethNetMask;
    private TextView ethGateWay;
    private TextView ethDns1;
    private TextView ethDns2;

    private Button closeEth;
    private Button openEth;
    private Button setEthStatic;
    private Button setEthDynamic;
    private Button enableEth;

    private String mIfaceName = "eth0";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_ethernet_parameters);

        mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
        rg_eth = findViewById(R.id.rg_eth);
        rb_eth0 = findViewById(R.id.rb_eth0);
        rb_eth1 = findViewById(R.id.rb_eth1);
        rg_eth = findViewById(R.id.rg_eth);
        ethIP = findViewById(R.id.ethIP);
        ethMAC = findViewById(R.id.ethMAC);
        ethNetMask = findViewById(R.id.ethNetMask);
        ethGateWay = findViewById(R.id.ethGateWay);
        ethDns1 = findViewById(R.id.ethDns1);
        ethDns2 = findViewById(R.id.ethDns2);
        setEthStatic = findViewById(R.id.setEthStatic);
        setEthDynamic = findViewById(R.id.setEthDynamic);
        enableEth = findViewById(R.id.enableEth);
        setEthStatic.setOnClickListener(this);
        setEthDynamic.setOnClickListener(this);
        enableEth.setOnClickListener(this);
        rg_eth.setOnCheckedChangeListener(this);
        int ifaceState = mzysjSystemManager.zYgetEthernetIfaceState(mIfaceName);
        if (ifaceState == 0) {
            enableEth.setText("打开以太网");
        } else {
            enableEth.setText("关闭以太网");
            getEthInfo();
        }
    }

    private void getEthInfo() {
        String iface = mIfaceName;
        ethIP.setText(mzysjSystemManager.zYgetEthIp(iface));
        ethMAC.setText(mzysjSystemManager.zYgetEthMacAddress(iface)); // MAC
        ethNetMask.setText(mzysjSystemManager.zYgetEthNetMask(iface));
        ethGateWay.setText(mzysjSystemManager.zYgetEthGateway(iface));
        ethDns1.setText(mzysjSystemManager.zYgetEthDns1(iface));
        ethDns2.setText(mzysjSystemManager.zYgetEthDns2(iface));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enableEth:
                int ifaceState = mzysjSystemManager.zYgetEthernetIfaceState(mIfaceName);
                if (ifaceState == 0) {
                    mzysjSystemManager.zYsetEthernetEnabled(mIfaceName, true);
                    enableEth.setText("关闭以太网");
                } else {
                    mzysjSystemManager.zYsetEthernetEnabled(mIfaceName, false);
                    enableEth.setText("打开以太网");
                }
                enableEth.postDelayed(() -> {
                    getEthInfo();
                }, 3000);
                break;

            case R.id.setEthStatic:
                mzysjSystemManager.zYsetEthernetParams(mIfaceName, 0, null);
                Map<String,String> mapStatic = new ArrayMap<>();
                mapStatic.put("ip","192.0.2.10");
                mapStatic.put("netmask","255.255.255.0");
                mapStatic.put("gateway","192.0.2.10");
                mapStatic.put("dns1","192.0.2.10");
                mapStatic.put("dns2","8.8.8.8");
                mzysjSystemManager.zYsetEthernetParams(mIfaceName, 1, mapStatic);
                getEthInfo();
                break;

            case R.id.setEthDynamic:
                mzysjSystemManager.zYsetEthernetParams(mIfaceName, 0, null);
                enableEth.postDelayed(() -> {
                    getEthInfo();
                }, 3000);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.rb_eth0) {
            mIfaceName = "eth0";
        } else if (i == R.id.rb_eth1) {
            mIfaceName = "eth1";
        }
        int ifaceState = mzysjSystemManager.zYgetEthernetIfaceState(mIfaceName);
        if (ifaceState == 0) {
            enableEth.setText("打开以太网");
        } else {
            enableEth.setText("关闭以太网");
        }
        getEthInfo();
    }
}