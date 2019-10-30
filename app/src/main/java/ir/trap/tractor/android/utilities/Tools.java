package ir.trap.tractor.android.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ir.trap.tractor.android.R;


public class Tools
{

    public static HashMap<String, Bitmap> cache = new HashMap<String, Bitmap>();
    public final static int SHORT_TOAST = 0;
    public final static int LONG_TOAST = 1;

    public static boolean isNetworkAvailable(Activity activity)
    {
        ConnectivityManager connectivity = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
            return false;
        } else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean hasICS()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static boolean isEmailValid(CharSequence email)
    {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static void showToast(Context caller, String toastMsg)
    {
//        showToast(caller, toastMsg, R.drawable.logo_toast, LONG_TOAST);
        showToast(caller, toastMsg, 0, LONG_TOAST, 0);
    }

    public static void showToast(Context caller, int Msg)
    {
//        showToast(caller, toastMsg, R.drawable.logo_toast, LONG_TOAST);
        showToast(caller, caller.getResources().getString(Msg), 0, LONG_TOAST, 0);
    }

    public static void showToast(Context caller, String Msg, int color)
    {
        showToast(caller, Msg, 0, LONG_TOAST, color);
    }

    public static void showToast(Context caller, String toastMsg, int imgRes, int toastType, int color)
    {

        try
        {// try-catch to avoid stupid app crashes
            LayoutInflater inflater = LayoutInflater.from(caller);

            View mainLayout = inflater.inflate(R.layout.toast_layout, null);
            View rootLayout = mainLayout.findViewById(R.id.toast_layout_root);

            if (color == R.color.red)
            {
                rootLayout.setBackground(caller.getResources().getDrawable(R.drawable.layout_corner_red));
            }
            else if (color == R.color.green)
            {
                rootLayout.setBackground(caller.getResources().getDrawable(R.drawable.layout_corner_green));
            }
            else
            {
                rootLayout.setBackground(caller.getResources().getDrawable(R.drawable.layout_corner));
            }

            ImageView image = (ImageView) mainLayout.findViewById(R.id.image);
            if (imgRes != 0)
                image.setImageResource(imgRes);
            else
                image.setVisibility(View.GONE);

            TextView text = (TextView) mainLayout.findViewById(R.id.text);
            text.setText(toastMsg);

            Toast toast = new Toast(caller);
            // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setGravity(Gravity.BOTTOM, 0, 250);
            if (toastType == SHORT_TOAST)// (isShort)
                toast.setDuration(Toast.LENGTH_SHORT);
            else
                toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(rootLayout);
            if (!toast.getView().isShown())
            {
                toast.show();
            }
            else
            {
                toast.cancel();
            }
        } catch (Exception ex)
        {// to avoid stupid app crashes
            Log.w("HPD", ex.toString());
        }
    }

    public static void ShowAlert(Context con, String Msg, int title)
    {
        ShowAlert(con, Msg, con.getResources().getString(title), false);
    }

    public static void ShowAlert(Context con, String Msg, int title, boolean finish)
    {
        ShowAlert(con, Msg, con.getResources().getString(title), finish);
    }

    public static void ShowAlert(Context con, int Msg, int title, boolean finish)
    {
        ShowAlert(con, con.getResources().getString(Msg), con.getResources().getString(title), finish);
    }

    public static void ShowAlert(Context con, int Msg, int title)
    {
        ShowAlert(con,
                con.getResources().getString(Msg),
                con.getResources().getString(title), false);

    }

    public static void ShowAlert(final Context con, String Msg, String title, boolean finish)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(con);
        b.setCancelable(false);

        LayoutInflater li = LayoutInflater.from(con);
        View vi = li.inflate(R.layout.dialog_layout, null);
        b.setView(vi);

        TextView title_view = (TextView) vi.findViewById(R.id.confirm_title);
        TextView message = (TextView) vi.findViewById(R.id.confirm_msessage);

        title_view.setText(title);
        message.setText(Msg);

        if (finish)
        {
            b.setPositiveButton("تایید", (dialogInterface, i) -> ((Activity) con).finish());

        }
        else
            b.setPositiveButton("تایید", null);
        b.create();
        b.show();
    }

    static public void CatchError(Context con, String Exception)
    {
        Dialog diag = new Dialog(con);
        diag.setTitle("خطا");
        TextView txt = new TextView(con);
        txt.setText(Exception);
        diag.setContentView(txt);
        diag.show();
    }

    public static Bitmap getBitmapScale(Bitmap bitmap)
    {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        try
        {
            return Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);
        } finally
        {
            bitmap.recycle();
        }
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes = new byte[buffer_size];
            for (; ; )
            {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex)
        {
        }
    }

    public static void writeImageFile(String file_name, File f1, Context context)
    {
        try
        {
            Log.d("file_name--", " " + file_name);

            // File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/Folder/"+file_name+".apk");
            // f2.createNewFile();

            File f2 = new File(Environment.getExternalStorageDirectory().toString() + "/ZappShare" + "/SharedImages");
            f2.mkdirs();
            f2 = new File(f2.getPath() + "/" + file_name + ".jpg");
            f2.createNewFile();

            InputStream in = new FileInputStream(f1);

            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            Toast.makeText(context, "File copied", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage() + " in the specified directory.");
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static File copyFileToExternal(Context context, String fileName)
    {
        File file = null;
        String newPath = Environment.getExternalStorageState() + "/ZappShare" + "/SharedImages";
        try
        {
            File f = new File(newPath);
            f.mkdirs();
            FileInputStream fin = context.openFileInput(fileName);
            FileOutputStream fos = new FileOutputStream(newPath + fileName);
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = fin.read(buffer)) != -1)
            {
                fos.write(buffer, 0, len1);
            }
            fin.close();
            fos.close();
            file = new File(newPath + fileName);
            if (file.exists())
                return file;
        } catch (Exception e)
        {

        }
        return null;
    }

    public static void appBackupAndSend(Context context, ArrayList<File> fileList)
    {
        try
        {
            //            Log.d ("file_name--", " " + file_name);

            // File f2 = new File(Environment.getExternalStorageDirectory().toString()+"/Folder/"+file_name+".apk");
            // f2.createNewFile();
            ArrayList<File> backupFiles = new ArrayList<File>();

            for (File file : fileList)
            {
                File filePath = new File(Environment.getExternalStorageDirectory().toString() + "/ZappBackup");
                filePath.mkdirs();
                filePath = new File(filePath.getPath() + "/" + file.getName() + ".apk");
                backupFiles.add(filePath);
                filePath.createNewFile();

                InputStream in = new FileInputStream(file);

                OutputStream out = new FileOutputStream(filePath);

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0)
                {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Toast.makeText(context, "File copied", Toast.LENGTH_SHORT).show();
            }
            Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.setType("application/vnd.android.package-archive");
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            ArrayList<Uri> uriFiles = new ArrayList<Uri>();

            for (File file : backupFiles)
            {
                uriFiles.add(Uri.fromFile(file));
            }
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriFiles);

            try
            {
                context.startActivity(Intent.createChooser(shareIntent, "Share via"));
            } catch (android.content.ActivityNotFoundException ex)
            {
                Toast.makeText(context, "There are no share applications installed.", Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage() + " in the specified directory.");
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void createCachedFile(Context context, String key, ArrayList<Uri> fileName) throws IOException
    {

        try
        {
            String tempFile = null;
            for (Uri file : fileName)
            {
                FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(fileName);
                oos.close();
                fos.close();

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static Object readCachedFile(Context context, String key) throws IOException, ClassNotFoundException
    {
        Object object = null;
        try
        {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return object;
    }
}