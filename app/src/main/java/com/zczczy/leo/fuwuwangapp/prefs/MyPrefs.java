package com.zczczy.leo.fuwuwangapp.prefs;

import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Leo on 2015/3/9.
 */

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    //XiYBzzSUfvh4+zr/wGJrdlb/dM2+q1ZoEYAUULXMnuxjMuZvFl2+l4yN0pYiJrMHA8X6nbeBN8NTuG07vzwabnl2VqB9HajTEFD1Y8Oh/b87DzVJ/vsEZY5lxn851Qzh5amW9d5TaHS4v9xVZbDsFT7J/NmBPxVbay4hHD5X8s0=
    //7B53AB7F1D9A8FF1
    @DefaultString("039C237E8850DBDC")
    String token();

    @DefaultString("A0D3F0B730CECB7E2074DEA6CE4DC85705AEC41311E68D15EDD50C7F66A9EEC984B73F9C8EB2A35C")
    String shopToken();

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
