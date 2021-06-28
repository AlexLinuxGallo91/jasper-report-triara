package com.triara.jarperreport.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {

    public static boolean verifyJsonText(String jsonText) {
        boolean result = true;

        try {
            Gson gson = new Gson();
            gson.fromJson(jsonText, Object.class);
        } catch (JsonSyntaxException e) {
            result = false;
        }

        return result;
    }



}
