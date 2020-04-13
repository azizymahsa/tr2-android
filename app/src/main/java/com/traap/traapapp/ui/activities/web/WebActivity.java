package com.traap.traapapp.ui.activities.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.Utility;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebActivity extends BaseActivity
{
    private WebView webView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;
    private View rlShirt;
    private String TAG = "WebActivity";
//

    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        Log.e("testt222", "inja");

        initView();
        try
        {
            String postData = "";
            webView = findViewById(R.id.webView);
            if (Build.VERSION.SDK_INT >= 19)
            {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            else
            {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            

            String url = getIntent().getStringExtra("URL");
           /* if (getIntent().hasExtra("Title"))
            {
                if (getIntent().getStringExtra("Title").contains("بیمه"))
                {
                    postData = "userToken=" + URLEncoder.encode(getIntent().getStringExtra("TOKEN"), "UTF-8")
                            + "&businessToken=" + URLEncoder.encode(getIntent().getStringExtra("bimeh_api_key"), "UTF-8")
                            + "&redirectUrl=" + URLEncoder.encode(getIntent().getStringExtra("bimeh_call_back"), "UTF-8")
                            + "&redirectSuccess=" + URLEncoder.encode(url, "UTF-8")
                    ;
                    url = getIntent().getStringExtra("bimeh_base_url");
                }
                else if (!getIntent().getStringExtra("Title").contains("الو"))
                {
                    postData = "auth=" + URLEncoder.encode(getIntent().getStringExtra("TOKEN"), "UTF-8");
                }

            }*/

            webView.setInitialScale(0);
            webView.setVerticalScrollBarEnabled(false);

            //postData="Authorization="+ URLEncoder.encode(Prefs.getString("accessToken",""), "UTF-8");
            //webView.postUrl(url, postData.getBytes());

            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Authorization",Prefs.getString("accessToken",""));
            webView.loadUrl(url,extraHeaders);

            WebSettings settings = webView.getSettings();
            initWebViewSettings();
            // Enable AppCache
            // Fix for CB-2282
            webView.getSettings().setAppCacheMaxSize(5 * 1048576);
            //   settings.setAppCachePath(databasePath);
            webView.getSettings().setAppCacheEnabled(true);

            // Enable responsive layout
            webView.getSettings().setUseWideViewPort(true);


            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.OFF);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setUserAgentString("Android Mozilla/5.0 AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
            webView.getSettings().setAllowFileAccess(true);
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().supportZoom();



            webView.setDownloadListener(new DownloadListener() {

                @Override
                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    Log.e("test11222333", url );
                /*    if (url.contains(".pdf")){
                        Utility.openUrlCustomTab(WebActivity.this,"docs.google.com/viewer?url="+url.replace("https://",""));
                    }*/

                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                    catch(ActivityNotFoundException e) {
                        // Chrome is not installed
                        DownloadManager.Request request = new DownloadManager.Request(
                                Uri.parse(url));

                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ");
                        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                        dm.enqueue(request);
                        Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                                Toast.LENGTH_LONG).show();
                    }
                   /* DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Name of your downloadble file goes here, example: Mathematics II ");
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();*/

                }
            });


            // Zoom out if the content width is greater than the width of the viewport
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setWebViewClient(new WebViewClient()
            {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon)
                {
                    super.onPageStarted(view, url, favicon);
                    showLoading();
                    Log.e("test11222", url );
              /*      Log.e("test11222", url );
                    if (url.contains(".pdf")){
                        Utility.openUrlCustomTab(WebActivity.this,url);
                    }*/

                }

                @Override
                public void onPageCommitVisible(WebView view, String url)
                {
                    super.onPageCommitVisible(view, url);
                    hideLoading();

                }

                @Override
                public void onPageFinished(WebView view, String url)
                {

                    hideLoading();

                    super.onPageFinished(view, url);

                }
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String temp  = request.getUrl().toString();
                    if(temp.isEmpty()){
                        Log.e("test", "No url returned!");
                    }else{
                        Log.e("test", temp);
                    }
                    return  false;
                }
            });
            webView.setWebChromeClient(new ChromeClient());
        } catch (Exception e)
        {
            e.printStackTrace();
            hideLoading();
            showError(this, "خطا در دریافت اطلاعات از سرور!");
            finish();
        }
    }

    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    private void initView()
    {
        try
        {
            //toolbar
            tvTitle = findViewById(R.id.tvTitle);


            tvUserName = findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            imgMenu = findViewById(R.id.imgMenu);
            imgMenu.setVisibility(View.GONE);

            tvPopularPlayer = findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            imgBack = findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                finish();
            });
            rlShirt = findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(v -> startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100)
            );
            String title = getIntent().getStringExtra("Title");

            tvTitle.setText(title);
            FrameLayout flLogoToolbar = findViewById(R.id.flLogoToolbar);
            flLogoToolbar.setOnClickListener(v -> {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            });


        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void html(String html)
    {


        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");

        webView.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @SuppressWarnings("deprecation")
    private void initWebViewSettings()
    {
        webView.setInitialScale(0);
        webView.setVerticalScrollBarEnabled(false);
        // Enable JavaScript
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        // Set the nav dump for HTC 2.x devices (disabling for ICS, deprecated entirely for Jellybean 4.2)
        try
        {
            Method gingerbread_getMethod = WebSettings.class.getMethod("setNavDump", new Class[]{boolean.class});

            String manufacturer = android.os.Build.MANUFACTURER;
            // Log.d(TAG, "CordovaWebView is running on device made by: " + manufacturer);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB &&
                    android.os.Build.MANUFACTURER.contains("HTC"))
            {
                gingerbread_getMethod.invoke(settings, true);
            }
        } catch (NoSuchMethodException e)
        {
            Log.d(TAG, "We are on a modern version of Android, we will deprecate HTC 2.3 devices in 2.8");
        } catch (IllegalArgumentException e)
        {
            Log.d(TAG, "Doing the NavDump failed with bad arguments");
        } catch (IllegalAccessException e)
        {
            Log.d(TAG, "This should never happen: IllegalAccessException means this isn't Android anymore");
        } catch (InvocationTargetException e)
        {
            Log.d(TAG, "This should never happen: InvocationTargetException means this isn't Android anymore.");
        }

        //We don't save any form data in the application
        settings.setSaveFormData(false);
        settings.setSavePassword(false);

        // Jellybean rightfully tried to lock this down. Too bad they didn't give us a whitelist
        // while we do this
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
        {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            settings.setMediaPlaybackRequiresUserGesture(false);
        }
        // Enable database
        // We keep this disabled because we use or shim to get around DOM_EXCEPTION_ERROR_16
        String databasePath = webView.getContext().getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(databasePath);


        //Determine whether we're in debug or release mode, and turn on Debugging!
        ApplicationInfo appInfo = webView.getContext().getApplicationContext().getApplicationInfo();
        if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 &&
                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
        {
            // enableRemoteDebugging();
        }

        settings.setGeolocationDatabasePath(databasePath);

        // Enable DOM storage
        settings.setDomStorageEnabled(true);

        // Enable built-in geolocation
        settings.setGeolocationEnabled(true);

        // Enable AppCache
        // Fix for CB-2282
        settings.setAppCacheMaxSize(5 * 1048576);
        settings.setAppCachePath(databasePath);
        settings.setAppCacheEnabled(true);

        // Fix for CB-1405
        // Google issue 4641
        String defaultUserAgent = settings.getUserAgentString();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);

    }




    public class ChromeClient extends WebChromeClient
    {


        private File createImageFile() throws IOException
        {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File imageFile = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            return imageFile;
        }
        // For Android 5.0
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Log.e("ll", "Unable to create Image File", ex);
                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                } else {
                    takePictureIntent = null;
                }
            }

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");

            Intent[] intentArray;
            if (takePictureIntent != null) {
                intentArray = new Intent[]{takePictureIntent};
            } else {
                intentArray = new Intent[0];
            }

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

            return true;

        }

        // openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

            mUploadMessage = uploadMsg;
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard

            File imageStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES)
                    , "AndroidExampleFolder");

            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs();
            }

            // Create camera captured image file path and name
            File file = new File(
                    imageStorageDir + File.separator + "IMG_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg");

            mCapturedImageURI = Uri.fromFile(file);

            // Camera capture image intent
            final Intent captureIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");

            // Create file chooser intent
            Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

            // Set camera intent to file chooser
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                    , new Parcelable[] { captureIntent });

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);


        }

        // openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType,
                                    String capture) {

            openFileChooser(uploadMsg, acceptType);
        }



    }
    //------------- here is my onActivityResult method to handle data from gallery or camera intent ----------------//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;

        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            if (requestCode == FILECHOOSER_RESULTCODE) {

                if (null == this.mUploadMessage) {
                    return;

                }

                Uri result = null;

                try {
                    if (resultCode != RESULT_OK) {

                        result = null;

                    } else {

                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }

                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;

            }
        }

        return;
    }
}
