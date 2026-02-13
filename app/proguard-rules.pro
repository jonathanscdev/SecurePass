# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep CryptoManager (uses reflection-like patterns with KeyStore)
-keep class mx.jsc.securepass.crypto.CryptoManager { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Hilt
-dontwarn dagger.hilt.android.internal.**
