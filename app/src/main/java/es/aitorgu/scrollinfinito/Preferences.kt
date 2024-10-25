package es.aitorgu.scrollinfinito

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {
    companion object{
        const val PREFS_NAME="mydatabase"
        const val TASKS="tasks_value"
    }

    val prefs:SharedPreferences=context.getSharedPreferences(PREFS_NAME,0)

    fun saveTasks(tasks:List<String>){
    prefs.edit().putStringSet(TASKS,tasks.toSet()).apply()

    }
}