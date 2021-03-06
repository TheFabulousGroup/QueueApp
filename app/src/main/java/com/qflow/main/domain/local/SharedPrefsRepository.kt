package com.qflow.main.domain.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.qflow.main.domain.adapters.UserAdapter


class SharedPrefsRepository(c : Context) {

    companion object{
        const val ID_USER = "ID_USER"
        const val USER_NAME = "USER_NAME"
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

    fun putUserToken(token : String?){
        encryptedShared.edit().putString(ID_USER, token).apply()
    }

    fun putUserName(userName : String?){
        encryptedShared.edit().putString(USER_NAME, userName).apply()
    }

    fun removeUserToken(){
        encryptedShared.edit().remove(ID_USER).apply()
    }

    fun getUserToken() : String?{
        return (encryptedShared.getString(ID_USER, null))
    }

    fun getUserName() : String?{
        return (encryptedShared.getString(USER_NAME, null))
    }

}