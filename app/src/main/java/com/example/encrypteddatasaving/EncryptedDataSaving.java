package com.example.encrypteddatasaving;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.RequiresApi;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Set;

public class EncryptedDataSaving {

    private Context context;
    private static final String KEY_GEN_ALIAS = "key_gen_alias";
    private String FILE_NAME;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public EncryptedDataSaving(Context context, String FILE_NAME){
        this.context = context;
        this.FILE_NAME = FILE_NAME;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            try {
                sharedPreferences = EncryptedSharedPreferences.create(this.context,
                        FILE_NAME, createMasterKey(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
            }catch (GeneralSecurityException | IOException e){
                e.printStackTrace();
            }
        }else {
            sharedPreferences = this.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }

        this.editor = sharedPreferences.edit();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private KeyGenParameterSpec createKeyGen(){
        return new KeyGenParameterSpec.Builder(KEY_GEN_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private MasterKey createMasterKey() throws GeneralSecurityException, IOException {
        return new MasterKey.Builder(this.context, KEY_GEN_ALIAS)
                .setKeyGenParameterSpec(createKeyGen())
                .build();
    }

    public void putString(String key, String value){
        this.editor.putString(key, value);
    }

    public void putBoolean(String key, boolean value){
        this.editor.putBoolean(key, value);
    }

    public void putInt(String key, int value){
        this.editor.putInt(key, value);
    }

    public void putFloat(String key, float value){
        this.editor.putFloat(key, value);
    }

    public void putLong(String key, long value){
        this.editor.putLong(key, value);
    }

    public void putStringSet(String key, Set<String> value){
        this.editor.putStringSet(key, value);
    }

    public void remove(String key){
        this.editor.remove(key);
    }

    public void apply(){
        this.editor.apply();
    }

    public boolean commit(){
        return this.editor.commit();
    }

    public void clear(){
        this.editor.clear();
    }

    public String getString(String key, String defValue){
        return this.sharedPreferences.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue){
        return this.sharedPreferences.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue){
        return this.sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue){
        return this.sharedPreferences.getFloat(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue){
        return this.sharedPreferences.getStringSet(key, defValue);
    }

    public int getInt(String key, int defValue){
        return this.sharedPreferences.getInt(key, defValue);
    }

    public boolean contains(String key){
        return this.sharedPreferences.contains(key);
    }

    public Map<String, ?> getAll(){
        return this.sharedPreferences.getAll();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
