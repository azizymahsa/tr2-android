package ir.trap.tractor.android.apiServices.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Javad.Abadi on 3/26/2018.
 */

@Module
public class AppModule
{
    Application mApplication;


    public AppModule(Application mApplication)
    {
        this.mApplication = mApplication;
    }


    @Provides
    @Singleton
    Application provideApplication()
    {
        return mApplication;
    }
}

