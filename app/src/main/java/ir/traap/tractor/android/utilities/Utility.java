package ir.traap.tractor.android.utilities;

/**
 * Created by Javad.Abadi on 7/10/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.scottyab.aescrypt.AESCrypt;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.singleton.SingletonContext;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;


public class Utility
{
    public static List<String> splite(String s, int chunkSize)
    {
        List<String> strings = new ArrayList<String>();
        int index = 0;
        while (index < s.length())
        {
            strings.add(s.substring(index, Math.min(index + 4, s.length())));
            index += chunkSize;
        }
        return strings;
    }

    public static String getPersianDate(String dateFormatServer)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd" );
        String date = dateFormat.format(dateFormatServer);  // formatted date in string
        String[] splitsDate = date.split("-");

        PersianDate persianDate = new PersianDate();
        persianDate.setGrgYear(Integer.valueOf(splitsDate[0]));
        persianDate.setGrgMonth(Integer.valueOf(splitsDate[1]));
        persianDate.setGrgDay(Integer.valueOf(splitsDate[2]));


        PersianDateFormat pdformater1 = new PersianDateFormat("Y/m/d");
        pdformater1.format(persianDate);//1396/05/20

        //PersianDateFormat pdformater2 = new PersianDateFormat("l j F y ");
        // date = String.valueOf(pdformater2.format(pdate));//۱۹ تیر ۹۶
        date = String.valueOf(pdformater1.format(persianDate));//1396/05/20

        return date;
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void hideSoftKeyboard(View input, Context context)
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(input.getWindowToken(), 0);
        } catch (Exception e)
        {
        }


    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean checkEmailForValidity(String email)
    {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();

    }


//    public static String encryption(String strNormalText)
//    {
//
//        try
//        {
//            return AESCrypt.encrypt(Settings.Secure.getString(SingletonContext.getInstance().getContext().getContentResolver(),
//                    Settings.Secure.ANDROID_ID), strNormalText);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static String decryption(String strEncryptedText)
//    {
//        try
//        {
//            return AESCrypt.decrypt(Settings.Secure.getString(SingletonContext.getInstance().getContext().getContentResolver(),
//                    Settings.Secure.ANDROID_ID), strEncryptedText);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public static String MD5(String str)
    {

        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(str.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++)
            {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void playSound(Context ctx, int resID)
    {
        MediaPlayer mp = MediaPlayer.create(ctx, resID);
        mp.setVolume(.2f, .2f);
        mp.setOnCompletionListener(new OnCompletionListener()
        {

            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }

        });
        mp.start();
    }

    public static int getResourceIdByName(Context context, String string, String packageString)
    {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(string, packageString, packageName);
        return resId;
    }

    public static String getResourceNameById(Context context, int id)
    {
        return context.getResources().getResourceName(id);
    }

    public static int DtoN(String date)
    {
        try
        {
            int num, i, year, month, day;
            String yearStr = date.substring(0, 4);
            String monthStr = date.substring(5, 7);
            String dayStr = date.substring(8, 10);
            try
            {
                year = Integer.parseInt(yearStr);
                month = Integer.parseInt(monthStr);
                day = Integer.parseInt(dayStr);
            } catch (Exception e)
            {
                return -1;
            }
            int BaseYear = 1300;
            if (year == 0 || month == 0 || day == 0)
                return -999999;
            if (year < BaseYear || month < 1 || month > 12 || day < 1
                    || (month <= 6 && day > 31) || (month > 6 && day > 30))
                return -1;
            int year_dist;
            year_dist = year - BaseYear;
            num = year_dist * 365;
            i = 1;
            while (i < month)
            {
                int x;
                if (i <= 6)
                    x = 31;
                else if (i <= 11)
                    x = 30;
                else
                    x = 29;
                num = num + x;
                i++;
            }
            num = num + day;
            i = BaseYear;
            while (i < year)
            {
                if (IsSolHejLeap(i))
                    num++;
                i++;
            }
            return num - 18262;
        } catch (Exception e)
        {
            return 0;
        }
    }

    private static boolean IsSolHejLeap(int year)
    {
        year = year + 38;
        year = year * 31;
        year = year % 128;
        return year <= 30;
    }

    public static void CallUSSD(String USSDMessage, Context mContext)
    {
        try
        {
            String message = USSDMessage.replace("#", Uri.encode("#"));
            Intent startIntent = new Intent("android.intent.action.CALL",
                    Uri.parse("tel:" + message));
            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(startIntent);
        } catch (Exception e)
        {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static String getMyOperator(Context aContext)
    {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) aContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String mynumber = mTelephonyMgr.getNetworkOperator();

        String Operator = "";
        if (mynumber != null)
        {
            if (mynumber.equals("43211"))
            {
                Operator = "MCI";
            } else if (mynumber.equals("43235"))
            {
                Operator = "IRANCELL";
            } else if (mynumber.equals("43232"))
            {
                Operator = "TALIYA";
            } else if (mynumber.equals("43220"))
            {
                Operator = "RAITEL";
            }
        }
        return Operator;
    }


    public static String timeLongToString(long time)
    {
        time /= 1000;
        int hour, minute, second;
        second = (int) (time % 60);
        time /= 60;
        minute = (int) (time % 60);
        time /= 60;
        hour = (int) time;
        return String.format(Locale.US, "%02d:%02d:%02d", hour, minute, second);
    }


    public static boolean isConnectionReachable(Context context)
    {
        InetAddress in;
        in = null;

        try
        {
            return in.isReachable(5000);
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) SingletonContext.getInstance().getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting() && activeNetworkInfo.isAvailable();

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setMobileDataEnabled(Context context, boolean enabled)
    {
        try
        {
            final ConnectivityManager conman = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            Class conmanClass;
            conmanClass = Class.forName(conman.getClass().getName());
            final Field connectivityManagerField = conmanClass
                    .getDeclaredField("mService");
            connectivityManagerField.setAccessible(true);
            final Object connectivityManager = connectivityManagerField
                    .get(conman);
            final Class connectivityManagerClass = Class
                    .forName(connectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = connectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isPointInsideView(float x, float y, View view)
    {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        return (x > viewX && x < (viewX + view.getWidth()))
                && (y > viewY && y < (viewY + view.getHeight()));
    }


    public static String converToEnglish(String recieveDate)
    {
        for (int i = 0; i <= 9; i++)
        {
            recieveDate = recieveDate.replace((char) (1776 + i),
                    (char) (48 + i));
        }
        return recieveDate;
    }

    public static String priceFormat(String price)
    {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.valueOf(price));
    }


    public static void disableEnableControls(boolean enable, ViewGroup vg)
    {
        for (int i = 0; i < vg.getChildCount(); i++)
        {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup)
            {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    public static void disableEnableClickableViews(boolean enable, ViewGroup vg)
    {
        for (int i = 0; i < vg.getChildCount(); i++)
        {
            View child = vg.getChildAt(i);
            if (child.isClickable())
                child.setEnabled(enable);
            if (child instanceof ViewGroup)
            {
                disableEnableClickableViews(enable, (ViewGroup) child);
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static String dateShow(String time)
    {

        String[] splite = time.split(" ");
        String[] dateSplite = splite[0].split("/");

	/*	String dayM=dateSplite[2];
		String monthM=dateSplite[1];
		String yearM=dateSplite[0];*/

		/*	Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(yearM));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayM));
			cal.set(Calendar.MONTH, Integer.parseInt(monthM));
			String format = new SimpleDateFormat(" MMM dd").format(cal.getTime());

			return format;
		}catch (Exception e) {
			System.out.println("Exception ::"+e);
			//return "";
		}

*/

        //String outputPattern = "dd-MMM-yyyy h:mm a";


        String inputPattern = "yyyy/MM/dd HH:mm";
        String outputPattern = "dd MMM";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try
        {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return str;


    }

    public static String dateShowPolicy(String time)
    {

        String[] splite = time.split(" ");
        String[] dateSplite = splite[0].split("/");

        //String outputPattern = "dd-MMM-yyyy h:mm a";


        String inputPattern = "yyyy/MM/dd HH:mm";
        String outputPattern = "MMM dd HH:mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try
        {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return str;


    }

    public static String dateShowView(String time)
    {

        String[] splite = time.split(" ");
        String[] dateSplite = splite[0].split("/");

	/*	String dayM=dateSplite[2];
		String monthM=dateSplite[1];
		String yearM=dateSplite[0];*/

		/*	Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(yearM));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayM));
			cal.set(Calendar.MONTH, Integer.parseInt(monthM));
			String format = new SimpleDateFormat(" MMM dd").format(cal.getTime());

			return format;
		}catch (Exception e) {
			System.out.println("Exception ::"+e);
			//return "";
		}

*/

        //String outputPattern = "dd-MMM-yyyy h:mm a";


        String inputPattern = "yyyy/MM/dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try
        {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return str;


    }

    public static void startAnim(Context context, View view, int animId)
    {
        if (context == null || view == null)
        {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(context, animId);
        view.startAnimation(animation);
    }


    public static String dateShowViewFlight(String time)
    {

        String[] splite = time.split(" ");
        String[] dateSplite = splite[0].split("-");

   /* String dayM=dateSplite[2];
      String monthM=dateSplite[1];
      String yearM=dateSplite[0];*/

      /* Calendar cal = Calendar.getInstance();
         cal.set(Calendar.YEAR, Integer.parseInt(yearM));
         cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayM));
         cal.set(Calendar.MONTH, Integer.parseInt(monthM));
         String format = new SimpleDateFormat(" MMM dd").format(cal.getTime());

         return format;
      }catch (Exception e) {
         System.out.println("Exception ::"+e);
         //return "";
      }

*/

        //String outputPattern = "dd-MMM-yyyy h:mm a";


        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try
        {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return str;


    }

//    public static String getDeviceID(Context context)
//    {
//        //  postEvent(new ReadPhoneStatePermissionRequiredEvent());
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return telephonyManager.getDeviceId();
//    }
//
//    public static String getSubscriberID(Context context)
//    {
//        //  postEvent(new ReadPhoneStatePermissionRequiredEvent());
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return telephonyManager.getSubscriberId();
//    }

    public static String getOpratorID(Context context)
    {
        //  postEvent(new ReadPhoneStatePermissionRequiredEvent());
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getNetworkOperatorName();
    }

//    public static String getSoftWareVersion(Context context)
//    {
//        //  postEvent(new ReadPhoneStatePermissionRequiredEvent());
//        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return telephonyManager.getDeviceSoftwareVersion();
//    }


    public static boolean campareDate(String Depart, String Return)
    {


        try
        {

            DateFormat formatter;
            Date date;
            Date date2;
            formatter = new SimpleDateFormat("yyyy/MM/dd");
            date = formatter.parse(Depart);
            date2 = formatter.parse(Return);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return date2.getTime() < date.getTime();

        } catch (Exception e)
        {
            return false;
        }

    }


    public static String convertNumbersToPersian(String str)
    {
        String answer = str;
        answer = answer.replace("1", "١");
        answer = answer.replace("2", "٢");
        answer = answer.replace("3", "٣");
        answer = answer.replace("4", "٤");
        answer = answer.replace("5", "٥");
        answer = answer.replace("6", "٦");
        answer = answer.replace("7", "٧");
        answer = answer.replace("8", "٨");
        answer = answer.replace("9", "٩");
        answer = answer.replace("0", "٠");
        return answer;
    }

    public static String convertNumbersToEnglish(String str)
    {
        String answer = str;
        answer = answer.replace("۱", "1");
        answer = answer.replace("۲", "2");
        answer = answer.replace("۳", "3");
        answer = answer.replace("۴", "4");
        answer = answer.replace("۵", "5");
        answer = answer.replace("۶", "6");
        answer = answer.replace("۷", "7");
        answer = answer.replace("۸", "8");
        answer = answer.replace("۹", "9");
        answer = answer.replace("۰", "0");
        answer = answer.replace("١", "1");
        answer = answer.replace("٢", "2");
        answer = answer.replace("٣", "3");
        answer = answer.replace("٤", "4");
        answer = answer.replace("٥", "5");
        answer = answer.replace("٦", "6");
        answer = answer.replace("٧", "7");
        answer = answer.replace("٨", "8");
        answer = answer.replace("٩", "9");
        answer = answer.replace("٠", "0");

        return answer;
    }


    public static boolean CheckCartDigit(String cardNumber)
    {
        int cardNumberLength = cardNumber.length();
        if (cardNumberLength < 16 || Integer.valueOf(cardNumber.substring(1, 10)) == 0 ||
                Integer.valueOf(cardNumber.substring(1, 10)) == 0)
            return false;
        long s = 0;
//        Log.e("teeeest111", cardNumber);

        for (int i = 0; i < 16; i++)
        {
            //Log.e("teeeest111", i+"=>"+(i+1) );
            //Log.e("teeeest222",  Integer.valueOf(cardNumber.substring(i, (i+1)))+"" );
            int k = i % 2 == 0 ? 2 : 1;
            int d = Integer.valueOf(cardNumber.substring(i, (i + 1))) * k;
            int l = d > 9 ? d - 9 : d;
            s += l;
        }

        return s % 10 == 0;
    }

    public static void openUrlCustomTab(Activity context, String url)
    {
        try
        {
            Uri uri = Uri.parse(url);
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            customTabsIntent.launchUrl(context, uri);

        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    public static void share(String body)
    {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);


        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body);
        SingletonContext.getInstance().getContext().startActivity(Intent.createChooser(sharingIntent, "share").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    public static String cardFormat(String number)
    {
        try
        {
            List<String> strings;
            strings = Utility.splite(number, 4);
            return strings.get(0) + "-" + strings.get(1) + "-" + strings.get(2) + "-" + strings.get(3);
        } catch (Exception e)
        {
            return number;
        }

    }


    public static String cardStarFormat(String number)
    {
        try
        {
            List<String> strings;
            strings = Utility.splite(number, 4);
            return strings.get(0) + "-" + strings.get(1).substring(0, 2) + "** -" + "****" + "-" + strings.get(3);
        } catch (Exception e)
        {
            return number;

        }

    }


    public static boolean mciValidation(String number)
    {
        if (TextUtils.isEmpty(number))
        {
            return false;
        }
        if (number.length() > 14)
        {
            return false;
        }
        String check = number.substring(0, 4);
      /*  return check.equals("0910") || check.equals("0911") || check.equals("0912") || check.equals("0913")
                || check.equals("0914") || check.equals("0915") || check.equals("0916") || check.equals("0917")
                || check.equals("0918") || check.equals("0919");*/
        return true;
    }

    public static boolean mtnValidation(String number)
    {
        if (TextUtils.isEmpty(number))
        {
            return false;
        }
        if (number.length() > 14)
        {
            return false;
        }
        String check = number.substring(0, 4);
        /*return check.equals("0939") || check.equals("0938") || check.equals("0937") || check.equals("0936")
                || check.equals("0935") || check.equals("0933") || check.equals("0930") || check.equals("0903")
                || check.equals("0902") || check.equals("0901")|| check.equals("0905");*/
        return true;
    }

    public static boolean rightelValidation(String number)
    {
        if (TextUtils.isEmpty(number))
        {
            return false;
        }
        if (number.length() > 14)
        {
            return false;
        }
        String check = number.substring(0, 4);
        /*      return check.equals("0921") || check.equals("0922");*/
        return true;

    }

    public static boolean checkGps()
    {

        final LocationManager manager = (LocationManager) SingletonContext.getInstance().getContext().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public static boolean containsNumber(String text)
    {
        return text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5")
                || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9") || text.contains("0") ||
                text.contains("۱") || text.contains("۲") || text.contains("۳") || text.contains("۴") || text.contains("۵")
                || text.contains("۶") || text.contains("۷") || text.contains("۸") || text.contains("۹") || text.contains("۰");


    }

    public static boolean checkDate(int year, int month, int dayOfMonth)
    {

        Calendar c = Calendar.getInstance();

// set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        Date today = c.getTime();

// or as a timestamp in milliseconds
        long todayInMillis = c.getTimeInMillis();

// user-specified date which you are testing
// let's say the components come from a form or something


// reuse the calendar to set user specified date
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

// and get that as a Date
        Date dateSpecified = c.getTime();

// test your condition
        if (dateSpecified.before(today))
        {
            System.err.println("Date specified [" + dateSpecified + "] is before today [" + today + "]");
            return false;

        } else
        {
            System.err.println("Date specified [" + dateSpecified + "] is NOT before today [" + today + "]");
            return true;
        }

    }

    private static String convertToHex(byte[] data)
    {
        StringBuilder buf = new StringBuilder();
        for (byte b : data)
        {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do
            {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static Integer getCountOfDays(String createdDateString, String expireDateString)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try
        {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime))
        {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else
        {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ((int) dayCount);
    }

    public static boolean validSignature(Context context, String buildKey)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures)
            {
                MessageDigest sha = MessageDigest.getInstance("SHA");
                sha.update(signature.toByteArray());
                final String currentSignature = Base64.encodeToString(sha.digest(), Base64.DEFAULT);


                if (buildKey.equals(currentSignature))
                {
                    return true;
                }
            }
        } catch (Exception e)
        {
        }

        return false;
    }

    public static void runLayoutAnimation(final RecyclerView recyclerView)
    {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.grid_layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

}
