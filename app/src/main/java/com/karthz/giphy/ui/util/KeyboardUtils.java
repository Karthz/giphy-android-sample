package com.karthz.giphy.ui.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    public static void showKeyboard(@NonNull Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
    }

    public static void showKeyboard(@NonNull View view) {
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(@NonNull Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((activity.getCurrentFocus() == null)
                ? null
                : activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideKeyboard(@NonNull View view) {
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
