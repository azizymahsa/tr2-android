package com.traap.traapapp.utilities;

import java.util.Arrays;

public class NationalCodeValidation
{
    public static boolean isValidNationalCode(String nationalCode)
    {
        if (nationalCode.length() != 10)
        {
            return false;
        }
        else
        {
            //Check for equal numbers
            String[] allDigitEqual = {"0000000000", "2222222222", "3333333333",
                    "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};
            if (Arrays.asList(allDigitEqual).contains(nationalCode))
            {
                return false;
            }
            else
            {
                int sum = 0;
                int lenght = 10;
                for (int i = 0; i < lenght - 1; i++)
                {
                    sum += Integer.parseInt(String.valueOf(nationalCode.charAt(i))) * (lenght - i);
                }

                int r = Integer.parseInt(String.valueOf(nationalCode.charAt(9)));

                int c = sum % 11;

                return (((c < 2) && (r == c)) || ((c >= 2) && ((11 - c) == r)));
            }

        }
    }
}
