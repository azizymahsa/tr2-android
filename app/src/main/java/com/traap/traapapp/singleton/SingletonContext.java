package com.traap.traapapp.singleton;

import android.content.Context;

/**
 * Created by Javad.Abadi on 7/2/2018.
 */
public class SingletonContext
{
    private static final SingletonContext ourInstance = new SingletonContext();
    private Context context;

    private SingletonContext()
    {

    }

    public static SingletonContext getInstance()
    {
        return ourInstance;
    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }


}
