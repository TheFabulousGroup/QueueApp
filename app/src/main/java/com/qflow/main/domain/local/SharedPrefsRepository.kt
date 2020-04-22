package com.qflow.main.domain.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

/**
 * Created by Rubén Izquierdo on 2/4/20
 */

class SharedPrefsRepository(c : Context) {

    companion object{
        const val ID_USER = "ID_USER"
    }

    private val prefs : SharedPreferences = c.getSharedPreferences("prefs", MODE_PRIVATE)

    fun putUserId(id : String){
        prefs.edit().putString(ID_USER, id).apply()
    }

    fun removeUserId(){
        prefs.edit().remove(ID_USER).apply()
    }

    fun getUserId() : String?{
        return prefs.getString(ID_USER, null)
    }

    fun clear(){
        prefs.edit().clear().apply()
    }
}