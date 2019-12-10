package com.traap.traapapp.apiServices;

import android.content.Context;
//import android.support.multidex.MultiDex;

//import androidx.multidex.MultiDex;

import androidx.multidex.MultiDex;

import com.traap.traapapp.EniacApplication;
import com.traap.traapapp.apiServices.di.component.DaggerNetComponent;
import com.traap.traapapp.apiServices.di.component.NetComponent;
import com.traap.traapapp.apiServices.di.module.AppModule;
import com.traap.traapapp.apiServices.di.module.NetModule;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.helper.Const;
//import library.android.eniac.base.EniacApplication;
import lombok.Getter;


/**
 * Created by Javad.Abadi on 3/26/2018.
 */

public abstract class ServiceApplication extends EniacApplication
{
    @Getter
    private NetComponent mNetComponent;

    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();


        SingletonService.getInstance().setContext(this);

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Const.BASEURL))
                .build();

        SingletonService.getInstance().setNetComponent(mNetComponent).inject();
    }


}
