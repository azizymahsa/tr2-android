package com.traap.traapapp.ui.activities.userProfile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;
import com.traap.traapapp.apiServices.model.profile.putProfile.request.SendProfileRequest;
import com.traap.traapapp.apiServices.model.profile.putProfile.response.SendProfileResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by Javad.Abadi on 10/7/2019.
 */
public class UserProfileActivity extends BaseActivity implements UserProfileActionView,
        OnAnimationEndListener, OnServiceStatus<WebServiceClass<GetProfileResponse>>
{
    private Toolbar mToolbar;
    private CircularProgressButton btnConfirm;
    private EditText etFirstName, etLastName, etFirstNameUS, etNationalCode, etBirthDay;
    private ClearableEditText etPopularPlayer;
    private TextView tvMenu;
    //    private ImageView ivProfile;
    private File userPic;
    private boolean isChangePic = false;
//    private FrameLayout flLogoToolbar;
    private String popularPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mToolbar = findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("ویرایش حساب کاربری");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        btnConfirm = findViewById(R.id.btnConfirm);
        //btnConfirm.setText("ارسال اطلاعات کاربری");

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPopularPlayer = findViewById(R.id.etPopularPlayer);
        etFirstNameUS = findViewById(R.id.etFirstNameUS);
        etNationalCode = findViewById(R.id.etNationalCode);
        etBirthDay = findViewById(R.id.etBirthDay);

//        flLogoToolbar = findViewById(R.id.flLogoToolbar);
        etPopularPlayer.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });

//        flLogoToolbar.setVisibility(View.GONE);

      //  etFirstName.requestFocus();

        getDataProfileUser();


        btnConfirm.setOnClickListener(v ->
        {
            btnConfirm.startAnimation();
            btnConfirm.setClickable(false);

            sendProfileData();
        });
    }

    private void sendProfileData()
    {
        if (etNationalCode.getText().toString().trim().equalsIgnoreCase("") &&
                etFirstNameUS.getText().toString().trim().equalsIgnoreCase("") &&
                etFirstName.getText().toString().trim().equalsIgnoreCase("") &&
                etLastName.getText().toString().trim().equalsIgnoreCase("") &&
                etBirthDay.getText().toString().trim().equalsIgnoreCase("") &&
                (etPopularPlayer.getText().toString().trim().equalsIgnoreCase("") ||
                        etPopularPlayer.getText().toString().trim().equalsIgnoreCase("0")) )
        {
            showError(this, "اطلاعاتی جهت ارسال مشخص نشد.");
            hideLoading();
        }
        else
        {
            Integer popularPlayer = 0;
            if (!etPopularPlayer.getText().toString().equalsIgnoreCase(""))
            {
                popularPlayer = Integer.parseInt(etPopularPlayer.getText().toString());
            }


            SendProfileRequest request = new SendProfileRequest();

            request.setPopularPlayer(popularPlayer);
            request.setFirstName(etFirstName.getText().toString());
            request.setLastName(etLastName.getText().toString());
            request.setEnglishName(etFirstNameUS.getText().toString());

            request.setBirthday(etBirthDay.getText().toString().equalsIgnoreCase("") ?
                    null :
                    etBirthDay.getText().toString()
            );

            request.setNationalCode(etNationalCode.getText().toString());

            SingletonService.getInstance().sendProfileService().sendProfileService(request,
                    new OnServiceStatus<WebServiceClass<SendProfileResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<SendProfileResponse> response)
                {
                    if (response.info.statusCode != 200)
                    {
                        showError(UserProfileActivity.this, response.info.message);
                        hideLoading();

                    }
                    else
                    {
                        Prefs.putString("firstName", response.data.getFirstName());
                        Prefs.putString("lastName", response.data.getLastName());
                        Prefs.putString("FULLName", response.data.getFirstName() + " " + response.data.getLastName());
                        Prefs.putString("englishName", response.data.getEnglishName());
                        if (response.data.getBirthday() != null)
                        {
                            Prefs.putString("birthday", response.data.getBirthday());
                        }
                        if (response.data.getPopularPlayer() != 0)
                        {
                            Prefs.putInt("popularPlayer", response.data.getPopularPlayer());
                        }
                        Prefs.putString("nationalCode", response.data.getNationalCode());

                        showToast(UserProfileActivity.this, "اطلاعات کاربری شما با موفقیت بروزرسانی شد.", R.color.green);
                        onAnimationEnd();
                    }
                }

                @Override
                public void onError(String message)
                {
                    if (!Tools.isNetworkAvailable(UserProfileActivity.this))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(UserProfileActivity.this, "خطا در دریافت اطلاعات از سرور!");
                        hideLoading();

                    }
                    else
                    {
                        showAlert(UserProfileActivity.this, R.string.networkErrorMessage, R.string.networkError);
                       hideLoading();
                    }
                }
            });
        }
    }

    private void getDataProfileUser()
    {

        etFirstName.setText(Prefs.getString("firstName", ""));
        etLastName.setText(Prefs.getString("lastName", ""));

        SingletonService.getInstance().getProfileService().getProfileService(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //  presenter.onDestroy();
    }

    @Override
    public void showLoading()
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);

    }

    @Override
    public void hideLoading()
    {
        btnConfirm.revertAnimation(UserProfileActivity.this);
        btnConfirm.setClickable(true);
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setText("ارسال اطلاعات کاربری");
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_button_login));
    }

    @Override
    public void openImageChooser()
    {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderMode(true) // set folder mode (false by default)
                .single()
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select")
                .toolbarDoneButtonText("DONE") // done button text
                .start(); // image selection title
    }

    @Override
    public void uploadImage()
    {
        if (!isChangePic)
            return;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (ImagePicker.shouldHandle(requestCode, resultCode, data))
        {
            // Get a list of picked images
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null)
            {
                saveImage(BitmapFactory.decodeFile(image.getPath()));
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 22)
        {
            String pass = data.getStringExtra("newPass");


        }
    }

    private void saveImage(Bitmap finalBitmap)
    {
        isChangePic = true;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/eniac");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "profile" + ".jpg";
        userPic = new File(myDir, fname);
        if (userPic.exists()) userPic.delete();
        try
        {
            FileOutputStream out = new FileOutputStream(userPic);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);
            out.flush();
            out.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(WebServiceClass<GetProfileResponse> response)
    {
        if (response.info.statusCode == 200)
        {
          /*  if (response.data.getPopularPlayer() != 0)
            {
                Prefs.putInt("favPlayerNo", response.data.getPopularPlayer());
            }
*/
            etFirstName.setText(response.data.getFirstName());
            etLastName.setText(response.data.getLastName());
          //  etPopularPlayer.setText(response.data.getPopularPlayer().toString());
            etBirthDay.setText(response.data.getBirthday());
            etFirstNameUS.setText(response.data.getEnglishName());
            etNationalCode.setText(response.data.getNationalCode());

            Prefs.putString("shareText", response.data.getShareText());
        }
        else
        {
            showError(this, response.info.message);
        }
    }

    @Override
    public void onError(String message)
    {
        if (!Tools.isNetworkAvailable(this))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(this, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(this, R.string.networkErrorMessage, R.string.networkError);
        }
    }
}