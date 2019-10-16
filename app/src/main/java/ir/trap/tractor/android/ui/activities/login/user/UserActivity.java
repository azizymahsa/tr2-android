package ir.trap.tractor.android.ui.activities.login.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.base.GoToActivity;
import ir.trap.tractor.android.ui.activities.main.MainActivity;
import library.android.eniac.base.BaseActivity;

/**
 * Created by MahtabAzizi on 10/7/2019.
 */
public class UserActivity extends BaseActivity implements UserView, OnAnimationEndListener, View.OnClickListener {
   // private UserPresenterImpl presenter;
    private RelativeLayout rlImage;
    private CircularProgressButton btnConfirm;
    private EditText etFirstName, etLastName, etInvite;
    private TextView tvMenu;
    private ImageView ivProfile;
    private File userPic;
    private boolean isChangePic = false;
    private FrameLayout flLogoToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       // presenter = new UserPresenterImpl(this, new UpdateUserImpl());
        btnConfirm = findViewById(R.id.btnConfirm);
        etFirstName = findViewById(R.id.etFirstName);
        ivProfile = findViewById(R.id.ivProfile);
        etInvite = findViewById(R.id.etInvite);

        flLogoToolbar = findViewById(R.id.flLogoToolbar);
        etLastName = findViewById(R.id.etLastName);
        rlImage = findViewById(R.id.rlImage);
        btnConfirm.setOnClickListener(this);
     //   btnConfirm.setOnClickListener(presenter);
       // rlImage.setOnClickListener(presenter);
        //presenter.details(etFirstName, etLastName, etInvite);
        flLogoToolbar.setVisibility(View.GONE);
        Prefs.putBoolean("isFirstLogin", true);
        setDataProfileUser();

    }

    private void setDataProfileUser() {

        etFirstName.setText(Prefs.getString("firstName",""));
        etLastName.setText(Prefs.getString("lastName",""));
        etInvite.setText(Prefs.getString("keyInvite",""));



    /*    Prefs.putString("englishName", profile.getEnglishName());
        if (profile.getBirthday()!=null) {
            Prefs.putString("birthday", profile.getBirthday().toString());
        }
        if (profile.getPopularPlayer()!=null){
            Prefs.putInt("popularPlayer", profile.getPopularPlayer());
        }
        Prefs.putString("nationalCode", profile.getNationalCode());*/

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
        btnConfirm.revertAnimation(UserActivity.this);
        btnConfirm.setClickable(true);
    }

    @Override
    public void onError(String message, String name, boolean b)
    {
       // showToast(this, message, R.color.red);

    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setText(getString(R.string.send_code));
        btnConfirm.setBackground(ContextCompat.getDrawable(this, R.drawable.background_button_login));
    }

    @Override
    public void startActivity(GoToActivity activity)
    {
        switch (activity)
        {
            case MainActivity:
                startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                break;
            case PassCodeActivity:
              //  startActivityForResult(new Intent(this, PassCodeActivity.class), 22);
                break;
        }
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
       // uploadMultipart(this);

    }

/*    public void uploadMultipart(final Context context)
    {
        try
        {

            //---------------------new FAN--------------------------
            AndroidNetworking.upload(Const.BASEURL + Const.UploadImageProfile +
                    "?userId=" + Prefs.getInt("userId", 0))
                    .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                    .addHeaders("Token",Prefs.getString("serverToken",""))
//                    .addHeaders(SingletonDiba.getInstance().getDIBA_NAME(), SingletonDiba.getInstance().getDIBA_KEY())
//                    .addHeaders(SingletonDiba.getInstance().getDIBA_USER(), Utility.decryption(Prefs.getString(Utility.encryption(SingletonDiba.getInstance().getPASS_KEY()), "")))
                    .addMultipartFile("file", userPic)
                    .setTag("uploadProfile")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener()
                    {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes)
                        {
                            Log.e("-Upload Progress-",  "in Progress #");
                        }
                    })
                    .getAsString(new StringRequestListener()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            try
                            {
                                showToast(UserActivity.this, "اطلاعات شما با موفقیت بروزرسانی شد.", R.color.green);
//                                Tools.showToast(UserActivity.this, "اطلاعات شما با موفقیت بروزرسانی شد.");
//                                Log.e("--Upload Response--", response);
                                btnConfirm.revertAnimation(UserActivity.this);
                                btnConfirm.setClickable(true);

                                JSONObject object = new JSONObject(response);

                                Prefs.putString("userImage", object.getString("Url"));
                                EventBus.getDefault().post(object.getString("Url"));
                                new Handler().postDelayed(() ->
                                                finish()
                                        , 2000);

//                                Log.e("--userImage--", Prefs.getString("userImage", ""));
//                                Log.e("userImage", response.getUrl() + " ###");
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                btnConfirm.revertAnimation(UserActivity.this);
                                btnConfirm.setClickable(true);
                                showToast(UserActivity.this, "خطایی رخ داده است!", R.color.red);
                                Log.e("--Upload Exception--", e.getMessage() + " #");
                            }
                        }

                        @Override
                        public void onError(ANError anError)
                        {
                            Log.e("--Upload Error--", anError.getMessage().toString());
                            btnConfirm.revertAnimation(UserActivity.this);
                            btnConfirm.setClickable(true);
                            showToast(UserActivity.this, "خطایی رخ داده است.", R.color.red);
                        }
                    });
            //---------------------new FAN--------------------------

//            UploadNotificationConfig config = new UploadNotificationConfig();
//            config.getCompleted().autoClear = true;
//
//            new MultipartUploadRequest(context, " https://rest.loyalbank.app/apidiba/Users/UploadImage?userId=" + Prefs.getInt("userId", 0))
//                    .addFileToUpload(userPic.getAbsolutePath(), "name")
//                    .addHeader(SingletonDiba.getInstance().getDIBA_NAME(), SingletonDiba.getInstance().getDIBA_KEY())
//                    .addHeader(SingletonDiba.getInstance().getDIBA_USER(), Utility.decryption(Prefs.getString(Utility.encryption(SingletonDiba.getInstance().getPASS_KEY()), "")))
//
//                    .setMaxRetries(2)
//                    .setNotificationConfig(config)
//
//                    .setDelegate(new UploadStatusDelegate()
//                    {
//                        @Override
//                        public void onProgress(Context context, UploadInfo uploadInfo)
//                        {
//
//
//                        }
//
//                        @Override
//                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
//                                            Exception exception)
//                        {
//
//                        }
//
//                        @Override
//                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse)
//                        {
//                            try
//                            {
//
//                                JSONArray jsonArray = new JSONArray(serverResponse.getBodyAsString());
//                                JSONObject object = jsonArray.getJSONObject(0);
//                                Prefs.putString("userImage", "http://91.241.92.15:1051/" + object.getString("Url"));
//
//
//                            } catch (JSONException e)
//                            {
//                                e.printStackTrace();
//
//                            }
//
//                            // your code here
//                            // if you have mapped your server response to a POJO, you can easily get it:
//                            // YourClass obj = new Gson().fromJson(serverResponse.getBodyAsString(), YourClass.class);
//
//                        }
//
//                        @Override
//                        public void onCancelled(Context context, UploadInfo uploadInfo)
//                        {
//                            // your code here
//                        }
//                    })
//                    .startUpload();
        } catch (Exception exc)
        {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data))
        {
            // Get a list of picked images
            Image image = ImagePicker.getFirstImageOrNull(data);
            if (image != null)
            {
                ivProfile.setImageBitmap(BitmapFactory.decodeFile(image.getPath()));
                saveImage(BitmapFactory.decodeFile(image.getPath()));
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 22)
        {
            String pass = data.getStringExtra("newPass");

          //  presenter.onConfirmPassword(pass);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnConfirm:
                Intent intent = new Intent(this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;

        }
    }
}