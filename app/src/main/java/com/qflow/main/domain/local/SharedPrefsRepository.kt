package com.qflow.main.domain.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * Created by Rubén Izquierdo on 2/4/20
 */

class SharedPrefsRepository(c : Context) {

    companion object{
        const val ID_USER = "ID_USER"
    }

    private val prefs : SharedPreferences = c.getSharedPreferences("prefs", MODE_PRIVATE)
    private val encryptedShared: SharedPreferences


    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        encryptedShared = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            c,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }

    fun putUserToken(id : String){
        encryptedShared.edit().putString(ID_USER, id).apply()
    }

    fun removeUserToken(){
        encryptedShared.edit().remove(ID_USER).apply()
    }

    fun getUserToken() : String?{
        return encryptedShared.getString(ID_USER, null)
    }

    fun clearNormal(){
        prefs.edit().clear().apply()
    }

    fun clearEncrypter(){
        encryptedShared.edit().clear().apply()
    }
}