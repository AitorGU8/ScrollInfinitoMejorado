package es.aitorgu.scrollinfinito

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    companion object{
        const val PREFS_NAME="mydatabase"
    }

    val prefs:SharedPreferences=context.getSharedPreferences(PREFS_NAME,0)

}