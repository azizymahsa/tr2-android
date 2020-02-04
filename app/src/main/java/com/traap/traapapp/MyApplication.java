package com.traap.traapapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
//import android.support.multidex.MultiDex;

//import androidx.multidex.MultiDex;
import com.traap.traapapp.notification.SilentPushReceiver;

import androidx.multidex.MultiDex;

import com.androidnetworking.AndroidNetworking;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jacksonandroidnetworking.JacksonParserFactory;
//import com.orm.SugarContext;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

import com.traap.traapapp.apiServices.ServiceApplication;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.splash.SplashActivity;
import com.traap.traapapp.utilities.font.CustomViewWithTypefaceSupport;
import com.traap.traapapp.utilities.font.TextField;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.metrica.push.YandexMetricaPush;

import library.android.service.di.component.DaggerNetGdsComponent;
import library.android.service.di.component.NetGdsComponent;
import library.android.service.di.module.NetModule;
import library.android.service.generator.SingletonGdsService;
import library.android.service.helper.Const;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends ServiceApplication
{

//    private AdpPushClient chabok = null;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    @SuppressLint("HardwareIds")
    @Override
    public void onCreate()
    {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/iran_sans_normal.ttf").build());
        Realm.init(this);

       // UXCam.startWithKey("h6hki2cq0lq974n");

        FirebaseAnalytics.getInstance(this);
//        FirebaseCrash.setCrashCollectionEnabled(!BuildConfig.DEBUG);

        Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(false) // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);
        SingletonGdsService.getInstance().setContext(this);

        NetGdsComponent mNetGdsComponent = DaggerNetGdsComponent.builder()
                .netModule(new NetModule(Const.BASEURL))
                .build();
        SingletonGdsService.getInstance().setNetGdsComponent(mNetGdsComponent).inject();

//        SingletonGdsService.getInstance().setContext(this);
//        NetGdsComponent mNetGdsComponent = DaggerNetGdsComponent.builder()
//                .netModule(new NetModule(Const.BASEURL))
//                .build();
//        SingletonGdsService.getInstance().setNetGdsComponent(mNetGdsComponent).inject();

        //-----------------------Yandex-------------------------
        // Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("2c55ed84-1f92-47cd-b5fd-b837dc89b30f").build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);
        YandexMetricaPush.init(getApplicationContext());

        YandexMetrica.registerReferrerBroadcastReceivers(new SilentPushReceiver());
        //-----------------------Yandex-------------------------

//        SugarContext.init(this);

        // Adding an Network Interceptor for Debugging purpose :
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(70, TimeUnit.SECONDS);
        client.readTimeout(70, TimeUnit.SECONDS);
        client.writeTimeout(70, TimeUnit.SECONDS);
//        client.addInterceptor(new ChuckInterceptor(SingletonService.getInstance().getContext()));

        OkHttpClient okHttpClient = client.build();
        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);

        // Then set the JacksonParserFactory like below
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        SingletonContext.getInstance().setContext(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran_sans_normal.ttf")
                .setFontAttrId(R.attr.fontPath)
                .addCustomViewWithSetTypeface(CustomViewWithTypefaceSupport.class)
                .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                .build()
        );
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();


//        initPushClient();
    }


//    private synchronized void initPushClient()
//    {
//        if (chabok == null)
//        {
//            chabok = AdpPushClient.init(
//                    getApplicationContext(),
//                    SplashActivity.class,
//                    "ogomegad/274025764855",
//                    "9163b7512b64c6cac9a226c150bf68bb558bb007",
//                    "wujeduwejig",
//                    "wirvuilwem"
//            );
//
//
////            chabok.setDevelopment(true);
//            chabok.get().setDevelopment(true);
//
////             chabok.register(Prefs.getString("uuid",""));
//        }
//    }

//    public synchronized AdpPushClient getPushClient() throws IllegalStateException
//    {
//        if (chabok == null)
//        {
//            throw new IllegalStateException("Adp Push Client not initialized");
//        }
//        return chabok;
//    }


    @Override
    public void onTerminate()
    {
//        if (chabok != null)
//        {
//            chabok.dismiss();
//        }
        super.onTerminate();
    }


}
