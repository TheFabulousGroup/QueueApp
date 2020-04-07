package com.qflow.main.core.extensions

import com.google.gson.Gson
import java.util.*

fun String.Companion.empty() = ""


inline fun <reified T> String.toModel(): T = Gson().fromJson(this,T::class.java)



fun Map<String,String>.composeJson(): String
{
    val stringJson = StringBuilder()
    var index = 0

    if(!this.isEmpty())
    {
        stringJson.append("{")


        for((key,value) in this)
        {
            stringJson.append("\""+key+"\"")
            stringJson.append(":")
            stringJson.append("\""+value+"\"")

            if(index!=this@composeJson.size-1)
                stringJson.append(",")

            index++
        }
        stringJson.append("}")


    }

    return stringJson.toString()
}

fun String.containOr(list: Collection<String>): Boolean = list.find { it==this.toLowerCase(Locale.getDefault()) } != null

fun CharSequence.indexOfAll(string: String): List<Int>{

    val indexes = mutableListOf<Int>()
    val lowerCaseTextString = this.toString().toLowerCase()
    val lowerCaseWord = string.toLowerCase()

    var index = 0
    while (index != -1) {
        index = lowerCaseTextString.indexOf(lowerCaseWord, index)
        if (index != -1) {
            indexes.add(index)
            index++
        }
    }
    return indexes
}

fun String?.toIntZero() = if(isNullOrEmpty()) 0 else this!!.toInt()
fun String?.completeWithZero() = if(this.toIntZero() < 10) "0${this}" else this
fun String.stripSlash() = replace("\\","")
