package com.traap.traapapp.utilities;

public class ReplacePersianNumberToEnglish
{
    public static CharSequence getEnglishChar(CharSequence charSequence)
    {
        String number = String.valueOf(charSequence);
        return number.replace("۱", "1")
                .replace("۲", "2")
                .replace("۳", "3")
                .replace("۴", "4")
                .replace("۵", "5")
                .replace("۶", "6")
                .replace("۷", "7")
                .replace("۸", "8")
                .replace("۹", "9")
                .replace("۰", "0");
    }
}
