package com.traap.traapapp.ui.fragments.gateWay;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.view.ViewCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.ForgetPasswordWalletResponse;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by MahsaAzizi on 25/12/2019.
 */
public class ManageWalletFragment extends BaseFragment implements View.OnClickListener
{


    private View rootView, btnChangePass, btnForgetPass;
    private MainActionView mainView;

    private LinearLayout llDetailDescriptionForgetPass, llChangePass;
    private TextView txtForgetPass, txtChangePass;
    private EditText edtTemNewPass, edtNewPass, edtOldPass;
    private TextInputLayout inputLayout;
    private TextInputLayout inputLayout2;
    private TextInputLayout inputLayout3;

    public ManageWalletFragment()
    {

    }

    public static ManageWalletFragment newInstance(MainActionView mainView)
    {
        ManageWalletFragment f = new ManageWalletFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_manage_wallet, container, false);


        initView();


        return rootView;
    }

    private void initView()
    {

        inputLayout = rootView.findViewById(R.id.inputLayout);
        inputLayout2 = rootView.findViewById(R.id.inputLayout2);
        inputLayout3 = rootView.findViewById(R.id.inputLayout3);

        ViewCompat.setLayoutDirection(inputLayout, ViewCompat.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(inputLayout2, ViewCompat.LAYOUT_DIRECTION_RTL);
        ViewCompat.setLayoutDirection(inputLayout3, ViewCompat.LAYOUT_DIRECTION_RTL);

        edtNewPass = rootView.findViewById(R.id.edtNewPass);
        edtOldPass = rootView.findViewById(R.id.edtOldPass);
        edtTemNewPass = rootView.findViewById(R.id.edtTemNewPass);

        txtForgetPass = rootView.findViewById(R.id.txtForgetPass);
        txtChangePass = rootView.findViewById(R.id.txtChangePass);
        llChangePass = rootView.findViewById(R.id.llChangePass);
        btnChangePass = rootView.findViewById(R.id.btnChangePass);
        btnForgetPass = rootView.findViewById(R.id.btnForgetPass);
        llDetailDescriptionForgetPass = rootView.findViewById(R.id.llDetailDescriptionForgetPass);

        btnForgetPass.setOnClickListener(this);
        btnChangePass.setOnClickListener(this);
        txtChangePass.setOnClickListener(this);
        txtForgetPass.setOnClickListener(this);


    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {

    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public void ShowHidePass(View view)
    {

      /*  if(view.getId()==R.id.edtOldPass){

            if(edtOldPass.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                edtOldPass.setLe(R.drawable.hide_password);

                //Show Password
                edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.show_password);

                //Hide Password
                edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }*/
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            case R.id.edtOldPass:
                //show
             /*   edtOldPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                //hide
                edtOldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());*/
                break;
            case R.id.btnForgetPass:

                MessageAlertDialog dialog1 = new MessageAlertDialog((Activity) mainView, "",
                        getContext().getString(R.string._request_change_pass), true, "تایید", "انصراف",
                        true, new MessageAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {
                        requestForgetPassword();

                    }

                    @Override
                    public void onCancelClick()
                    {

                    }
                });
                dialog1.setCancelable(false);
                dialog1.show(((Activity) mainView).getFragmentManager(), "dialogMessage");

        break;
        case R.id.btnChangePass:

        if (edtNewPass.getText().toString().length() > 4 && edtOldPass.getText().toString().length() > 4 && edtTemNewPass.getText().toString().length() > 4)
        {
            if (edtNewPass.getText().toString().equals(edtTemNewPass.getText().toString()))
            {
                requestChangePassword();

            } else
            {
                Toast.makeText(getContext(), getContext().getString(R.string._msg_no_correct_pass), Toast.LENGTH_SHORT).show();

            }
        } else if (edtNewPass.getText().toString().length() == 0 && edtOldPass.getText().toString().length() == 0 && edtTemNewPass.getText().toString().length() == 0)

        {
            Toast.makeText(getContext(), getContext().getString(R.string._msg_none_fields), Toast.LENGTH_SHORT).show();

        } else
        {
            Toast.makeText(getContext(), getContext().getString(R.string._not_currect_pass), Toast.LENGTH_SHORT).show();

        }


        break;

        case R.id.txtChangePass:
        llDetailDescriptionForgetPass.setVisibility(View.GONE);
        llChangePass.setVisibility(View.VISIBLE);

        txtChangePass.setTextColor(getResources().getColor(R.color.textColorPrimary));
        txtChangePass.setBackgroundResource(R.drawable.background_border_a);
        txtForgetPass.setTextColor(getResources().getColor(R.color.returnButtonColor));
        txtForgetPass.setBackgroundResource(android.R.color.transparent);
        break;
        case R.id.txtForgetPass:
        llDetailDescriptionForgetPass.setVisibility(View.VISIBLE);
        llChangePass.setVisibility(View.GONE);

        txtChangePass.setTextColor(getResources().getColor(R.color.returnButtonColor));
        txtChangePass.setBackgroundResource(android.R.color.transparent);
        txtForgetPass.setTextColor(getResources().getColor(R.color.textColorPrimary));
        txtForgetPass.setBackgroundResource(R.drawable.background_border_a);
        break;


    }

}
    /*----------------------------------------------------------------------------------------------------*/

    private void requestChangePassword()
    {
        mainView.showLoading();
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        request.setPin2_new(edtNewPass.getText().toString());
        request.setPin2_old(edtOldPass.getText().toString());
        SingletonService.getInstance().forgetPasswordWalletService().ChangePasswordWalletService(new OnServiceStatus<WebServiceClass<ForgetPasswordWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<ForgetPasswordWalletResponse> response)
            {

                try
                {
                    mainView.hideLoading();

                    if (response.info.statusCode == 200)
                    {

                        showAlert(getActivity(), getActivity().getString(R.string._send_sms_forget_pass), 0);

                     //   showAlert(getActivity(), response.info.message, 0);
                        clearEditText();
                    } else
                    {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e)
                {
                    mainView.hideLoading();
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {

                mainView.showError(message);
                mainView.hideLoading();

            }
        }, request);
    }

    private void clearEditText()
    {
        edtOldPass.setText("");
        edtNewPass.setText("");
        edtTemNewPass.setText("");
    }

    /*----------------------------------------------------------------------------------------------------*/

    private void requestForgetPassword()
    {
        mainView.showLoading();
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().forgetPasswordWalletService().ForgetPasswordWalletService(new OnServiceStatus<WebServiceClass<ForgetPasswordWalletResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<ForgetPasswordWalletResponse> response)
            {
                mainView.hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {


                        showAlert(getActivity(), getActivity().getString(R.string._send_sms_forget_pass), 0);
                    } else
                    {

                        mainView.showError(response.info.message);

                    }
                } catch (Exception e)
                {
                    mainView.hideLoading();
                    mainView.showError(e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {

                mainView.showError(message);
                mainView.hideLoading();

            }
        }, request);
    }
}
