package com.example.mvvm_template.data.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

const val SHARED_PREFERENCES_FILE_NAME = "ace_store"
const val INTRO_KEY="into"
class AppPrefrancesImp @Inject constructor(context: Context) : AppPrefrances {

    private val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)

    override fun setSkipIntro(skipIntro: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(INTRO_KEY,skipIntro)
        editor.apply()
    }


    override fun isSkipIntro(): Boolean {
        return sharedPref.getBoolean(INTRO_KEY,false)
    }

    override fun getLang(): String {
        return sharedPref.getString("lang","ar")!!
    }

    override fun setLang(str: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("lang",str)
        editor.apply()
    }
}