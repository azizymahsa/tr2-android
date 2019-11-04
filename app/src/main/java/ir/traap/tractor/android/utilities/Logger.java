package ir.traap.tractor.android.utilities;

import android.util.Log;

import ir.traap.tractor.android.BuildConfig;


public class Logger
{
    private static final boolean ENABLE_LOGS = BuildConfig.DEBUG;

    private Logger()
    {
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg)
    {
        if (ENABLE_LOGS)
        {
            Log.v(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, Exception e)
    {
        if (ENABLE_LOGS)
        {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void v(String tag, String msg, OutOfMemoryError e)
    {
        if (ENABLE_LOGS)
        {
            Log.v(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void d(String tag, String msg)
    {
        if (ENABLE_LOGS)
        {
            Log.d(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void d(String tag, String msg, Exception e)
    {
        if (ENABLE_LOGS)
        {
            Log.d(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void d(String tag, String msg, OutOfMemoryError e)
    {
        if (ENABLE_LOGS)
        {
            Log.d(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void e(String tag, String msg)
    {
        if (ENABLE_LOGS)
        {
            Log.e(tag, msg);
        }
    }

    @SuppressWarnings("unused")
    public static void e(String tag, String msg, Exception e)
    {
        if (ENABLE_LOGS)
        {
            Log.e(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static void e(String tag, String msg, OutOfMemoryError e)
    {
        if (ENABLE_LOGS)
        {
            Log.e(tag, msg, e);
        }
    }

    @SuppressWarnings("unused")
    public static boolean getIsLogsEnabled()
    {
        return ENABLE_LOGS;
    }

    @SuppressWarnings("unused")
    public static void reportException(Exception e)
    {
        if (ENABLE_LOGS)
        {
            Log.e("Exception", e.toString(), e);
        }
    }
}
