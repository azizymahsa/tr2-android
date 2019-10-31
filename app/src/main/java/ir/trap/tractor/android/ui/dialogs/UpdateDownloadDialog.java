package ir.trap.tractor.android.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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

import com.gastudio.downloadloadding.library.GADownloadingView;

import java.io.File;

import ir.trap.tractor.android.R;
import library.android.eniac.base.BaseDialog;

/**
 * Created by Javad.Aabdi on 3/16/2019.
 */
@SuppressLint("ValidFragment")
public class UpdateDownloadDialog extends BaseDialog
{
    private Dialog dialog;
    private Activity activity;
    private GADownloadingView mProgressBar;
    private TextView mProgressText, tvDownload;
    private boolean isDownloaded = false;


    public UpdateDownloadDialog(Activity activity)
    {
        this.activity = activity;
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

        mProgressBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isDownloaded)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                        intent.setData(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/diba.apk")));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 1234);
                    } else
                    {
                        callInstallProcess();
                    }
                }


            }
        });


        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == Activity.RESULT_OK)
        {
            callInstallProcess();
        }
    }

    private void callInstallProcess()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/diba.apk")),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();

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


    public void setProgress(int progress)
    {
        mProgressBar.updateProgress(progress);
    }

    public void setProgressText(String progressText)
    {
        mProgressText.setText(progressText);

    }

    public void onComplete()
    {
        mProgressBar.updateProgress(100);

        new Handler().postDelayed(() ->
        {
            mProgressBar.releaseAnimation();

            new Handler().postDelayed(() ->
            {
                isDownloaded = true;
                tvDownload.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/diba.apk")), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }, 1000);
        }, 2600);


    }
}
