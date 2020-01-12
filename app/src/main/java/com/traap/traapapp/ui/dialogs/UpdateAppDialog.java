package com.traap.traapapp.ui.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.traap.traapapp.R;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.splash.UpdateAppAction;

/**
 * Created by Javad.Abadi on 7/24/2018.
 */
@SuppressLint("ValidFragment")
public class UpdateAppDialog extends DialogFragment implements View.OnClickListener
{
    private Activity activity;
    private Dialog dialog;
    private TextView tvDesc;
    private String desc;
    private CircularProgressButton btnConfirmUpdate, btnCancelUpdate, btnUpdateDetail, btnWebSite;
    private ImageView btnGooglePlayUpdate, btnCafeBazaarUpdate;
    private boolean isForce;
    private FrameLayout flCancelUpdate, flConfirmUpdate, flGooglePlayUpdate, flCafeBazaarUpdate, flWebSite;
    private String downloadUrl, webSiteLink, cafeBazaarLink, googlePlayLink;
    private UpdateAppAction updateApp;


    public UpdateAppDialog(Activity activity, String desc, boolean isForce,
                           @Nullable String downloadUrl,
                           @Nullable String webSiteLink,
                           @Nullable String cafeBazaarLink,
                           @Nullable String googlePlayLink,
                           UpdateAppAction updateApp)
    {
        this.activity = activity;
        this.desc = desc;
        this.isForce = isForce;
        this.downloadUrl = downloadUrl;
        this.webSiteLink = webSiteLink;
        this.cafeBazaarLink = cafeBazaarLink;
        this.googlePlayLink = googlePlayLink;
        this.updateApp = updateApp;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialog_update);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        tvDesc = dialog.findViewById(R.id.tvDesc);
        btnConfirmUpdate = dialog.findViewById(R.id.btnConfirmUpdate);

        btnGooglePlayUpdate = dialog.findViewById(R.id.btnGooglePlayUpdate);
        btnCafeBazaarUpdate = dialog.findViewById(R.id.btnCafeBazaarUpdate);
        btnWebSite = dialog.findViewById(R.id.btnWebSite);
        btnCancelUpdate = dialog.findViewById(R.id.btnCancelUpdate);
        btnUpdateDetail = dialog.findViewById(R.id.btnUpdateDetail);

        flCancelUpdate = dialog.findViewById(R.id.flCancelUpdate);
        flConfirmUpdate = dialog.findViewById(R.id.flConfirmUpdate);
        flGooglePlayUpdate = dialog.findViewById(R.id.flGooglePlayUpdate);
        flCafeBazaarUpdate = dialog.findViewById(R.id.flCafeBazaarUpdate);
        flWebSite = dialog.findViewById(R.id.flWebSite);

        btnConfirmUpdate.setOnClickListener(this);
        btnCancelUpdate.setOnClickListener(this);
        btnWebSite.setOnClickListener(this);
        btnCafeBazaarUpdate.setOnClickListener(this);
        btnGooglePlayUpdate.setOnClickListener(this);
        btnUpdateDetail.setOnClickListener(this);
        tvDesc.setText(desc);
        setCancelable(false);

        if (isForce)
        {
            flCancelUpdate.setVisibility(View.GONE);

        } else
        {
            flCancelUpdate.setVisibility(View.VISIBLE);
        }
        try
        {

            if (!cafeBazaarLink.equals(null))
            {
                flCafeBazaarUpdate.setVisibility(View.VISIBLE);
            }
            else
            {
                flCafeBazaarUpdate.setVisibility(View.GONE);
            }
        }
        catch (NullPointerException e)
        {
            flCafeBazaarUpdate.setVisibility(View.GONE);
        }

        try
        {

            if (!webSiteLink.equals(null))
            {
                flWebSite.setVisibility(View.VISIBLE);
            }
            else
            {
                flWebSite.setVisibility(View.GONE);
            }
        }
        catch (NullPointerException e)
        {
            flWebSite.setVisibility(View.GONE);
        }

        try
        {
            if (!downloadUrl.equals(null))
            {
                flConfirmUpdate.setVisibility(View.VISIBLE);
            }
            else
            {
                flConfirmUpdate.setVisibility(View.GONE);
            }
        }
        catch (NullPointerException e)
        {
            flConfirmUpdate.setVisibility(View.GONE);
        }

        try
        {
            if (!googlePlayLink.equals(null))
            {
                flGooglePlayUpdate.setVisibility(View.VISIBLE);
            }
            else
            {
                flGooglePlayUpdate.setVisibility(View.GONE);
            }

        }
        catch (NullPointerException e)
        {
            flGooglePlayUpdate.setVisibility(View.GONE);
        }

        return dialog;
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnConfirmUpdate:
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    requestPermissionConfirm();
                }
                else
                {
                    goAction();
                }
                dismiss();

                break;
            }
            case R.id.btnCancelUpdate:
                updateApp.onCancel();
                dismiss();
                break;
            case R.id.btnUpdateDetail:
                try
                {
                    if (desc != null)
                    {
                        updateApp.onDetailUpdate();
                    }
                    else
                    {
                        updateApp.onErrorUpdateDescription();
                    }
                }
                catch (Exception e)
                {
                    updateApp.onErrorUpdateDescription();
                }
                break;
            case R.id.btnGooglePlayUpdate:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    requestPermissionGooglePlay();
                }
                else
                {
                    updateApp.onUpdateFromGooglePlay();
                }
                dismiss();
                break;
            case R.id.btnCafeBazaarUpdate:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    requestPermissionCafeBazaar();
                }
                else
                {
                    updateApp.onUpdateFromCafeBazaar();
                }
                dismiss();
                break;
            case R.id.btnWebSite:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    requestPermissionSite();
                }
                else
                {
                    updateApp.onUpdateFromWebSite(webSiteLink);
                }
                dismiss();
                break;
        }
    }

    private void requestPermissionConfirm()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        goAction();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        requestPermissionConfirm();
                    }
                })
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void requestPermissionCafeBazaar()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        updateApp.onUpdateFromCafeBazaar();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        requestPermissionCafeBazaar();
                    }
                })
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void requestPermissionGooglePlay()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        updateApp.onUpdateFromGooglePlay();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        requestPermissionGooglePlay();
                    }
                })
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void requestPermissionDirect()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        goAction();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        requestPermissionDirect();
                    }
                })
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void requestPermissionSite()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        updateApp.onUpdateFromWebSite(webSiteLink);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        requestPermissionSite();
                    }
                })
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void goAction()
    {
        String packageName = activity.getApplicationContext().getPackageName();

//        if (downloadUrl.endsWith(".apk"))
//        {
            try
            {
                updateApp.onUpdate();
            }
            catch (Exception e)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                activity.startActivityForResult(intent,100);
            }
//        }
//        else if (downloadUrl.contains("play.google.com"))
//        {
//            try
//            {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("market://details?id=" + packageName));
//                activity.startActivityForResult(intent,100);
//            }
//            catch (ActivityNotFoundException e2)
//            {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
//                activity.startActivityForResult(intent,100);
//            }
//        }
//        else if (downloadUrl.contains("cafebazaar.ir"))
//        {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("https://cafebazaar.ir/app/" + packageName));
//            activity.startActivityForResult(intent,100);
//        }
//        else
//        {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(downloadUrl));
//            activity.startActivityForResult(intent,100);
//        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

}
