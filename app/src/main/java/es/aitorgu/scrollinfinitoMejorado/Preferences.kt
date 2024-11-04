package es.aitorgu.scrollinfinitoMejorado

import android.content.Context
import android.content.SharedPreferences

/**
 * Clase Preferences para gestionar las preferencias compartidas de la aplicación,
 * incluyendo la funcionalidad para guardar y recuperar tareas.
 *
 * @param context El contexto necesario para acceder a las preferencias compartidas.
 */
class Preferences(context: Context) {

    companion object {
        /**
         * Nombre del archivo de preferencias.
         */
        const val PREFS_NAME = "myDatabase"

        /**
         * Clave utilizada para almacenar el conjunto de tareas.
         */
        const val TASKS = "tasks_value"
    }

    /**
     * Objeto SharedPreferences que permite el acceso y modificación de las preferencias.
     */
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    /**
     * Guarda una lista de tareas en las preferencias compartidas.
     * Convierte la lista a un conjunto de Strings antes de guardarla.
     *
     * @param tasks Lista de tareas que se desea guardar.
     */
    fun saveTasks(tasks: List<String>) {
        prefs.edit().putStringSet(TASKS, tasks.toSet()).apply()
    }

    /**
     * Recupera la lista de tareas guardada en las preferencias compartidas.
     * Convierte el conjunto de Strings almacenado en una lista mutable para su manejo.
     *
     * @return Una lista mutable de tareas recuperadas de las preferencias.
     */
    fun getTasks(): MutableList<String> {
        return prefs.getStringSet(TASKS, emptySet<String>())?.toMutableList() ?: mutableListOf()
    }
}
