package ir.trap.tractor.android.ui.base;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.trap.tractor.android.utilities.Tools;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity
{

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    public void showToast(Context context, String message, int color)
    {
        Tools.showToast(context, message, color);
    }

    public void showError(Context context, String message)
    {
        Tools.showToast(context, message, R.color.red);
    }

    public static void showAlert(Context context, String Msg, int title)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    public static void showAlert(Context context, String Msg, int title, boolean finish)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");

        if (finish)
        {
            ((Activity) context).finish();
        }
    }

    public static void showAlert(Context context, int Msg, int title, boolean finish)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, context.getString(Msg));
        dialog.show(((Activity) context).getFragmentManager(), "dialog");

        if (finish)
        {
            ((Activity) context).finish();
        }
    }

    public static void showAlert(Context context, int Msg, int title)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, context.getString(Msg));
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

}
