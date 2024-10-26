package es.aitorgu.scrollinfinito

import android.app.Application

/**
 * TaskApplication es la clase de aplicación principal de la app.
 * Inicializa la instancia de Preferences para gestionar las preferencias compartidas.
 */
class TaskApplication : Application() {

    companion object {
        /**
         * Objeto Preferences que permite acceder y modificar las preferencias de la aplicación.
         * Se inicializa cuando se crea la aplicación.
         */
        lateinit var prefs: Preferences
    }

    /**
     * Método onCreate se llama cuando la aplicación es creada.
     * Inicializa el objeto prefs para gestionar las preferencias compartidas.
     */
    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(baseContext)
    }
}
