package ir.trap.tractor.android.apiServices;

import android.content.Context;
//import android.support.multidex.MultiDex;

//import androidx.multidex.MultiDex;

import androidx.multidex.MultiDex;

import ir.trap.tractor.android.apiServices.di.component.DaggerNetComponent;
import ir.trap.tractor.android.apiServices.di.component.NetComponent;
import ir.trap.tractor.android.apiServices.di.module.AppModule;
import ir.trap.tractor.android.apiServices.di.module.NetModule;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.helper.Const;
import library.android.eniac.base.EniacApplication;
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
