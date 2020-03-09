package com.qflow.main.domain.local.models
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Here we will define the User class for our App
 * */
@IgnoreExtraProperties
data class User(val username:String?=null,val mail:String?=null,val pass:String ?=null) {
}
