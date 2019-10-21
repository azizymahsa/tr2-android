package ir.trap.tractor.android.utilities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public abstract class IMEI_Device
{
    public static String getIMEI(Context appContext, Context activityContext)
    {
        @Nullable
        String IMEI = "";

        TelephonyManager mTelephony = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            return "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            {
//                if (mTelephony.getPhoneCount() == 2)
//                {
//                    IMEI = mTelephony.getImei(0);
//                } else
//                {
//                    IMEI = mTelephony.getImei();
//                }
//            }
//            else
//            {
                if (mTelephony.getPhoneCount() == 2)
                {
                    IMEI = mTelephony.getDeviceId(0);
                    if (IMEI == null || IMEI.equalsIgnoreCase(""))
                    {
                        IMEI = mTelephony.getDeviceId(1);
                    }
                }
                else
                {
                    IMEI = mTelephony.getDeviceId();
                }
//            }
        }
        else
        {
            IMEI = mTelephony.getDeviceId();
        }

        if (IMEI == null)
        {
            IMEI = "";
        }

        Logger.e("--IMEI--", IMEI + " ##");

        return IMEI;
    }

    public static String getAndroid_ID(Context mContext)
    {
        String android_id = Settings.Secure.getString(mContext.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (android_id.length() >= 16)
        {
            android_id = android_id.substring(0, 15);
        }
        Logger.e("--android_id--", android_id + " ##");

        return android_id;
    }

}
