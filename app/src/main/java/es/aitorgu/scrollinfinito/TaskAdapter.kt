package es.aitorgu.scrollinfinito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * TaskAdapter es un adaptador personalizado para gestionar la visualización de
 * una lista de tareas en un RecyclerView.
 *
 * @param tasks Lista de tareas que se mostrarán en el RecyclerView.
 * @param onItemDone Función lambda que se ejecuta cuando el usuario marca una tarea como realizada.
 *                   Recibe la posición de la tarea en la lista como parámetro.
 */
class TaskAdapter(private val tasks: List<String>, private val onItemDone: (Int) -> Unit) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Infla el diseño de cada elemento de la lista y crea una instancia de TaskViewHolder.
     *
     * @param parent El ViewGroup al que se añadirá el nuevo View.
     * @param viewType Tipo de vista del nuevo View (en este caso, no se utiliza).
     * @return Una nueva instancia de TaskViewHolder para representar el elemento.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Devuelve la cantidad de elementos en la lista de tareas.
     *
     * @return El tamaño de la lista de tareas.
     */
    override fun getItemCount() = tasks.size

    /**
     * Enlaza cada elemento de la lista de tareas con su ViewHolder correspondiente
     * para que sea mostrado en el RecyclerView.
     *
     * @param holder El ViewHolder que representa cada tarea.
     * @param position La posición de la tarea en la lista.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position], onItemDone)
    }
}
