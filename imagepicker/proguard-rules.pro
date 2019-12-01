# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting_icon_m in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# For using GSON @Expose annotation
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class com.gun0912.tedpermission.** { *; }
#-keep class com.abbyy.mobile.ocr4.* { *; }

#-keep class com.abbyy.mobile.ocr4.layout.* { *; }

#-keepclassmembers class com.abbyy.mobile.ocr4.* { *; }
-keepclassmembers class **.R$* {public static <fields>;}
-keep class **.R$*
#-keepclassmembers class com.abbyy.mobile.ocr4.layout.* { *; }
-dontwarn com.squareup.okhttp.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions
-dontwarn javax.naming.**
-dontwarn javax.servlet.**
-dontwarn org.slf4j.**
-dontwarn org.apache.**
-dontwarn org.springframework.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.* { *; }
-dontwarn javax.annotation.Nullable
#-dontwarn com.wdullaer.**
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn net.sourceforge.jtds.**
-dontwarn com.google.zxing.client.**
-dontwarn com.intouch.dev.alacards.**
-dontwarn com.google.code.**
-keep public class com.google.android.gms.* { public *; }
-keepattributes com.adpdigital.push.location.LocationParams.Builder
#-dontwarn com.adpdigital.push.location.LocationParams.Builder
#-dontwarn com.adpdigital.push.location.**
-dontwarn com.google.android.gms.**

-dontwarn com.caverock.**
-dontwarn com.hariofspades.**
-dontwarn com.google.gson.**
-dontwarn okhttp3.internal.platform.**
-dontwarn java.io.**
-dontwarn com.sothree.slidinguppanel.**
-dontwarn com.orm.**
-dontwarn okio.**
-dontwarn library.android.eniac.flight.**
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keep class com.wooplr.spotlight.** { *; }
-keep interface com.wooplr.spotlight.**
-keep enum com.wooplr.spotlight.**
-keep class net.sqlcipher.database.** { *; }

-keep class com.traap.traapapp.models.** { *; }
-keep class com.traap.traapapp.models.dbModels.** { *; }
-keep class com.traap.traapapp.models.dbModels.ArchiveCardDBModel { *; }
-keep class com.traap.traapapp.models.dbModels.BankDB { *; }
-keep class com.traap.traapapp.models.otherModels.** { *; }

-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }

#-keep class net.sqlcipher.** { *; }
#-dontwarn net.sqlcipher.**


-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl {
*;
}


-dontwarn okio.**
#
-dontwarn scala.**
-dontwarn org.slf4j.**
-dontwarn org.spongycastle.**
-dontwarn org.bitcoinj.store.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.common.primitives.**
-dontwarn info.guardianproject.netcipher.**

-keep class com.squareup.wire.** { *; }
#-keep class scala.collection.SeqLike { *; }
-keep class com.btcontract.wallet.lightning.proto.** { *; }

-keep public enum * {
	public static **[] values();
	}
-dontwarn okio.**
-dontwarn scala.**
-dontwarn org.slf4j.**
-dontwarn org.spongycastle.**
-dontwarn org.bitcoinj.store.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.common.primitives.**
-dontwarn info.guardianproject.netcipher.**

#-keep class com.farazpardazan.accubin.core.Bank
#-keepclassmembers class com.farazpardazan.accubin.core.Bank { *; }

#-keep class com.farazpardazan.accubin.core.CardBin
#-keepclassmembers class com.farazpardazan.accubin.core.CardBin { *; }
-keep public enum * {
	public static **[] values();
	public static ** valueOf(java.lang.String);
}
-dontwarn javax.**
-dontwarn lombok.**
-dontwarn org.apache.**
-dontwarn com.squareup.**
-dontwarn com.sun.**
-dontwarn **retrofit**
-dontwarn library.android.eniac.**
-dontwarn org.mapstruct.ap.spi.**
-dontwarn okio.**
-dontwarn com.fasterxml.**
-dontwarn com.adpdigital.**
-dontwarn android.graphics.drawable..**
-dontwarn android.support.test.drawable..**
-dontwarn android.content.drawable..**

#--------------------------
-keepclassmembers class net.sourceforge.zbar.ImageScanner { *; }
-keepclassmembers class net.sourceforge.zbar.Image { *; }
-keepclassmembers class net.sourceforge.zbar.Symbol { *; }
-keepclassmembers class net.sourceforge.zbar.SymbolSet { *; }

-keepnames public class * extends io.realm.RealmObject
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class *
-dontwarn io.realm.**

