package com.traap.traapapp.ui.activities.splash;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.chuck.ChuckInterceptor;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import com.traap.traapapp.BuildConfig;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getVersion.request.GetVersionRequest;
import com.traap.traapapp.apiServices.model.getVersion.response.GetVersionResponse;
import com.traap.traapapp.models.otherModels.download.Download;
import com.traap.traapapp.ui.activities.login.LoginActivity;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.activities.web.WebActivity;
import com.traap.traapapp.ui.dialogs.DialogGetPermissionRequest;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.UpdateAppDialog;
import com.traap.traapapp.ui.dialogs.UpdateDownloadDialog;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements OnServiceStatus<WebServiceClass<GetVersionResponse>>,
        UpdateAppAction
{
    private static final int REQUEST_CODE = 123;
    private String description;
    private String url;

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
        } catch (PackageManager.NameNotFoundException e)
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
            }
        } else
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
        } else
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
        } catch (NullPointerException e)
        {
//            String mMessage =  "خطای دریافت اطلاعات از سرور!" + "\n" +
//                    "لطفا پس از چند دقیقه مجددا اقدام نمایید";
//
//            showError(mMessage);
            goToActivity();
            return;
        }
        if (response.info.statusCode != 200)
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
            } else
            {
                description = response.data.getDescription();

                url = response.data.getDownloadUrl();

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
                        goToActivity();
                    }

                    @Override
                    public void onCancelClick()
                    {
                    }
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
        updateDialog = new UpdateDownloadDialog(this, this);
        updateDialog.show(getFragmentManager(), "updateDialog");
        new Handler().postDelayed(() ->
        {
            startDownload();

        }, 200);

    }

    private void startDownload()
    {
        String fileName = "Traap.apk";
        File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File outputFile = new File(dirPath, fileName);
        if (outputFile.exists())
        {
            outputFile.delete();
        }

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(70, TimeUnit.SECONDS);
        client.readTimeout(70, TimeUnit.SECONDS);
        client.writeTimeout(70, TimeUnit.SECONDS);
        client.addInterceptor(new ChuckInterceptor(SingletonService.getInstance().getContext()));

        Download download = new Download();

        AndroidNetworking.download(url, dirPath.getAbsolutePath(), fileName)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .setOkHttpClient(client.build())
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener((timeTakenInMillis, bytesSent, bytesReceived, isFromCache) ->
                {
                    double current = Math.round(bytesReceived / (Math.pow(1024, 2)));
                    download.setCurrentFileSize(((int) current));

                    EventBus.getDefault().post(download);

//                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
//                        Log.d(TAG, " bytesSent : " + bytesSent);
//                        Log.d(TAG, " bytesReceived : " + bytesReceived);
//                        Log.d(TAG, " isFromCache : " + isFromCache);
                })
                .setDownloadProgressListener((bytesDownloaded, totalBytes) ->
                {
                    int progress = (int) ((bytesDownloaded * 100) / totalBytes);
                    download.setProgress(progress);

                    int totalFileSize = (int) (totalBytes / (Math.pow(1024, 2)));
                    download.setTotalFileSize(totalFileSize);

                    EventBus.getDefault().post(download);
                })
                .startDownload(new DownloadListener()
                {
                    @Override
                    public void onDownloadComplete()
                    {
                        // do anything after completion

                        download.setProgress(100);

                        EventBus.getDefault().post(download);
                    }

                    @Override
                    public void onError(ANError error)
                    {
                        // handle error
                    }
                });


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
        } catch (ActivityNotFoundException e2)
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

    @Override
    public void showAlert(String message)
    {
        showError(message);
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
        int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (result == mGranted && result2 == mGranted && result3 == mGranted)
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        )
        {
            String message = "برای ادامه کار حتما باید مجوز دسترسی به وضعیت دستگاه صادر شود.لطفا در تنظیمات برنامه مجوز مربوطه را صادر نمایید.";

            MessageAlertDialog dialog = new MessageAlertDialog(this, "خطای مجوز دسترسی", message, true,
                    new MessageAlertDialog.OnConfirmListener()
                    {
                        @Override
                        public void onConfirmClick()
                        {
                            ActivityCompat.requestPermissions(SplashActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    REQUEST_CODE);
                        }

                        @Override
                        public void onCancelClick()
                        {
                            finish();
                        }
                    });
            dialog.show((this).getFragmentManager(), "dialog");

//            Tools.showToast(this, "برای ادامه کار حتما باید مجوز دسترسی به وضعیت دستگاه صادر شود.لطفا در تنظیمات برنامه مجوز مربوطه را صادر نمایید.");
        } else
        {
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE);
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
