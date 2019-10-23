package ir.trap.tractor.android.ui.activities.splash;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.ui.activities.main.MainActivity;
import ir.trap.tractor.android.ui.dialogs.DialogGetPermissionRequest;
import ir.trap.tractor.android.utilities.Tools;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity
{

    private static final int REQUEST_CODE = 123;

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .schemaVersion(0)
//                .migration(new RealmMigrations())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
//        realm = Realm.getInstance(realmConfiguration);

        PackageInfo pInfo = null;
        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.tvVersion)).setText("نسخه " + pInfo.versionName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (setPermission())
            {
                goToActivity();
            }
        }
        else
        {
            goToActivity();
        }
    }

    private void goToActivity()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (Prefs.getString("accessToken", "").isEmpty())
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                else
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                finish();
            }
        }, 4500);

    }


    //------------------------------add permission--------------------------------
    private boolean setPermission()
    {
        if (!checkPermission())
        {
            requestPermissions();
            return false;
        } else
        {
            return true;
        }
    }

    private boolean checkPermission()
    {
        int mGranted = PackageManager.PERMISSION_GRANTED;

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (result == mGranted)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void requestPermissions()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE))
        {
            Tools.showToast(this, "برای ادامه کار حتما باید مجوز دسترسی به وضعیت دستگاه صادر شود.لطفا در تنظیمات برنامه مجوز مربوطه را صادر نمایید.");

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
            else
            {
                DialogGetPermissionRequest dialog = new DialogGetPermissionRequest(this, new DialogGetPermissionRequest.OnButtonClick()
                {
                    @Override
                    public void onPositiveButton()
                    {
                        requestPermissions();
                    }

                    @Override
                    public void onNegativeButton()
                    {
                        finish();
                    }
                });

                dialog.show(getFragmentManager(), "dialogGetPermissionRequest");
            }
            return;
        }
    }

    //------------------------------add permission--------------------------------

}
