package ir.trap.tractor.android.ui.activities.splash;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import ir.trap.tractor.android.BuildConfig;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getVersion.request.GetVersionRequest;
import ir.trap.tractor.android.apiServices.model.getVersion.response.GetVersionResponse;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.ui.activities.main.MainActivity;
import ir.trap.tractor.android.ui.activities.web.WebActivity;
import ir.trap.tractor.android.ui.dialogs.DialogGetPermissionRequest;
import ir.trap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.trap.tractor.android.ui.dialogs.UpdateAppDialog;
import ir.trap.tractor.android.ui.dialogs.UpdateDownloadDialog;
import ir.trap.tractor.android.utilities.Tools;
import ir.trap.tractor.android.utilities.Utility;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements OnServiceStatus<WebServiceClass<GetVersionResponse>>,
        UpdateAppAction
{
    private static final int REQUEST_CODE = 123;
    private String description;

    private UpdateDownloadDialog updateDialog;

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
                if (Utility.isNetworkAvailable())
                {
                    versionRequest();
                }
//                goToActivity();
            }
        }
        else
        {
            if (Utility.isNetworkAvailable())
            {
                versionRequest();
            }
//            goToActivity();
        }
    }

    private void versionRequest()
    {
        new Handler().postDelayed(() ->
        {
            GetVersionRequest request = new GetVersionRequest();
            request.setVersion(BuildConfig.VERSION_CODE);

            SingletonService.getInstance().getVersionService().getVersionService(this, request);

        }, 4500);
    }

    private void goToActivity()
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


    @Override
    public void onReady(WebServiceClass<GetVersionResponse> response)
    {
        try
        {
            if (response == null || response.info == null)
            {
//                String mMessage =  "خطای دریافت اطلاعات از سرور!" + "\n" +
//                        "لطفا پس از چند دقیقه مجددا اقدام نمایید";
//
//                showError(mMessage);
                goToActivity();
                return;
            }
        }
        catch (NullPointerException e)
        {
//            String mMessage =  "خطای دریافت اطلاعات از سرور!" + "\n" +
//                    "لطفا پس از چند دقیقه مجددا اقدام نمایید";
//
//            showError(mMessage);
            goToActivity();
            return;
        }
        if (response.info.statusCode <= 200 || response.info.statusCode > 299)
        {
//            showError(response.info.message);
            Tools.showToast(this, response.info.message, R.color.red);
            goToActivity();
        }
        else
        {
            if (BuildConfig.VERSION_CODE >= response.data.getVersion())
            {
                goToActivity();
            }
            else
            {
                description = response.data.getDescription();

//                String url = response.data.getDownloadUrl();

                UpdateAppDialog updateAppAlert = new UpdateAppDialog(this,
                        response.data.getTitle(),
                        response.data.getIsForceDownload(),
                        response.data.getDownloadUrl(),
                        response.data.getWebSite(),
                        response.data.getCafeBazaar(),
                        response.data.getGooglePlay(),
                        this
                );
                updateAppAlert.show(getFragmentManager(), "updateDialog");
            }
        }
    }

    @Override
    public void onError(String message)
    {
//        String mMessage =  "خطای دریافت اطلاعات از سرور!" + "\n" +
//                "لطفا پس از چند دقیقه مجددا اقدام نمایید";
//
//        showError(mMessage);
        goToActivity();
    }

    private void showError(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog(this, "خطا!", message, false,
                new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                finish();
            }

            @Override
            public void onCancelClick()
            { }
        });
        dialog.show((this).getFragmentManager(), "dialog");
    }


    @Override
    public void onCancel()
    {
        goToActivity();
    }

    @Override
    public void onDetailUpdate()
    {
        startActivity(new Intent(this, WebActivity.class).putExtra("description", description));
    }

    @Override
    public void onUpdate()
    {
        updateDialog = new UpdateDownloadDialog(this);
        updateDialog.show(getFragmentManager(), "updateDialog");
        new Handler().postDelayed(() ->
        {
            startDownload();

        }, 200);

    }

    private void startDownload()
    {

    }

    @Override
    public void onUpdateFromCafeBazaar()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://cafebazaar.ir/app/" + BuildConfig.APPLICATION_ID));
        startActivity(intent);
    }

    @Override
    public void onUpdateFromGooglePlay()
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
            startActivity(intent);
        }
        catch (ActivityNotFoundException e2)
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID));
            startActivity(intent);
        }
    }

    @Override
    public void onUpdateFromWebSite(String downloadUrl)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(downloadUrl));
        startActivity(intent);
    }

    //------------------------------add permission--------------------------------
    private boolean setPermission()
    {
        if (!checkPermission())
        {
            requestPermissions();
            return false;
        }
        else
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
            String message = "برای ادامه کار حتما باید مجوز دسترسی به وضعیت دستگاه صادر شود.لطفا در تنظیمات برنامه مجوز مربوطه را صادر نمایید.";

            MessageAlertDialog dialog = new MessageAlertDialog(this, "خطای مجوز دسترسی", message, true,
                    new MessageAlertDialog.OnConfirmListener()
                    {
                        @Override
                        public void onConfirmClick()
                        {
                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
                        }

                        @Override
                        public void onCancelClick()
                        {
                            finish();
                        }
                    });
            dialog.show((this).getFragmentManager(), "dialog");

//            Tools.showToast(this, "برای ادامه کار حتما باید مجوز دسترسی به وضعیت دستگاه صادر شود.لطفا در تنظیمات برنامه مجوز مربوطه را صادر نمایید.");
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
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();

                if (Utility.isNetworkAvailable())
                {
                    versionRequest();
                }
//                goToActivity();
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
