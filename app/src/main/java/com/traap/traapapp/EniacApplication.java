package com.traap.traapapp;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.multidex.MultiDex;

import com.orm.SugarContext;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.utilities.font.CustomViewWithTypefaceSupport;
import com.traap.traapapp.utilities.font.TextField;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class EniacApplication extends Application
{

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        SingletonContext.getInstance().setContext(this);
//        SingletonGdsService.getInstance().setContext(this);

/*
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(Const.BASEURL))
                .build();

        SingletonService.getInstance().setNetComponent(mNetComponent).inject();*/
        SugarContext.init(this);

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


    }


    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }


}
