package com.traap.traapapp.ui.activities.userProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import saman.zamani.persiandate.PersianDate;

import com.readystatesoftware.chuck.ChuckInterceptor;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.traap.traapapp.BuildConfig;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.helper.Const;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;
import com.traap.traapapp.apiServices.model.profile.postPhoto.SendImageResponse;
import com.traap.traapapp.apiServices.model.profile.putProfile.request.SendProfileRequest;
import com.traap.traapapp.apiServices.model.profile.putProfile.response.SendProfileResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NationalCodeValidation;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.PrepareImageFilePart;
//import com.traap.traapapp.utilities.calendar.materialdatetimepicker.com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Javad.Abadi on 10/7/2019.
 */
public class UserProfileActivity extends BaseActivity implements UserProfileActionView,
        OnAnimationEndListener, OnServiceStatus<WebServiceClass<GetProfileResponse>>,  DatePickerDialog.OnDateSetListener
{
    private Toolbar mToolbar;
    private CircularProgressButton btnConfirm;
    private ClearableEditText etFirstName, etLastName, etFirstNameUS, etLastNameUS, etEmail, etNationalCode, etNickName;
    private ClearableEditText etPopularPlayer;
    private TextView tvMenu, tvBirthDay, tvUserName, tvHeaderPopularNo;
    private Spinner spinnerGender;
    private FloatingActionButton fabCapture;
    private ImageView imgProfile, imgBirthdayReset;

    private Boolean isImageFileExist = false;

    private HeaderModel headerModel;

    private boolean sendProfileSuccess = false, sendPhotoSuccess = false;
    private boolean sendProfileFailure = false, sendPhotoFailure = false;

    private PersianCalendar currentDate;
    private Uri imageUri;

    private DatePickerDialog pickerDialogDate;

    private Integer popularPlayer = 12;

    private File userPic;
    private ArrayList<String> genderStrList;

    private MultipartBody.Part part;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mToolbar = findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setVisibility(View.INVISIBLE);
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> finish());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("ویرایش حساب کاربری");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nested);
        btnConfirm = findViewById(R.id.btnConfirm);
//        btnConfirm.setText("ارسال اطلاعات کاربری");

        imgBirthdayReset = findViewById(R.id.imgBirthdayReset);
        imgProfile = findViewById(R.id.imgProfile);
        fabCapture = findViewById(R.id.fabCapture);
        spinnerGender = findViewById(R.id.spinnerGender);
        tvBirthDay = findViewById(R.id.tvBirthDay);
        etNickName = findViewById(R.id.etNickName);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPopularPlayer = findViewById(R.id.etPopularPlayer);
        etFirstNameUS = findViewById(R.id.etFirstNameUS);
        etLastNameUS = findViewById(R.id.etLastNameUS);
        etEmail = findViewById(R.id.etEmail);
        etNationalCode = findViewById(R.id.etNationalCode);

        etFirstName.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) });
        etLastName.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) });
        etFirstNameUS.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) });
        etLastNameUS.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) });
        etEmail.setFilters(new InputFilter[] { new InputFilter.LengthFilter(100) });
        etNickName.setFilters(new InputFilter[] { new InputFilter.LengthFilter(50) });

        etPopularPlayer.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
        etNationalCode.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

        etFirstName.requestFocus();

        genderStrList= new ArrayList<String>();
//        genderStrList.add("--انتخاب جنسیت--");
        genderStrList.add("مرد");
        genderStrList.add("زن");

        ArrayAdapter<String> adapterGenderStrList = new ArrayAdapter<String>(this,
                R.layout.my_spinner_item, genderStrList);
        adapterGenderStrList.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGenderStrList);

        initDate();
        getDataProfileUser();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY - oldScrollY > 0)
                {
                    Animation animHide = AnimationUtils.loadAnimation(UserProfileActivity.this, R.anim.hide_button);
                    findViewById(R.id.rlImageProfile).startAnimation(animHide);
                    findViewById(R.id.rlImageProfile).setVisibility(View.GONE);
                }

                else
                {
                    Animation animShow = AnimationUtils.loadAnimation(UserProfileActivity.this, R.anim.show_button);
                    findViewById(R.id.rlImageProfile).startAnimation(animShow);
                    findViewById(R.id.rlImageProfile).setVisibility(View.VISIBLE);
                }
            }
        });


        btnConfirm.setOnClickListener(v ->
        {
            btnConfirm.startAnimation();
            btnConfirm.setClickable(false);

            uploadProfileData();
        });

        findViewById(R.id.rlBirthDay).setOnClickListener(v ->
        {
            pickerDialogDate.show(getSupportFragmentManager(), "CreateDate");
//            pickerDialogDate.show(getFragmentManager(), "CreateDate");
        });

        imgBirthdayReset.setOnClickListener(v ->
        {
            tvBirthDay.setText("");
            imgBirthdayReset.setVisibility(View.GONE);
        });

        fabCapture.setOnClickListener(v ->
        {
            getPermission();
        });
    }

    private void getPermission()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        openImageChooser();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                        MessageAlertDialog dialog = new MessageAlertDialog(UserProfileActivity.this, "",
                                "برای دسترسی به عکس های دستگاهتان اخذ این مجوز الزامی است. ",
                                true, new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                getPermission();
                            }

                            @Override
                            public void onCancelClick()
                            {

                            }
                        }
                        );
                        dialog.show(getFragmentManager(), "dialogMessage");
                    }
                })
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void initDate()
    {
        currentDate = new PersianCalendar();

        pickerDialogDate = DatePickerDialog.newInstance(this,
                currentDate.getPersianYear(),
                currentDate.getPersianMonth(),
                currentDate.getPersianDay()
        );
        pickerDialogDate.setMaxDate(currentDate);
    }

    private boolean setError()
    {
        boolean err = true;
        String message = "";
        if (etNationalCode.getText().toString().length() < 10 && etNationalCode.getText().toString().length() > 0)
        {
            message = message + "کد ملی،";
            err = false;
//            etNationalCode.setError("کد ملی باید 10رقمی باشد!");
        }
        else if (etNationalCode.getText().toString().length() == 10)
        {
            if (!NationalCodeValidation.isValidNationalCode(etNationalCode.getText().toString()))
            {
                message = message + "کد ملی،";
                err = false;
//                ((TextView)etNationalCode).setError("کد ملی نامعتبر است!");
            }
        }
        if (!etEmail.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-zA-Z]+.[a-zA-Z]+") && !etEmail.getText().toString().equalsIgnoreCase(""))
        {
            message = message + "ایمیل،";
            err = false;
//            etEmail.setError("ایمیل درست نیست!");
        }

//        if (!etFirstName.getText().toString().trim().matches("[ آئءا-ی]+") && !etFirstName.getText().toString().equalsIgnoreCase(""))
//        {
//            message = message + "نام فارسی،";
//            err = false;
////            etFirstNameUS.setError("نام انگلیسی درست نیست!");
//        }
//
//        if (!etLastName.getText().toString().trim().matches("[ آئءا-ی]+") && !etLastName.getText().toString().equalsIgnoreCase(""))
//        {
//            message = message + "نام خانوادگی فارسی،";
//            err = false;
////            etFirstNameUS.setError("نام انگلیسی درست نیست!");
//        }

//        if (!etFirstNameUS.getText().toString().trim().matches("[a-zA-Z ]+") && !etFirstNameUS.getText().toString().equalsIgnoreCase(""))
//        {
//            message = message + "نام انگلیسی،";
//            err = false;
////            etFirstNameUS.setError("نام انگلیسی درست نیست!");
//        }
//
//        if (!etLastNameUS.getText().toString().trim().matches("[a-zA-Z ]+") && !etLastNameUS.getText().toString().equalsIgnoreCase(""))
//        {
//            message = message + "نام خانوادگی انگلیسی،";
//            err = false;
////            etLastNameUS.setError("نام خانوادگی انگلیسی درست نیست!");
//        }

        if (!err)
        {
            message = message + " باید اصلاح گردد.";
            showError(this, message);
        }
//        else if (etNationalCode.getText().toString().trim().equalsIgnoreCase("") &&
//                etFirstNameUS.getText().toString().trim().equalsIgnoreCase("") &&
//                etLastNameUS.getText().toString().trim().equalsIgnoreCase("") &&
//                etFirstName.getText().toString().trim().equalsIgnoreCase("") &&
//                etLastName.getText().toString().trim().equalsIgnoreCase("") &&
//                tvBirthDay.getText().toString().trim().equalsIgnoreCase("") &&
//                spinnerGender.getSelectedItemPosition() == 0 &&
//                etEmail.getText().toString().trim().equalsIgnoreCase("") &&
//                (etPopularPlayer.getText().toString().equalsIgnoreCase("") ||
//                        etPopularPlayer.getText().toString().trim().equalsIgnoreCase("0")) )
//        {
//            err = false;
//            showError(this, "اطلاعاتی جهت ارسال مشخص نشد.");
//        }
        return err;
    }

    private void getDataProfileUser()
    {
        SingletonService.getInstance().getProfileService().getProfileService(this);
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
//        btnConfirm.setText("ارسال اطلاعات کاربری");
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_button_login));

    }

    @Override
    public void openImageChooser()
    {
//        ImagePicker.create(this)
//                .returnMode(ReturnMode.GALLERY_ONLY) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
//                .folderMode(true) // set folder mode (false by default)
//                .single()
//                .toolbarFolderTitle("Folder") // folder selection title
//                .toolbarImageTitle("Tap to select")
//                .toolbarDoneButtonText("DONE") // done button text
//                .start(); // image selection title

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    public void uploadProfileData()
    {
//        if (!isChangePic)
//            return;

        if (!setError())
        {
            hideLoading();
        }
        else
        {
            try
            {
                if (!etPopularPlayer.getText().toString().equalsIgnoreCase("") &&
                        !etPopularPlayer.getText().toString().equalsIgnoreCase("0"))
                {
                    popularPlayer = Integer.parseInt(etPopularPlayer.getText().toString().trim());
                }
                else
                {
                    popularPlayer = 12;
                }
            }
            catch (Exception e)
            {

            }
            headerModel = new HeaderModel();

            try
            {

//                part = PrepareImageFilePart.prepareFilePart(userPic.getName(), userPic);
//                part = PrepareImageFilePart.prepareFilePart("photo", userPic);
//                part = MultipartBody.Part.createFormData("photo", userPic.getName(),
//                        RequestBody.create(MediaType.parse("image/*"), userPic));
                if (isImageFileExist)
                {
                    isImageFileExist = false;
                    sendProfilePhoto();
                }
                else
                {
                    sendPhotoSuccess = true;
                }
            }
            catch (Exception e)
            {
                sendPhotoSuccess = true;
                Logger.e("-Exception Photo-", e.getMessage());
//                part = null;
            }

            SendProfileRequest request = new SendProfileRequest();
            request.setPopularPlayer(12);

            request.setFirstName(etFirstName.getText().toString().trim());
            request.setLastName(etLastName.getText().toString().trim());
            request.setFirstNameUS(etFirstNameUS.getText().toString().trim());
            request.setLastNameUS(etLastNameUS.getText().toString().trim());
            request.setNationalCode(etNationalCode.getText().toString().trim());
            request.setPopularPlayer(12);
            request.setEmail(etEmail.getText().toString().trim());
            request.setNickName(etNickName.getText().toString().trim());
//            request.setBirthday(tvBirthDay.getText().toString().equalsIgnoreCase("") ? "" :
//                    getGrgDate(tvBirthDay.getText().toString().trim()));
            request.setBirthday(tvBirthDay.getText().toString().trim().replace("/", "-"));

            request.setGender(spinnerGender.getSelectedItemPosition()+1);

            SingletonService.getInstance().sendProfileService().sendProfileService(request,
                    new OnServiceStatus<WebServiceClass<SendProfileResponse>>()
                    {
                        @Override
                        public void onReady(WebServiceClass<SendProfileResponse> response)
                        {
                            if (response.info.statusCode != 200)
                            {
                                showError(UserProfileActivity.this, response.info.message);
                                sendProfileFailure = true;
                                finishSendData("");
                            }
                            else
                            {
                                //------------------------------------------
                                Prefs.putString("firstName", etFirstName.getText().toString());
                                Prefs.putString("lastName", etLastName.getText().toString());
                                Prefs.putString("FULLName", etFirstName.getText().toString() + " " + etLastName.getText().toString());
                                Prefs.putString("nickName", etNickName.getText().toString());
                                Prefs.putString("firstNameUS", etFirstNameUS.getText().toString());
                                Prefs.putString("lastNameUS", etLastNameUS.getText().toString());
                                Prefs.putString("email", etEmail.getText().toString());
                                Prefs.putInt("gender", spinnerGender.getSelectedItemPosition());

                                if (tvBirthDay.getText() != null)
                                {
                                    Prefs.putString("birthday", tvBirthDay.getText().toString().equalsIgnoreCase("") ?
                                            null :
                                            tvBirthDay.getText().toString());
                                }
                                if (popularPlayer != 0)
                                {
                                    Prefs.putInt("popularPlayer", popularPlayer);
                                }
                                else
                                {
                                    Prefs.putInt("popularPlayer", 12);
                                }
                                Prefs.putString("nationalCode", etNationalCode.getText().toString());

                                if (!Prefs.getString("FULLName", "").trim().replace(" ", "")
                                        .equalsIgnoreCase(""))
                                {
                                    TrapConfig.HEADER_USER_NAME = Prefs.getString("FULLName", "");
                                }
                                else
                                {
                                    TrapConfig.HEADER_USER_NAME = Prefs.getString("mobile", "");
                                }

                                headerModel.setPopularNo(popularPlayer);
                                headerModel.setHeaderName(TrapConfig.HEADER_USER_NAME);

                                sendProfileSuccess = true;
                                finishSendData("");
                            }
                        }

                        @Override
                        public void onError(String message)
                        {
                            sendProfileFailure = true;
                            finishSendData("");
                            if (Tools.isNetworkAvailable(UserProfileActivity.this))
                            {
                                Logger.e("-OnError-", "Error: " + message);
                                showError(UserProfileActivity.this, "خطا در دریافت اطلاعات از سرور!");

                            }
                            else
                            {
                                showAlert(UserProfileActivity.this, R.string.networkErrorMessage, R.string.networkError);
                            }
                        }
                    });
        }

    }

    private void sendProfilePhoto()
    {
        //---------------------new FAN--------------------------
        Logger.e("--imagePath--", userPic.getAbsolutePath());
        Logger.e("--url--", Const.BASEURL + Const.SEND_PROFILE_PHOTO);
        Logger.e("--token--", Prefs.getString("accessToken",""));

        AndroidNetworking.upload(Const.BASEURL + Const.SEND_PROFILE_PHOTO)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization",Prefs.getString("accessToken",""))
//                .setOkHttpClient(client.build())
                .addMultipartFile("photo", userPic)
                .setTag("uploadProfile")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener((bytesUploaded, totalBytes) ->
                {
                    Logger.e("-Upload Progress-",  "in Progress #");
                })
//                .getAsObject(WebServiceClass.class, new ParsedRequestListener<WebServiceClass<SendImageResponse>>()
//                {
//                    @Override
//                    public void onResponse(WebServiceClass<SendImageResponse> response)
//                    {
//                        if (response.info.statusCode == 201)
//                        {
//                            headerModel.setProfileUrl(response.data.getImageUrl());
//
//                            sendPhotoSuccess = true;
//                            finishSendData();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError)
//                    {
//                        sendPhotoFailure = true;
//                        Logger.e("-onError Photo1-", "Error: " + anError.getErrorDetail());
//                        Logger.e("-onError Photo2-", "Error: " + anError);
//
//                        finishSendData();
//                    }
//                });
                .getAsString(new StringRequestListener()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e("--Upload Response--", response + " ##");

                        try
                        {
                            JSONObject object = new JSONObject(response);
                            JSONObject info = object.getJSONObject("info");
                            Logger.e("+info+", info.toString());
                            JSONObject data = object.getJSONObject("data");
                            Logger.e("+data+", data.toString());

                            Integer status = info.getInt("code");

                            Logger.e("-status Response1-", status.toString() + " ");
                            Logger.e("-status Response2-", info.getString("code"));
                            if (status == 201)
                            {
                                String imageURL = data.getString("photo");
                                headerModel.setProfileUrl(imageURL);
                                Logger.e("-image Link Response-", imageURL.toString() + " ");
                                Prefs.putString("profileImage", imageURL);
//
                                sendPhotoSuccess = true;
                                finishSendData("");
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        sendPhotoSuccess = true;
                        finishSendData("");
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        sendPhotoFailure = true;

                        if (Tools.isNetworkAvailable(UserProfileActivity.this))
                        {
                            Logger.e("-onError Photo1-", "Error: " + anError.getErrorDetail());
                            Logger.e("-onError Photo2-", "Error: " + anError);

                        }
                        else
                        {
                            showAlert(UserProfileActivity.this, R.string.networkErrorMessage, R.string.networkError);
                        }
                        finishSendData(anError + " ");
                    }
                });
        //---------------------new FAN--------------------------

//        try
//        {
//            SingletonService.getInstance().sendProfileService().sendProfilePhoto(part, new OnServiceStatus<WebServiceClass<Object>>()
//            {
//                @Override
//                public void onReady(WebServiceClass<Object> response)
//                {
//                    if (response.info.statusCode != 200)
//                    {
//                        sendPhotoFailure = true;
//                        Logger.e("-onReady Photo1-", "Error: " + response.info.message);
//                        Logger.e("-onReady Photo2-", "Exception: " + response.info.exception);
//                        finishSendData();
//                    }
//                    else
//                    {
//                        Logger.e("-onReady Photo-", "Message: " + response.info.message);
//                        try
//                        {
////                        Picasso.with(this).load(response.data.getPhotoUrl()).into(imgProfile, new Callback()
////                        {
////                            @Override
////                            public void onSuccess()
////                            {
////                                Prefs.putString("profileImage", response.data.getPhotoUrl());
////                                headerModel.setProfileUrl(response.data.getPhotoUrl());
////                            }
////
////                            @Override
////                            public void onError()
////                            { }
////                        });
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//
//
////                    EventBus.getDefault().post(headerModel);
//
//                        sendPhotoSuccess = true;
//                        finishSendData();
//                    }
//                }
//
//                @Override
//                public void onError(String message)
//                {
//                    sendPhotoFailure = true;
//                    Logger.e("-onError Photo-", "Error: " + message);
//
//                    finishSendData();
//                }
//            });
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Logger.e("-Exception upload photo-", e.getMessage());
//        }
    }

    private void finishSendData(String photoErrorMessage)
    {
        if (sendPhotoSuccess && sendProfileSuccess)
        {
            btnConfirm.revertAnimation();
            btnConfirm.setClickable(true);

            EventBus.getDefault().post(headerModel);

            //success
            showToast(UserProfileActivity.this, "اطلاعات کاربری شما با موفقیت بروزرسانی شد.", R.color.green);
            finish();
        }
        else if (sendPhotoFailure && sendProfileFailure)
        {
//            hideLoading();
            //Fail
            if (!Tools.isNetworkAvailable(this))
            {
                Logger.e("-PhotoFailor-", "Error: " + "PhotoFailor");
                showError(this, "خطا در دریافت اطلاعات از سرور!");
            }
            else
            {
                showAlert(this, R.string.networkErrorMessage, R.string.networkError);
            }
            btnConfirm.revertAnimation();
            btnConfirm.setClickable(true);
        }
        else if (sendPhotoFailure && sendProfileSuccess)
        {
            btnConfirm.revertAnimation();
            btnConfirm.setClickable(true);
            String message = "اطلاعات شما با موفقیت ارسال گردید" + "\n" + "اما" + "\n" + "ارسال عکس ناموفق بود.";
            if (BuildConfig.DEBUG)
            {
                message = message + "\n" + photoErrorMessage;
            }
            showAlert(UserProfileActivity.this, message, R.string.error, false);
        }
//        else
//        {
//            btnConfirm.revertAnimation();
//            btnConfirm.setClickable(true);
//
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                isImageFileExist = true;
                imageUri = result.getUri();
                imgProfile.setImageBitmap(BitmapFactory.decodeFile(imageUri.getPath()));
                saveImage(BitmapFactory.decodeFile(imageUri.getPath()));
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }

    private void saveImage(Bitmap finalBitmap)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/traap");
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

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(WebServiceClass<GetProfileResponse> response)
    {
        if (response.info.statusCode == 200)
        {
            if (response.data.getPopularPlayer() != 0)
            {
                Prefs.putInt("favPlayerNo", response.data.getPopularPlayer());
            }

            etFirstName.setText(response.data.getFirstName());
            etLastName.setText(response.data.getLastName());

            if (!Prefs.getString("FULLName", "").replace(" ","").equalsIgnoreCase(""))
            {
                TrapConfig.HEADER_USER_NAME = Prefs.getString("FULLName", "");
            }
            else
            {
                TrapConfig.HEADER_USER_NAME = Prefs.getString("mobile", "");
            }

            try
            {
                if (!response.data.getBirthday().equalsIgnoreCase(""))
                {
//                    tvBirthDay.setText(getPersianDate(response.data.getBirthday()));
                    tvBirthDay.setText(response.data.getBirthday().replace("-", "/"));
                    imgBirthdayReset.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e)
            {

            }
            etNickName.setText(response.data.getEnglishName());
            if (response.data.getNationalCode().equalsIgnoreCase("0"))
            {
                etNationalCode.setText("");
            }
            else
            {
                etNationalCode.setText(response.data.getNationalCode());
            }
            etFirstNameUS.setText(response.data.getFirstNameUS());
            etLastNameUS.setText(response.data.getLastNameUS());
            etEmail.setText(response.data.getEmail());

            headerModel = new HeaderModel();
            headerModel.setPopularNo(response.data.getPopularPlayer());
            headerModel.setHeaderName(TrapConfig.HEADER_USER_NAME);

            try
            {
                Picasso.with(this).load(response.data.getPhotoUrl()).into(imgProfile, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                        Prefs.putString("profileImage", response.data.getPhotoUrl());
                        headerModel.setProfileUrl(response.data.getPhotoUrl());
                    }

                    @Override
                    public void onError()
                    {
                        Picasso.with(UserProfileActivity.this).load(R.drawable.ic_user_default).into(imgProfile);
                    }
                });
            }
            catch (Exception e)
            {
                if (Tools.isNetworkAvailable(this))
                {
                    Logger.e("-OnError-", "Error: " + e.getMessage());
//                    showError(this, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    showAlert(this, R.string.networkErrorMessage, R.string.networkError);
                }
            }

            spinnerGender.setSelection(response.data.getGender()-1);

            EventBus.getDefault().post(headerModel);

            if (headerModel.getPopularNo() != 0)
            {
                etPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
            }
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

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
        if (Tools.isNetworkAvailable(this))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(this, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(this, R.string.networkErrorMessage, R.string.networkError);
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay)
    {
        if (view.getTag().equals("CreateDate"))
        {
            PersianCalendar calendar = new PersianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);

            String createDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
            tvBirthDay.setText(createDate);
            imgBirthdayReset.setVisibility(View.VISIBLE);
        }
    }
}