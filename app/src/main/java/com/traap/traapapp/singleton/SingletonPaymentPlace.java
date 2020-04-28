package com.traap.traapapp.singleton;

import android.content.Context;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class SingletonPaymentPlace
{
    private static final SingletonPaymentPlace ourInstance = new SingletonPaymentPlace();
    private int paymentPlace=0;

    private SingletonPaymentPlace() {}

    public static SingletonPaymentPlace getInstance()
    {
        return ourInstance;
    }

    public int getPaymentPlace()
    {
        return paymentPlace;
    }

    public void setPaymentPlace(int paymentPlace)
    {
        this.paymentPlace = paymentPlace;
    }
}
