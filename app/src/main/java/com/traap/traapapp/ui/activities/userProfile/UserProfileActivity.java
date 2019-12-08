package com.traap.traapapp.ui.activities.userProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import okhttp3.MultipartBody;
import saman.zamani.persiandate.PersianDate;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.profile.getProfile.response.GetProfileResponse;
import com.traap.traapapp.apiServices.model.profile.putProfile.response.SendProfileResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NationalCodeValidation;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.PrepareImageFilePart;
//import com.traap.traapapp.utilities.calendar.materialdatetimepicker.com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


import org.greenrobot.eventbus.EventBus;

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

    private PersianCalendar currentDate;
    private Uri imageUri;

    private DatePickerDialog pickerDialogDate;

    Integer popularPlayer = 12;

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
        genderStrList.add("--انتخاب جنسیت--");
        genderStrList.add("زن");
        genderStrList.add("مرد");

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
            openImageChooser();
        });
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
        if (!etEmail.getText().toString().trim().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+") && !etEmail.getText().toString().equalsIgnoreCase(""))
        {
            message = message + "ایمیل،";
            err = false;
//            etEmail.setError("ایمیل درست نیست!");
        }

        if (!etFirstName.getText().toString().trim().matches("[ا-ی]+") && !etFirstName.getText().toString().equalsIgnoreCase(""))
        {
            message = message + "نام فارسی،";
            err = false;
//            etFirstNameUS.setError("نام انگلیسی درست نیست!");
        }

        if (!etLastName.getText().toString().trim().matches("[ا-ی]+") && !etLastName.getText().toString().equalsIgnoreCase(""))
        {
            message = message + "نام خانوادگی فارسی،";
            err = false;
//            etFirstNameUS.setError("نام انگلیسی درست نیست!");
        }

        if (!etFirstNameUS.getText().toString().trim().matches("[a-zA-Z]+") && !etFirstNameUS.getText().toString().equalsIgnoreCase(""))
        {
            message = message + "نام انگلیسی،";
            err = false;
//            etFirstNameUS.setError("نام انگلیسی درست نیست!");
        }

        if (!etLastNameUS.getText().toString().trim().matches("[a-zA-Z]+") && !etLastNameUS.getText().toString().equalsIgnoreCase(""))
        {
            message = message + "نام خانوادگی انگلیسی،";
            err = false;
//            etLastNameUS.setError("نام خانوادگی انگلیسی درست نیست!");
        }

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
//        getDataProfileUserFromCatch();
    }

    private void getDataProfileUserFromCatch()
    {
        if (Prefs.contains("firstName"))
        {
            etFirstName.setText(Prefs.getString("firstName", ""));
        }
        if (Prefs.contains("lastName"))
        {
            etLastName.setText(Prefs.getString("lastName", ""));
        }
        if (Prefs.contains("nickName"))
        {
            etNickName.setText(Prefs.getString("nickName", ""));
        }

        if (Prefs.contains("firstNameUS"))
        {
            etFirstNameUS.setText(Prefs.getString("firstNameUS", ""));
        }

        if (Prefs.contains("lastNameUS"))
        {
            etLastNameUS.setText(Prefs.getString("lastNameUS", ""));
        }

        if (Prefs.contains("email"))
        {
            etEmail.setText(Prefs.getString("email", ""));
        }

        if (Prefs.contains("gender"))
        {
            spinnerGender.setSelection(Prefs.getInt("gender", 0));
        }
        else
        {
            spinnerGender.setSelection(2);
        }

        if (Prefs.contains("birthday"))
        {
            tvBirthDay.setText(Prefs.getString("birthday", ""));
        }
        if (Prefs.contains("popularPlayer"))
        {
            if (Prefs.getInt("popularPlayer", 12) != 12)
            {
                etPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
                popularPlayer = Prefs.getInt("popularPlayer", 12);
            }
        }
        if (Prefs.contains("nationalCode"))
        {
            if (!Prefs.getString("nationalCode", "").equalsIgnoreCase("") &&
                    !Prefs.getString("nationalCode", "").equalsIgnoreCase("0"))
            {
                etNationalCode.setText(Prefs.getString("nationalCode", ""));
            }
        }
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

//            SendProfileRequest request = new SendProfileRequest();
//            request.setPopularPlayer(12);

            try
            {
                part = PrepareImageFilePart.prepareFilePart(userPic.getName(), userPic);
            }
            catch (Exception e)
            {
                part = null;
            }
            Map<String, Object> fields = new HashMap<>();
            fields.put("first_name", etFirstName.getText().toString().trim());
            fields.put("last_name", etLastNameUS.getText().toString().trim());
            fields.put("national_code", etNationalCode.getText().toString());
//            fields.put("birthday", tvBirthDay.getText().toString().equalsIgnoreCase("") ? null :
//                    getGrgDate(tvBirthDay.getText().toString()));
//                    tvBirthDay.getText().toString());
//            fields.put("birthday", getGrgDate(tvBirthDay.getText().toString()));
            fields.put("birthday", tvBirthDay.getText().toString());
            fields.put("english_name", etNickName.getText().toString().trim());
            fields.put("key_invite", 0);
            fields.put("sex", spinnerGender.getSelectedItemPosition());
            fields.put("first_english_name", etFirstNameUS.getText().toString().trim());
            fields.put("last_english_name",  etLastNameUS.getText().toString().trim());
            fields.put("email", etEmail.getText().toString().trim());

            SingletonService.getInstance().sendProfileService().sendProfileService(fields, part,
                    new OnServiceStatus<WebServiceClass<SendProfileResponse>>()
                    {
                        @Override
                        public void onReady(WebServiceClass<SendProfileResponse> response)
                        {
                            btnConfirm.revertAnimation();
                            btnConfirm.setClickable(true);

                            if (response.info.statusCode != 200)
                            {
                                showError(UserProfileActivity.this, response.info.message);
                                hideLoading();
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

                                HeaderModel headerModel = new HeaderModel();
                                headerModel.setPopularNo(popularPlayer);
                                headerModel.setHeaderName(TrapConfig.HEADER_USER_NAME);
                                EventBus.getDefault().post(headerModel);

//                                if (headerModel.getPopularNo() != 0)
//                                {
//                                    tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
//                                }
//                                tvUserName.setText(TrapConfig.HEADER_USER_NAME);

                                //------------------------------------------
//                                Prefs.putString("firstName", response.data.getFirstName());
//                                Prefs.putString("lastName", response.data.getLastName());
//                                Prefs.putString("FULLName", response.data.getFirstName() + " " + response.data.getLastName());
//                                Prefs.putString("nickName", response.data.getEnglishName());
//                                if (response.data.getBirthday() != null)
//                                {
//                                    Prefs.putString("birthday", response.data.getBirthday());
//                                }
//                                if (response.data.getPopularPlayer() != 0)
//                                {
//                                    Prefs.putInt("popularPlayer", response.data.getPopularPlayer());
//                                }
//                                Prefs.putString("nationalCode", response.data.getNationalCode());
//
//                                if (!Prefs.getString("FULLName", "").trim().replace(" ", "").equalsIgnoreCase(""))
//                                {
//                                    TrapConfig.HEADER_USER_NAME = Prefs.getString("FULLName", "");
//                                }
//                                else
//                                {
//                                    TrapConfig.HEADER_USER_NAME = Prefs.getString("mobile", "");
//                                }
//
//                                HeaderModel headerModel = new HeaderModel();
//                                headerModel.setPopularNo(response.data.getPopularPlayer());
//                                headerModel.setHeaderName(TrapConfig.HEADER_USER_NAME);
//                                EventBus.getDefault().post(headerModel);
//
//                                if (headerModel.getPopularNo() != 0)
//                                {
//                                    etPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
//                                }
//                                tvUserName.setText(TrapConfig.HEADER_USER_NAME);


                                showToast(UserProfileActivity.this, "اطلاعات کاربری شما با موفقیت بروزرسانی شد.", R.color.green);
                                finish();
                            }
                        }

                        @Override
                        public void onError(String message)
                        {
                            hideLoading();
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


    private String getGrgDate(String dateStr)
    {
        String spliter = "/";
        String[] date = dateStr.split(spliter);

        PersianDate persianDate = new PersianDate();
        persianDate.setShYear(Integer.parseInt(date[0]));
        persianDate.setShMonth(Integer.parseInt(date[1]));
        persianDate.setShDay(Integer.parseInt(date[2]));

        String month = String.valueOf(persianDate.getGrgMonth());
        String day = String.valueOf(persianDate.getGrgDay());

        if (persianDate.getGrgMonth() < 10)
        {
            month = "0" + month;
        }
        if (persianDate.getGrgDay() < 10)
        {
            day = "0" + day;
        }

        String newDate = persianDate.getGrgYear() + "-" + month + "-" + day;

        return newDate;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data))
//        {
//            // Get a list of picked images
//            Image image = ImagePicker.getFirstImageOrNull(data);
//            if (image != null)
//            {
////                imgProfile.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
////                saveImage(BitmapFactory.decodeFile(image.getPath()));
//            }
//        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
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

//            etFirstName.setText(response.data.getFirstName().replace("\"\"", ""));
            etFirstName.setText(response.data.getFirstName());
            etLastName.setText(response.data.getLastName());

            if (!Prefs.getString("FULLName", "").trim().replace(" ", "").equalsIgnoreCase(""))
            {
                TrapConfig.HEADER_USER_NAME = Prefs.getString("FULLName", "");
            }
            else
            {
                TrapConfig.HEADER_USER_NAME = Prefs.getString("mobile", "");
            }

            HeaderModel headerModel = new HeaderModel();
            headerModel.setPopularNo(response.data.getPopularPlayer());
            headerModel.setHeaderName(TrapConfig.HEADER_USER_NAME);
            EventBus.getDefault().post(headerModel);

            if (headerModel.getPopularNo() != 0)
            {
                etPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
            }
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            //  etPopularPlayer.setText(response.data.getPopularPlayer().toString());
            try
            {
                if (!response.data.getBirthday().equalsIgnoreCase(""))
                {
                    tvBirthDay.setText("" + response.data.getBirthday());
                }
            }
            catch (Exception e)
            {

            }
            etNickName.setText(response.data.getEnglishName());
            etNationalCode.setText(response.data.getNationalCode().equals("0") ? "" :
                    response.data.getNationalCode());
            etFirstNameUS.setText(response.data.getFirstNameUS());
            etLastNameUS.setText(response.data.getLastNameUS());
            try
            {
                etEmail.setText(response.data.getEmail());
            }
            catch (Exception e)
            {

            }

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

//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
//    {
//        if (view.getTag().equals("CreateDate"))
//        {
//            PersianCalendar calendar = new PersianCalendar();
//            calendar.set(year, monthOfYear, dayOfMonth);
////            pickerDialogDate.setPersianCalendar(calendar);
////            pickerDialogDate.setMaxDate(currentDate);
//
////            String createDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//            String createDate = year + "/" + dayOfMonth + "/" + (monthOfYear + 1);
////            viewModel.updateTvCreateDate(createDate);
//            tvBirthDay.setText(createDate);
//            imgBirthdayReset.setVisibility(View.VISIBLE);
//
////            this.year = year;
////            this.month = monthOfYear;
////            this.day = dayOfMonth;
//
////            PersianCalendar calendar1 = new PersianCalendar();
////            PersianCalendar calendar2 = new PersianCalendar();
////            calendar1.set(year, monthOfYear, dayOfMonth);
//
////            pickerDialogDate.setPersianCalendar(calendar1);
//
//
//        }
//    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay)
    {
        if (view.getTag().equals("CreateDate"))
        {
            PersianCalendar calendar = new PersianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);
//            pickerDialogDate.setPersianCalendar(calendar);
//            pickerDialogDate.setMaxDate(currentDate);

//            String createDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            String createDate = year + "/" + dayOfMonth + "/" + (monthOfYear + 1);
//            viewModel.updateTvCreateDate(createDate);
            tvBirthDay.setText(createDate);
            imgBirthdayReset.setVisibility(View.VISIBLE);

//            this.year = year;
//            this.month = monthOfYear;
//            this.day = dayOfMonth;

//            PersianCalendar calendar1 = new PersianCalendar();
//            PersianCalendar calendar2 = new PersianCalendar();
//            calendar1.set(year, monthOfYear, dayOfMonth);

//            pickerDialogDate.setPersianCalendar(calendar1);


        }
    }
}