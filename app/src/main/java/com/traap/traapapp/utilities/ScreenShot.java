package com.traap.traapapp.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Javad.Abadi on 8/21/2018.
 */
public class ScreenShot
{
    View view;
    Activity activity;
    String picName;

    public ScreenShot(View v, final Activity activity_)
    {
        this.view = v;
        this.activity = activity_;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
        picName = dateFormat.format(cal.getTime()) + ".jpg";

        final Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        MediaStore.Images.Media.insertImage(activity_.getContentResolver(), bitmap, picName , "");



        new TedPermission(activity)
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {

                        try
                        {
                            store(bitmap, picName);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        final File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/traap/Screenshots/", picName);

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("image/jpg");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myDir));
                        activity.startActivity(Intent.createChooser(sharingIntent, "Share image using"));


                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not share this \n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


    }

    public ScreenShot(View v, final Activity activity_,boolean isSava)
    {
        this.view = v;
        this.activity = activity_;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
        picName = dateFormat.format(cal.getTime()) + ".jpg";

        final Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        MediaStore.Images.Media.insertImage(activity_.getContentResolver(), bitmap, picName , "");



        new TedPermission(activity)
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {

                        try
                        {
                            store(bitmap, picName);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        final File myDir = new File(Environment.getExternalStorageDirectory().toString() + "/traap/Screenshots/", picName);

                       /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("image/jpg");
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(myDir));
                        activity.startActivity(Intent.createChooser(sharingIntent, "Share image using"));*/


                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not share this \n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


    }


    public void store(Bitmap bm, String fileName)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/traap/Screenshots/");
        if (!myDir.exists())
            myDir.mkdirs();
        File file = new File(myDir, fileName);
        try
        {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static File getFile(View view)
    {
        String picName;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss");
        picName = dateFormat.format(cal.getTime()) + ".jpg";

        final Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/traap");
        if (!myDir.exists())
            myDir.mkdirs();
        File file = new File(myDir, picName);
        try
        {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return new File(Environment.getExternalStorageDirectory().toString() + "/traap/", picName);

    }
}

