package ir.traap.tractor.android.ui.activities.userProfile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.activities.main.MainActivity;
import ir.traap.tractor.android.ui.base.GoToActivity;
import ir.traap.tractor.android.utilities.ClearableEditText;
import ir.traap.tractor.android.utilities.Tools;
import library.android.eniac.base.BaseActivity;

/**
 * Created by MahtabAzizi on 10/7/2019.
 */
public class UserProfileActionProfileActivity extends BaseActivity implements UserProfileActionView,
        OnAnimationEndListener, View.OnClickListener
{
    private Toolbar mToolbar;
    private CircularProgressButton btnConfirm;
    private EditText etFirstName, etLastName, etInvite;
    private ClearableEditText etPopularPlayer;
    private TextView tvMenu;
    //    private ImageView ivProfile;
    private File userPic;
    private boolean isChangePic = false;
    private FrameLayout flLogoToolbar;
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
        tvTitle.setText("مشخصات پروفایل کاربری");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        btnConfirm = findViewById(R.id.btnConfirm);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPopularPlayer = findViewById(R.id.etPopularPlayer);
        etInvite = findViewById(R.id.etInvite);

        flLogoToolbar = findViewById(R.id.flLogoToolbar);
        etPopularPlayer.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });

        btnConfirm.setOnClickListener(this);
        flLogoToolbar.setVisibility(View.GONE);

        setDataProfileUser();

    }

    private void setDataProfileUser()
    {

        etFirstName.setText(Prefs.getString("firstName", ""));
        etLastName.setText(Prefs.getString("lastName", ""));
        // etInvite.setText(Prefs.getString("keyInvite", ""));



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
        btnConfirm.revertAnimation(UserProfileActionProfileActivity.this);
        btnConfirm.setClickable(true);
    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setText(getString(R.string.send_code));
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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnConfirm:

                popularPlayer = etPopularPlayer.getText().toString();
                if (popularPlayer.matches(""))
                {
                    Tools.showToast(this,"وارد کردن شماره بازیکن محبوب اجباری می باشد." ,R.color.red);

                }else
                {

                    Prefs.putString("PopularPlayer",popularPlayer);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                break;

        }
    }
}