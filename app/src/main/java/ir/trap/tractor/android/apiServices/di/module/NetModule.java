package ir.trap.tractor.android.apiServices.di.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Javad.Abadi on 3/26/2018.
 */

@Module
public class NetModule {
    private String mBaseUrl;

    public NetModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }


    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient()
    {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(70, TimeUnit.SECONDS);
        client.readTimeout(70, TimeUnit.SECONDS);
        client.writeTimeout(70, TimeUnit.SECONDS);
        client.addInterceptor(new ChuckInterceptor(SingletonService.getInstance().getContext()));

        client.addInterceptor(chain ->
        {
            Request request = chain.request().newBuilder()
                    .addHeader("Token", Prefs.getString("serverToken",""))
//                    .addHeader(SingletonTrap.getInstance().getDIBA_NAME(),
//                            SingletonTrap.getInstance().getDIBA_KEY())
//                    .addHeader(SingletonTrap.getInstance().getDIBA_USER(),
//                            Utility.decryption(Prefs.getString(Utility.encryption(SingletonTrap.getInstance().getPASS_KEY()),
//                                    "")))
                    .build();

            return chain.proceed(request);
        });
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        
    }

}

