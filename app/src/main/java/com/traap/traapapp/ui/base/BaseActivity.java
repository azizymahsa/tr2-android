package com.traap.traapapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;

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


    @Override
    protected void onResume()
    {
        super.onResume();

        // It's required to call.
        //
        // This helps library to track correctly the next things:
        //  - active users
        //  - sessions duration
        //  - app usage frequency
//        YandexMetrica.resumeSession(this);

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // It's required to call.
        //
        // This helps library to track correctly the next things:
        //  - active users
        //  - sessions duration
        //  - app usage frequency
//        YandexMetrica.pauseSession(this);
        hideKeyboard(this);
    }

    public void showToast(Context context, String message, int color)
    {
        if (color == R.color.green)
        {
            showAlertSuccess(context, message, "", false);
        }
        else if (color == R.color.red)
        {
            showAlertFailure(context, message, context.getString(R.string.error), false);
        }
        else
        {
            showAlert(context, message, 0);
        }
    }

    public void showError(Context context, String message)
    {
        showAlertFailure(context, message, context.getString(R.string.error), false);
    }

    public void showAlertSuccess(Context context, String Msg, String mTitle, boolean finish)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, false,
                MessageAlertDialog.TYPE_SUCCESS, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                if (finish)
                {
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    public void showAlertFailure(Context context, String Msg, String mTitle, boolean finish)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, false,
                MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                if (finish)
                {
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    public static void showAlert(Context context, String Msg, int title)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, MessageAlertDialog.TYPE_MESSAGE);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    public static void showAlert(Context context, String Msg, int title, boolean finish)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, MessageAlertDialog.TYPE_MESSAGE);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");

        if (finish)
        {
            ((Activity) context).finish();
        }
    }

    public static void showAlert(Context context, int Msg, int title, boolean finish)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, context.getString(Msg), MessageAlertDialog.TYPE_MESSAGE);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");

        if (finish)
        {
            ((Activity) context).finish();
        }
    }

    public static void showAlert(Context context, int Msg, int title)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, context.getString(Msg), MessageAlertDialog.TYPE_MESSAGE);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }


    public static void hideKeyboard(Activity activity)
    {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null)
        {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
