package com.zysj.zysjApi;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


public class ToastUtil {

    static Toast mToast;

    public static void zysjToast(Context context, String toastMsg) {
        if(!TextUtils.isEmpty(toastMsg.trim())){}
        if (mToast == null) {
            mToast = Toast.makeText(context, toastMsg, Toast.LENGTH_LONG);
        } else {
            mToast.setText(toastMsg);
        }
        mToast.show();
    }

}
