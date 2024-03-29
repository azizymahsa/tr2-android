package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.gastudio.downloadloadding.library.GADownloadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import com.traap.traapapp.BuildConfig;
import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.download.Download;
import com.traap.traapapp.ui.activities.splash.UpdateAppAction;
import com.traap.traapapp.utilities.Logger;

/**
 * Created by Javad.Aabdi on 3/16/2019.
 */
@SuppressLint("ValidFragment")
public class UpdateDownloadDialog extends DialogFragment
{
    private Dialog dialog;
    private Activity activity;
    private GADownloadingView mProgressBar;
    private TextView mProgressText, tvDownload;
    private boolean isDownloaded = false;
    private UpdateAppAction action;

    private String fileName = "Traap.apk";
    private File dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    private File outputFile = new File(dirPath, fileName);
//    private File outputFile = new File(dirPath + "/" + fileName);


    public UpdateDownloadDialog(Activity activity, UpdateAppAction action)
    {
        this.activity = activity;
        this.action = action;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_download);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        mProgressBar = dialog.findViewById(R.id.ga_downloading);
        mProgressText = dialog.findViewById(R.id.progress_text);
        tvDownload = dialog.findViewById(R.id.tvDownload);

        new Handler().postDelayed(() -> mProgressBar.performAnimation(), 700);

        setCancelable(false);

        mProgressBar.setOnClickListener(view ->
        {
            if (isDownloaded)
            {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.fromFile(outputFile));

                    intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    activity.startActivityForResult(intent,100);
                }
                else
                {
                    callInstallProcess();
                }
            }
        });

        EventBus.getDefault().register(this);
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK)
        {
//            callInstallProcess();
        }
    }

    private void callInstallProcess()
    {
        Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", outputFile.getAbsoluteFile());

        Logger.e("--path--", uri.getPath());
//        Intent intent = new Intent(Intent.ACTION_VIEW);
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);

//        intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        activity.startActivityForResult(intent,100);
    }


//    @Override
//    public void onClick(View v)
//    {
//        switch (v.getId())
//        {
//            case R.id.btnConfirmExit:
//
//                break;
//            case R.id.btnCancelExit:
//                break;
//        }
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setProgress(int progress)
    {
        mProgressBar.updateProgress(progress);
    }

    private void setProgressText(String progressText)
    {
        mProgressText.setText(progressText);
        tvDownload.setText(progressText);

    }

    private void onComplete()
    {
        try
        {
            mProgressBar.updateProgress(100);

            new Handler().postDelayed(() ->
            {
                mProgressBar.releaseAnimation();

                new Handler().postDelayed(() ->
                {
                    isDownloaded = true;
//                    tvDownload.setVisibility(View.GONE);

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(outputFile),
                            "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    Logger.e("-outputFile-", outputFile.getAbsolutePath());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        try
                        {
                            activity.startActivityForResult(intent,100);
                        }
                        catch (android.os.FileUriExposedException e)
                        {
                            Logger.e("--Exception--", e.getMessage().toString());
                            e.printStackTrace();
                            action.showAlert("مشکل در نصب فایل!","خطا!");
                            dismiss();
                        }
                    }
                    else
                    {
                        activity.startActivityForResult(intent,100);
                    }
                }, 1000);
            }, 2600);
        }
        catch (Exception e)
        {
            action.showAlert("فایل مورد نظر جهت دانلود موجود نیست یا با مشکل روبرو شده است.","خطا!");
            dismiss();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadProgress(Download download)
    {
        setProgress(download.getProgress());

        if (download.getProgress() == 100)
        {
            onComplete();

        } else
        {
            if (isVisible())
            {
                setProgressText(String.format("Downloaded (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
