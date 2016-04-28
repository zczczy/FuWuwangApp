package com.zczczy.leo.fuwuwangapp.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Leo on 2015/3/9.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    @DefaultString("")
    String token();

    @DefaultString("")
    String username();

    @DefaultString("")
    String realname();

//    @DefaultString("大连市")
//    String locationAddress();
//
//    @DefaultString("全国")
//    String address();

    @DefaultString("3")
    String userType();

    @DefaultString("")
    String userId();

    @DefaultString("")
    String avatar();

    @DefaultLong(0L)
    long timerCode();

    @DefaultLong(0L)
    long timerLong();

    @DefaultLong(0L)
    long timerWithDraw();

    @DefaultLong(0L)
    long timerTransfer();

    @DefaultLong(0L)
    long timerCancel();

    @DefaultLong(0L)
    long timerPerfectInfo();
}
