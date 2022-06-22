package com.bawp.mycardsapp.Util;

import androidx.room.TypeConverter;
import androidx.room.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public class TypeConverterListString {
    @TypeConverter
    public static String listToString(List<String> list) {
        String string = "";
        for (int i = 0; i < list.size(); i++) {
            if (i != 0)
                string += ",";
            String item = list.get(i);
            string = string.concat(item);
        }
        return string;
    }

    @TypeConverter
    public static List<String> stringToList(String string) {
        return Arrays.asList(string.split(","));
    }
}
