package ir.trap.tractor.android.ui.dialogs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.card.Result;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardActionView;
import ir.trap.tractor.android.utilities.Utility;
import library.android.eniac.base.BaseDialog;

/**
 * Created by Javad.Abadi on 2/20/2019.
 */
@SuppressLint("ValidFragment")
public class ChangePasswordDialog extends BaseDialog implements View.OnClickListener, OnAnimationEndListener
{
    private Activity activity;
    private Dialog dialog;
    private CircularProgressButton btnConfirmEdit, btnCancelEdit;
    private EditText etPass, etNewPass, etRepeatNewPass;
    private TextView tvForgetPassword, tvConfirmForget;
    private TextInputLayout etLayoutPass, etLayoutNewPass, etLayoutRepeatNewPass;
    private FavoriteCardActionView mainView;
    private Result item;
    private LinearLayout llPass;

    private static final int TYPE_CHANGE_PASSWORD = 1;
    private static final int TYPE_FORGOT_PASSWORD = 2;

    private int type = TYPE_CHANGE_PASSWORD;


    public ChangePasswordDialog(Activity activity, FavoriteCardActionView mainView, Result item)
    {
        this.activity = activity;
        this.mainView = mainView;
        this.item = item;

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.alert_dialig_change_pass);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        etLayoutPass = dialog.findViewById(R.id.etLayoutPass);
        etLayoutNewPass = dialog.findViewById(R.id.etLayoutNewPass);
        etLayoutRepeatNewPass = dialog.findViewById(R.id.etLayoutRepeatNewPass);
        etPass = dialog.findViewById(R.id.etPass);
        etNewPass = dialog.findViewById(R.id.etNewPass);
        etRepeatNewPass = dialog.findViewById(R.id.etRepeatNewPass);
        btnConfirmEdit = dialog.findViewById(R.id.btnConfirmEdit);
        btnCancelEdit = dialog.findViewById(R.id.btnCancelEdit);
        tvForgetPassword = dialog.findViewById(R.id.tvForgetPassword);
        llPass = dialog.findViewById(R.id.llPass);
        tvConfirmForget = dialog.findViewById(R.id.tvConfirmForget);
        etLayoutPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etLayoutNewPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etLayoutRepeatNewPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));

        tvConfirmForget.setText("آیا از ارسال پیامک حاوی رمز دوم کارت به شماره همراه " + Prefs.getString("mobile", "") + " اطمینان دارید؟");
        btnCancelEdit.setOnClickListener(this);
        btnConfirmEdit.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvForgetPassword:

                YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        llPass.setVisibility(View.GONE);
                        tvForgetPassword.setVisibility(View.GONE);
                        tvConfirmForget.setVisibility(View.VISIBLE);
                        btnConfirmEdit.setText("ارسال پیامک");
                        type = TYPE_FORGOT_PASSWORD;

                    }
                }).duration(200).playOn(llPass);

                break;
            case R.id.btnConfirmEdit:
            {
//                if (!Utility.isNetworkAvailable())
//                {
//                    mainView.onInternetAlert();
//                    dismiss();
//                    return;
//                }
                Utility.hideSoftKeyboard(v, activity);


//                ChangePasswordTowRequestN request = new ChangePasswordTowRequestN();
//                ForgetPasswordTowRequest passwordTowRequest = new ForgetPasswordTowRequest();

                if (type == TYPE_CHANGE_PASSWORD)
                {
                    if (TextUtils.isEmpty(etPass.getText().toString()))
                    {
                        Toast.makeText(activity, "رمز کارت را وارد نمایید.", Toast.LENGTH_SHORT).show();


                        return;

                    }
                    if (TextUtils.isEmpty(etNewPass.getText().toString()))
                    {

                        Toast.makeText(activity, "رمز جدید را وارد نمایید.", Toast.LENGTH_SHORT).show();

                        return;

                    }
                    if (TextUtils.isEmpty(etRepeatNewPass.getText().toString()))
                    {
                        Toast.makeText(activity, "تکرار رمز جدید را وارد نمایید.", Toast.LENGTH_SHORT).show();


                        return;

                    }

                    if (!etRepeatNewPass.getText().toString().equals(etNewPass.getText().toString()))
                    {

                        Toast.makeText(activity, "تکرار رمز صحیح نمی باشد.", Toast.LENGTH_SHORT).show();


                        return;
                    }

                    mainView.onChangePasswordCard(item.getCardId(), etPass.getText().toString(),
                            etNewPass.getText().toString());
                }
                else
                {
                    mainView.onForgotPasswordCard(item.getCardId());
                }
                break;
            }
            case R.id.btnCancelEdit:
            {
                dismiss();
                break;
            }
        }
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirmEdit.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_border_a));
    }


    public void showProgress()
    {
        btnConfirmEdit.startAnimation();
        btnConfirmEdit.setClickable(false);

    }

    public void hideProgress()
    {
        btnConfirmEdit.revertAnimation();
        btnConfirmEdit.setClickable(true);
    }
}
