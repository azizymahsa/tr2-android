package com.traap.traapapp.apiServices.part;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.traap.traapapp.BuildConfig;
import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.helper.Const;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.mock.MockProcessor;
import com.traap.traapapp.apiServices.model.SingletonResponse;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.login.LoginActivity;
import com.traap.traapapp.utilities.Logger;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Response;

/**
 * Created by Javad.Abadi on 3/26/2018.
 */

public abstract class BasePart
{
    private ServiceGenerator serviceGenerator;
//    private CompositeDisposable disposable = new CompositeDisposable();

    public BasePart(ServiceGenerator serviceGenerator)
    {
        this.serviceGenerator = serviceGenerator;
    }

    protected abstract BasePart getPart();

//    public CompositeDisposable getDisposable()
//    {
//        return disposable;
//    }
//
//    public void setDisposable(CompositeDisposable disposable)
//    {
//        this.disposable = disposable;
//    }

    public ServiceGenerator getServiceGenerator()
    {
        return serviceGenerator;
    }

    public <T> void start(Single<Response<WebServiceClass<T>>> single, OnServiceStatus<WebServiceClass<T>> listener)
    {
        if (!BuildConfig.DEBUG)
        {
            call(single, listener);
            return;
        }
        MockProcessor<T> mockProcessor = new MockProcessor<>(listener, getPart());

        if (Const.MOCK && mockProcessor.getRawRes() != null && mockProcessor.loadJSONFromAsset() != null)
        {
            WebServiceClass<T> model = mockProcessor.getMockModel();
            if (model == null)
            {
                call(single, listener);
                return;
            }
            new Handler().postDelayed(() -> listener.onReady(model), 50);
            return;
        }
        call(single, listener);
    }


    private <T> void call(Single<Response<WebServiceClass<T>>> observable, OnServiceStatus<WebServiceClass<T>> listener)
    {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Response<WebServiceClass<T>>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
//                        disposable = (CompositeDisposable) d;
                    }

                    @Override
                    public void onSuccess(Response<WebServiceClass<T>> value)
                    {
                        Log.e("12333", "onSuccess: " );
                        if (BuildConfig.DEBUG && Const.TEST)
                        {
                            SingletonResponse.getInstance().addResponse(value);
                        }
                        if (BuildConfig.DEBUG)
                        {
                            Logger.e("--URL--", value.raw().request().url().toString());
                            Logger.e("--Body--", bodyToString(value.raw().request().body()));
                        }

                        if (value.code() > 299 || value.code() < 200)
                        {
                            listener.onError("خطا در پردازش اطلاعات!");
                            return;
                        }
//                        if (value.code() == 403)
//                        {
//                            SingletonContext.getInstance().getContext().startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
//                                    LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                            System.exit(0);
//
//                        }
                        if (value.body().info.statusCode == 401)
                        {
                            SingletonContext.getInstance().getContext().startActivity(new Intent(SingletonContext.getInstance().getContext(),
                                    LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            System.exit(0);
                            return;
                        }
                        else
                        {
                            listener.onReady(value.body());
                            return;
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        e.printStackTrace();
                        listener.onError(e.getMessage());
                    }
                });
    }















    public <T> void start(Observable<Response<T>> observable, OnServiceStatus<T> listener) {
        if (!BuildConfig.DEBUG) {
            call(observable, listener);
            return;
        }

        call(observable, listener);
    }


    private <T> void call(Observable<Response<T>> observable, OnServiceStatus<T> listener) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<T> value) {
                        if (BuildConfig.DEBUG && Const.TEST)
                            SingletonResponse.getInstance().addResponse(value);
                        if (value.code()==401){

                            //BaseActivity.startLogin(BaseActivity.baseActivity);
                            return;
                        }

                        listener.onReady(value.body());

                    /*    if (value.code()!=200){

                            try {
                                JSONObject json = new JSONObject(value.errorBody().string()); ;
                                listener.showErrorMessage(json.getString("error"));

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }else{
                            listener.onReady(value.body());

                        }*/



                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
























    private String bodyToString(final RequestBody request)
    {
        try
        {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e)
        {
            return "did not work";
        }
    }

}
