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
    private val ivEditTask: ImageView = view.findViewById(R.id.ivEditTask)

    /**
     * Método para renderizar los datos de una tarea en la vista.
     * Asigna el texto de la tarea al TextView y configura listeners
     * para los iconos de edición y marcado como realizada.
     *
     * @param task La tarea a mostrar.
     * @param onItemDone Función lambda que se ejecuta cuando se hace clic en el icono de tarea completada.
     *                   Recibe la posición del elemento como parámetro.
     * @param onItemEdit Función lambda que se ejecuta cuando se hace clic en el icono de editar tarea.
     *                   Recibe la posición del elemento como parámetro.
     */
    fun render(task: Task, onItemDone: (Int) -> Unit, onItemEdit: (Int) -> Unit) {
        tvTask.text = task.description

        // Configura el listener para marcar la tarea como completada
        ivTaskDone.setOnClickListener {
            // Aquí podrías cambiar el estado de la tarea o realizar alguna acción específica
            onItemDone(adapterPosition)
        }

        // Configura el listener para editar la tarea
        ivEditTask.setOnClickListener {
            onItemEdit(adapterPosition)
        }
    }
}
