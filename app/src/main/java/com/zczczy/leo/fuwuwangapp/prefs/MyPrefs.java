package com.zczczy.leo.fuwuwangapp.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Leo on 2015/3/9.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    //7B53AB7F1D9A8FF1
    @DefaultString("")
    String token();

    //274EF19BF74C34E7C2068C301091EB7841ADE0A3B409B2FAB92D95C39D8BE423D95C2023054BF70D
    @DefaultString("")
    String shopToken();

    @DefaultString("")
    String username();

    @DefaultString("")
    String realname();

    @DefaultString("")
    String locationAddress();

    @DefaultString("")
    String cityId();

    @DefaultString("")
    String address();

    @DefaultString("3")
    String userType();

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
