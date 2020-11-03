package com.example.encrypteddatasaving

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
private const val KEY_ALIAS = "key_alias"
class EncryptedDataSavingK(context: Context, FILE_NAME: String) {
    private val context: Context = context
    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private val FILE_NAME: String = FILE_NAME
    init {
        preferences = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            EncryptedSharedPreferences.create(this.context,
                FILE_NAME, getMasterKey(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
        } else {
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        }
        editor = preferences.edit()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun getMasterKey(): MasterKey {
        return MasterKey.Builder(this.context, KEY_ALIAS)
            .setKeyGenParameterSpec(getKeyGen())
            .build()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun getKeyGen(): KeyGenParameterSpec {
        return KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT
                or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

    }

    fun apply() = editor.apply()

    fun commit(): Boolean = editor.commit()

    fun putInt(key: String, value: Int): SharedPreferences.Editor? = editor.putInt(key, value)

    fun putString(key: String, value: String?): SharedPreferences.Editor? = editor.putString(key, value)

    fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor? = editor.putBoolean(key, value)

    fun putFloat(key: String, value: Float): SharedPreferences.Editor? = editor.putFloat(key, value)

    fun putLong(key: String, value: Long): SharedPreferences.Editor? = editor.putLong(key, value)

    fun putStringSet(key: String, value: Set<String>?): SharedPreferences.Editor? = editor.putStringSet(key, value)

    fun remove(key: String): SharedPreferences.Editor = editor.remove(key)

    fun clear(): SharedPreferences.Editor = editor.clear()

    fun getString(key: String, defValue: String?): String? = preferences.getString(key, defValue)

    fun getInt(key: String, defValue: Int): Int = preferences.getInt(key, defValue)

    fun getBoolean(key: String, defValue: Boolean): Boolean = preferences.getBoolean(key, defValue)

    fun getFloat(key: String, defValue: Float): Float = preferences.getFloat(key, defValue)

    fun getLong(key: String, defValue: Long): Long = preferences.getLong(key, defValue)

    fun getStringSet(key: String, value: Set<String>?): Set<String>? = preferences.getStringSet(key, value)

    fun contains(key: String): Boolean = preferences.contains(key)

    fun registerOnSharedPreferenceChangeListener( listener: SharedPreferences.OnSharedPreferenceChangeListener){
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener){
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


}