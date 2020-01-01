package com.traap.traapapp.utilities;

public class ReplaceEnglishNumberToPersian
{
    public static String getPersianChar(String charSequence)
    {
        String number = String.valueOf(charSequence);
        return number.replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹")
                .replace("0", "۰");
    }

}
