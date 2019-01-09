package com.example.android.myfitnessapp.util;

import android.widget.EditText;

public class Utilities {
    public static boolean isInputEmpty(String value, EditText view) {
        boolean isEmpty = value.equals("");
        if (isEmpty){
            view.setError("This field is required");
        }
        return isEmpty;
    }
}
