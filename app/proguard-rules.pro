# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
#
#-optimizationpasses 5          # 指定代码的压缩级别
#-dontusemixedcaseclassnames   # 是否使用大小写混合
#-dontpreverify           # 混淆时是否做预校验
#-verbose                # 混淆时是否记录日志
#
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
#
#-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
#-keep public class * extends android.app.Application   # 保持哪些类不被混淆
#-keep public class * extends android.app.Service       # 保持哪些类不被混淆
#-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
#-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
#-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
#-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
#-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆
#
#-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
#    native <methods>;
#}
#-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
#    public void *(android.view.View);
#}
#-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
#    public static final android.os.Parcelable$Creator *;
#}

-optimizationpasses 5  # 指定代码的压缩级别
#【混淆时不会产生形形色色的类名 】  # 是否使用大小写混合
-dontusemixedcaseclassnames
#【指定不去忽略非公共的库类。 】
-dontskipnonpubliclibraryclasses
#【不预校验】
-dontpreverify
-verbose
#【优化】
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontobfuscate
-dontoptimize

# androidannotations
-keepattributes *Annotation*
-dontwarn org.springframework.**


-dontwarn org.w3c.dom.bootstrap.**
-dontwarn org.apache.commons.**
-dontwarn com.google.code.rome.android.**
-dontwarn org.codehaus.jackson.**

-keep class com.google.gson.** { *;}
-keep class com.zczczy.fuwuwang.model.** { *;}
-keep class org.codehaus.jackson.** { *;}
-keep class org.w3c.dom.bootstrap.** { *;}
-keep class org.apache.commons.httpclient.** { *;}
-keep class com.google.code.rome.android.repackaged.com.sun.syndication.** { *;}



# okhtpp 和 picasso
-dontwarn com.squareup.okhttp.**

# nineoldandroids
-keep interface com.nineoldandroids.view.** { *; }
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# 百度地图 -libraryjars libs/baidumapapi_v3_5_0.jar
-dontwarn class com.baidu.**
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
