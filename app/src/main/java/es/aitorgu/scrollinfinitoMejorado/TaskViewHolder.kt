package es.aitorgu.scrollinfinitoMejorado

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * TaskViewHolder es una clase ViewHolder personalizada para representar
 * cada elemento de tarea en un RecyclerView.
 *
 * @param view La vista del elemento que contiene los componentes visuales de cada tarea.
 */
class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Elementos visuales de cada tarea en el RecyclerView
    private val tvTask: TextView = view.findViewById(R.id.tvTask)
    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone)

    /**
     * Método para renderizar los datos de una tarea en la vista.
     * Asigna el texto de la tarea al TextView y configura un listener
     * en el ImageView para ejecutar la función de callback cuando se marca como realizada.
     *
     * @param task El texto de la tarea a mostrar.
     * @param onItemDone Función lambda que se ejecuta cuando se hace clic en el icono de tarea completada.
     *                   Recibe la posición del elemento como parámetro.
     */
    fun render(task: String, onItemDone: (Int) -> Unit) {
        tvTask.text = task
        ivTaskDone.setOnClickListener { onItemDone(adapterPosition) }
    }
}
