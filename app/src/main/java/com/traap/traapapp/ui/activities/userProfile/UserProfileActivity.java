package com.traap.traapapp.ui.activities.userProfile;

import android.app.Activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import okhttp3.MultipartBody;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
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
import com.traap.traapapp.apiServices.model.profile.deleteProfile.DeleteProfileResponse;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;
import com.traap.traapapp.apiServices.model.profile.putProfile.request.SendProfileRequest;
import com.traap.traapapp.apiServices.model.profile.putProfile.response.SendProfileResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NationalCodeValidation;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Javad.Abadi on 10/7/2019.
 */
public class UserProfileActivity extends BaseActivity implements UserProfileActionView,
        OnAnimationEndListener, OnServiceStatus<WebServiceClass<GetProfileResponse>>, DatePickerDialog.OnDateSetListener
{
    private Toolbar mToolbar;
    private CircularProgressButton btnConfirm;
    private ClearableEditText etFirstName, etLastName, etFirstNameUS, etLastNameUS, etEmail, etNationalCode, etNickName;
    private ClearableEditText etPopularPlayer;
    private TextView tvMenu, tvUserName, tvHeaderPopularNo;
    private EditText tvBirthDay;
    private Spinner spinnerGender;
    private FloatingActionButton fabCapture;
    private ImageView imgProfile, imgBirthdayReset, imgBirthdaySet;

    private SlidingUpPanelLayout slidingUpPanelLayout;
    private RelativeLayout rlSelectImage, rlDeleteImage;

    private Animation animHideButton, animShowButton;

    private Boolean isImageFileExist = false;
    private Boolean isProfileImageAvailable = false;

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


    @SuppressLint("RestrictedApi")
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

        animHideButton = AnimationUtils.loadAnimation(UserProfileActivity.this, R.anim.hide_button);
        animShowButton = AnimationUtils.loadAnimation(UserProfileActivity.this, R.anim.show_button);

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nested);
        btnConfirm = findViewById(R.id.btnConfirm);
//        btnConfirm.setText("ارسال اطلاعات کاربری");

        slidingUpPanelLayout = findViewById(R.id.slidingLayout);
        rlDeleteImage = findViewById(R.id.rlDeleteImage);
        rlSelectImage = findViewById(R.id.rlSelectImage);

        imgBirthdayReset = findViewById(R.id.imgBirthdayReset);
        imgBirthdaySet = findViewById(R.id.imgBirthdaySet);
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

        etFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        etLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        etFirstNameUS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        etLastNameUS.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        etEmail.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        etNickName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});

        etPopularPlayer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        etNationalCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        etFirstName.requestFocus();

        genderStrList = new ArrayList<String>();
//        genderStrList.add("--انتخاب جنسیت--");
        genderStrList.add("مرد");
        genderStrList.add("زن");

        ArrayAdapter<String> adapterGenderStrList = new ArrayAdapter<String>(this,
                R.layout.my_spinner_item, genderStrList);
        adapterGenderStrList.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGenderStrList);

        initDate();
        getDataProfileUser();

        slidingUpPanelLayout.setFadeOnClickListener(v ->
        {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        });

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
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
        });


        btnConfirm.setOnClickListener(v ->
        {
            btnConfirm.startAnimation();
            btnConfirm.setClickable(false);


            uploadProfileData();
        });

        findViewById(R.id.rlBirthDay).setOnClickListener(v ->
        {
            //  pickerDialogDate.show(getSupportFragmentManager(), "CreateDate");
//            pickerDialogDate.show(getFragmentManager(), "CreateDate");
        });

        imgBirthdaySet.setOnClickListener(v ->
        {
            pickerDialogDate.show(getSupportFragmentManager(), "CreateDate");

        });

        imgBirthdayReset.setOnClickListener(v ->
        {
            tvBirthDay.setText("");
            imgBirthdayReset.setVisibility(View.GONE);

        });

        fabCapture.setOnClickListener(v ->
        {
            KeyboardUtils.forceCloseKeyboard(fabCapture);
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        });

        rlSelectImage.setOnClickListener(v -> getPermission());

        rlDeleteImage.setOnClickListener(v ->
        {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            if (isProfileImageAvailable)
            {
                MessageAlertDialog dialog = new MessageAlertDialog(UserProfileActivity.this, "",
                        "آیا از حذف عکس پروفایل خود اطمینان دارید؟",
                        true, "حذف", "انصراف", MessageAlertDialog.TYPE_MESSAGE,
                        new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                callDeletePhoto();
                            }

                            @Override
                            public void onCancelClick()
                            {
                            }
                        });
                dialog.setCancelable(true);
                dialog.show(getFragmentManager(), "alertDialog");
            }
            else
            {
                Picasso.with(UserProfileActivity.this).load(R.drawable.ic_user_default).into(imgProfile);
                rlDeleteImage.setAlpha(0.3f);
                rlDeleteImage.setActivated(false);
                rlDeleteImage.setEnabled(false);
                rlDeleteImage.setClickable(false);
            }
        });

        FrameLayout flLogoToolbar = findViewById(R.id.flLogoToolbar);

        flLogoToolbar.setOnClickListener(v ->
        {
            Intent returnIntent = new Intent();

            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        });
    }

    public boolean isValid(String text)
    {

        return text.matches("/^[1-4]\\d{3}\\/((0[1-6]\\/((3[0-1])|([1-2][0-9])|(0[1-9])))|((1[0-2]|(0[7-9]))\\/(30|31|([1-2][0-9])|(0[1-9]))))$/");
    }

    private void callDeletePhoto()
    {
        SingletonService.getInstance().sendProfileService().deleteProfilePhoto(new OnServiceStatus<WebServiceClass<DeleteProfileResponse>>()
        {
            @SuppressLint("RestrictedApi")
            @Override
            public void onReady(WebServiceClass<DeleteProfileResponse> response)
            {
                try
                {
                    if (response.info.statusCode != 200)
                    {
                        showError(UserProfileActivity.this, response.info.message);
                    }
                    else
                    {
                        showToast(UserProfileActivity.this, response.info.message, R.color.green);

                        Picasso.with(UserProfileActivity.this).load(R.drawable.ic_user_default).into(imgProfile);
                        Prefs.putString("profileImage", response.data.getPhotoUrl());

                        isProfileImageAvailable = false;

                        rlDeleteImage.setAlpha(0.3f);
                        rlDeleteImage.setActivated(false);
                        rlDeleteImage.setEnabled(false);
                        rlDeleteImage.setClickable(false);
                    }
                } catch (Exception e)
                {

                }
            }

            @Override
            public void onError(String message)
            {
                showError(UserProfileActivity.this, "خطای ارتباط با سرور!");
            }
        });

    }

    private void getPermission()
    {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

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
                                true, MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
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
        if (!TextUtils.isEmpty(tvBirthDay.getText().toString().replaceAll("_", "").replaceAll("/", "")))
        {
            if (tvBirthDay.getText().toString().replaceAll("_", "").length() != 10)
            {
                message = message + "تاریخ تولد،";
                err = false;
            }
            else
            {
                String[] date = tvBirthDay.getText().toString().replaceAll("_", "").split("/");
                Integer year = Integer.valueOf(date[0]);
                Integer month = Integer.valueOf(date[1]);
                Integer day = Integer.valueOf(date[2]);

                if (day < 1 || day > 31 || month < 1 || month > 12  || year < 1300 || year > 1399)
                {
                    message = message + "تاریخ تولد،";
                    err = false;
                }
            }
        }


        if (!err)
        {
            message = message + " باید اصلاح گردد.";
            showError(this, message);
        }
        return err;
    }

    private void getDataProfileUser()
    {
        showLoading();
        SingletonService.getInstance().getProfileService().getProfileService(this);
    }

    @Override
    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
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
//            hideLoading();
            hideSendDataLoading();
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
            } catch (Exception e)
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
                    sendPhotoFailure = false;
                }
            } catch (Exception e)
            {
                sendPhotoSuccess = true;
                sendPhotoFailure = false;
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

            request.setGender(spinnerGender.getSelectedItemPosition() + 1);

            SingletonService.getInstance().sendProfileService().sendProfileService(request,
                    new OnServiceStatus<WebServiceClass<SendProfileResponse>>()
                    {
                        @Override
                        public void onReady(WebServiceClass<SendProfileResponse> response)
                        {
                            try
                            {
                                btnConfirm.revertAnimation();
                                btnConfirm.setClickable(true);
                                if (response.info.statusCode != 200)
                                {
                                    showError(UserProfileActivity.this, response.info.message);
                                    sendProfileFailure = true;
                                    sendProfileSuccess = false;
                                    finishSendData("");
                                }
                                else
                                {
                                    //------------------------------------------
                                    Prefs.putString("firstName", etFirstName.getText().toString().trim());
                                    Prefs.putString("lastName", etLastName.getText().toString().trim());
                                    Prefs.putString("FULLName", etFirstName.getText().toString().trim() + " " + etLastName.getText().toString().trim());
                                    Prefs.putString("nickName", etNickName.getText().toString().trim());
                                    Prefs.putString("firstNameUS", etFirstNameUS.getText().toString().trim());
                                    Prefs.putString("lastNameUS", etLastNameUS.getText().toString().trim());
                                    Prefs.putString("email", etEmail.getText().toString().trim());
                                    Prefs.putInt("gender", spinnerGender.getSelectedItemPosition());

                                    if (tvBirthDay.getText() != null)
                                    {
                                        Prefs.putString("birthday", tvBirthDay.getText().toString().equalsIgnoreCase("") ?
                                                null :
                                                tvBirthDay.getText().toString().trim());
                                    }
                                    if (popularPlayer != 0)
                                    {
                                        Prefs.putInt("popularPlayer", popularPlayer);
                                    }
                                    else
                                    {
                                        Prefs.putInt("popularPlayer", 12);
                                    }
                                    Prefs.putString("nationalCode", etNationalCode.getText().toString().trim());

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
                                    sendProfileFailure = false;
                                    finishSendData("");
                                }
                            } catch (Exception e)
                            {
                            }

                        }

                        @Override
                        public void onError(String message)
                        {
                            btnConfirm.revertAnimation();
                            btnConfirm.setClickable(true);
                            sendProfileFailure = true;
                            sendProfileSuccess = false;
                            finishSendData("");
                            if (Tools.isNetworkAvailable(UserProfileActivity.this))
                            {
                                Logger.e("-OnError-", "Error: " + message);
                                showError(UserProfileActivity.this, "خطا در دریافت اطلاعات از سرور!");
                            }
                            else
                            {
                                ShowAlertFailure(UserProfileActivity.this, getString(R.string.networkErrorMessage), getString(R.string.networkError), false);
                            }
                        }
                    });
        }

    }

    @Override
    public void showSendDataLoading()
    {
        btnConfirm.startAnimation();
        btnConfirm.setClickable(false);
    }

    @Override
    public void hideSendDataLoading()
    {
        btnConfirm.revertAnimation(UserProfileActivity.this);
        btnConfirm.setClickable(true);
    }

    private void sendProfilePhoto()
    {
        //---------------------new FAN--------------------------
        Logger.e("--imagePath--", userPic.getAbsolutePath());
        Logger.e("--url--", Const.BASEURL + Const.SEND_PROFILE_PHOTO);
        Logger.e("--token--", Prefs.getString("accessToken", ""));

        AndroidNetworking.upload(Const.BASEURL + Const.SEND_PROFILE_PHOTO)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addHeaders("Authorization", Prefs.getString("accessToken", ""))
//                .setOkHttpClient(client.build())
                .addMultipartFile("photo", userPic)
                .setTag("uploadProfile")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener((bytesUploaded, totalBytes) ->
                {
                    Logger.e("-Upload Progress-", "in Progress #");
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
//                    public void showErrorMessage(ANError anError)
//                    {
//                        sendPhotoFailure = true;
//                        Logger.e("-showErrorMessage Photo1-", "Error: " + anError.getErrorDetail());
//                        Logger.e("-showErrorMessage Photo2-", "Error: " + anError);
//
//                        finishSendData();
//                    }
//                });
                .getAsString(new StringRequestListener()
                {
                    @SuppressLint("RestrictedApi")
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

                                isProfileImageAvailable = true;

                                rlDeleteImage.setAlpha(1f);
                                rlDeleteImage.setActivated(true);
                                rlDeleteImage.setEnabled(true);
                                rlDeleteImage.setClickable(true);

                                sendPhotoSuccess = true;
                                sendPhotoFailure = false;
                                finishSendData("");
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        sendPhotoSuccess = true;
                        sendPhotoFailure = false;
                        finishSendData("");
                    }

                    @Override
                    public void onError(ANError anError)
                    {
                        sendPhotoFailure = true;
                        sendPhotoSuccess = false;

                        if (Tools.isNetworkAvailable(UserProfileActivity.this))
                        {
                            Logger.e("-showErrorMessage Photo1-", "Error: " + anError.getErrorDetail());
                            Logger.e("-showErrorMessage Photo2-", "Error: " + anError);

                        }
                        else
                        {
                            ShowAlertFailure(UserProfileActivity.this, getString(R.string.networkErrorMessage), getString(R.string.networkError), false);
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
////                            public void showErrorMessage()
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
//                public void showErrorMessage(String message)
//                {
//                    sendPhotoFailure = true;
//                    Logger.e("-showErrorMessage Photo-", "Error: " + message);
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
            ShowAlertSuccess(UserProfileActivity.this, "اطلاعات کاربری شما با موفقیت بروزرسانی شد.", "", false);

//            finish();

        }
        else if (sendPhotoFailure && sendProfileFailure)
        {
//            hideLoading();
            //Fail
            if (Tools.isNetworkAvailable(this))
            {
                Logger.e("-PhotoFailor-", "Error: " + "PhotoFailor");
                showError(this, "خطا در دریافت اطلاعات از سرور!");
            }
            else
            {
                ShowAlertFailure(UserProfileActivity.this, getString(R.string.networkErrorMessage), getString(R.string.networkError), false);
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
            ShowAlertFailure(UserProfileActivity.this, message, getString(R.string.error), false);
        }
//        else
//        {
//            btnConfirm.revertAnimation();
//            btnConfirm.setClickable(true);
//
//        }
    }

    @SuppressLint("RestrictedApi")
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

                rlDeleteImage.setAlpha(1f);
                rlDeleteImage.setActivated(true);
                rlDeleteImage.setEnabled(true);
                rlDeleteImage.setClickable(true);
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
        if (userPic.exists())
        {
            userPic.delete();
        }
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

    @SuppressLint("RestrictedApi")
    @Override
    public void onReady(WebServiceClass<GetProfileResponse> response)
    {
        try
        {
            hideLoading();

            if (response.info.statusCode == 200)
            {
                if (response.data.getPopularPlayer() != 0)
                {
                    Prefs.putInt("favPlayerNo", response.data.getPopularPlayer());
                }
                try
                {
                    etFirstName.setText(response.data.getFirstName());
                    etLastName.setText(response.data.getLastName());

                    if (!Prefs.getString("FULLName", "").replace(" ", "").equalsIgnoreCase(""))
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
                    } catch (Exception e)
                    {
                        tvBirthDay.setText(response.data.getBirthday().replace("-", "/"));
                        imgBirthdayReset.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e)
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
                    if (!response.data.getPhotoUrl().contains("default_avatar.png"))
                    {
                        Picasso.with(this).load(response.data.getPhotoUrl()).into(imgProfile, new Callback()
                        {
                            @Override
                            public void onSuccess()
                            {
                                Prefs.putString("profileImage", response.data.getPhotoUrl());
                                headerModel.setProfileUrl(response.data.getPhotoUrl());

                                isProfileImageAvailable = true;

                                rlDeleteImage.setAlpha(1f);
                                rlDeleteImage.setActivated(true);
                                rlDeleteImage.setEnabled(true);
                                rlDeleteImage.setClickable(true);
                            }

                            @Override
                            public void onError()
                            {
                                Picasso.with(UserProfileActivity.this).load(R.drawable.ic_user_default).into(imgProfile);
                            }
                        });
                    }
                    else
                    {
                        Picasso.with(UserProfileActivity.this).load(R.drawable.ic_user_default).into(imgProfile);

                        rlDeleteImage.setAlpha(0.3f);
                        rlDeleteImage.setActivated(false);
                        rlDeleteImage.setEnabled(false);
                        rlDeleteImage.setClickable(false);
                    }
                } catch (Exception e)
                {
                    if (Tools.isNetworkAvailable(this))
                    {
                        Logger.e("-OnError-", "Error: " + e.getMessage());
//                    showError(this, "خطا در دریافت اطلاعات از سرور!");
                    }
                    else
                    {
                        ShowAlertFailure(UserProfileActivity.this, getString(R.string.networkErrorMessage), getString(R.string.networkError), false);
                    }
                }

                spinnerGender.setSelection(response.data.getGender() - 1);

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
        } catch (
                Exception e)

        {
        }

    }

    @Override
    public void onError(String message)
    {
        hideLoading();

        if (Tools.isNetworkAvailable(this))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(this, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            ShowAlertFailure(UserProfileActivity.this, getString(R.string.networkErrorMessage), getString(R.string.networkError), false);
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay)
    {
        if (view.getTag().equals("CreateDate"))
        {
            PersianCalendar calendar = new PersianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);
            String day;
            String month;

            if (String.valueOf(dayOfMonth).length() == 1)
            {
                day = "0" + dayOfMonth;
            }
            else
            {
                day = String.valueOf(dayOfMonth);

            }

            if (String.valueOf(monthOfYear + 1).length() == 1)
            {
                month = "0" + (monthOfYear + 1);
            }
            else
            {
                month = String.valueOf(monthOfYear + 1);

            }

            String createDate = year + "/" + (month) + "/" + day;
            tvBirthDay.setText(createDate);
            imgBirthdayReset.setVisibility(View.VISIBLE);

        }
    }
}