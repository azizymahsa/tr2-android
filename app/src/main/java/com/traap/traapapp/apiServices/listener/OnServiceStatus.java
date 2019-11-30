package com.traap.traapapp.apiServices.listener;


/**
 * Created by Javad.Abadi on 3/26/2018.
 */

public interface OnServiceStatus<T>
{

    void onReady(T t);


    void onError(String message);
}
