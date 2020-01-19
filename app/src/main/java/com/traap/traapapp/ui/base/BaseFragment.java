package com.traap.traapapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import com.traap.traapapp.R;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.MessageAlertSuccesDialog;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.Objects;

public class BaseFragment extends Fragment
{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    public void showToast(Context context, String message, int color)
    {
//        Tools.showToast(context, message, color);
        if (color == R.color.green)
        {
            ShowAlertSuccess(context, message, "", false);
        }
        else if (color == R.color.red)
        {
            ShowAlertFailure(context, message, context.getString(R.string.error), false);
        }
        else
        {
            showAlert(context, message, 0);
        }
    }

    public void showError(Context context, String message)
    {
//        Tools.showToast(context, message, R.color.red);
        ShowAlertFailure(context, message, context.getString(R.string.error), false);
    }

    public void ShowAlertSuccess(Context context, String Msg, String mTitle, boolean finish)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, MessageAlertDialog.TYPE_SUCCESS);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");

        if (finish)
        {
            ((Activity) context).finish();
        }
    }

    public void ShowAlertFailure(Context context, String Msg, String mTitle, boolean finish)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, MessageAlertDialog.TYPE_ERROR);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");

        if (finish)
        {
            ((Activity) context).finish();
        }
    }

    public static void showAlert(Context context, String Msg, int title)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, mTitle, Msg, MessageAlertDialog.TYPE_MESSAGE);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }
public static void showAlertSuccess(Context context, String Msg, int title,int type)
    {
        String mTitle = title == 0 ? "" : context.getString(title);
        MessageAlertSuccesDialog dialog = new MessageAlertSuccesDialog((Activity) context, mTitle, Msg, MessageAlertDialog.TYPE_SUCCESS);
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

    @Override
    public void onPause()
    {
        super.onPause();
        hideKeyboard(Objects.requireNonNull(getActivity()));
    }
}
