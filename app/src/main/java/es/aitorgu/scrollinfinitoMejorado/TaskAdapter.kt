package es.aitorgu.scrollinfinitoMejorado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * TaskAdapter es un adaptador personalizado para gestionar la visualización de
 * una lista de tareas en un RecyclerView.
 *
 * @param tasks Lista de tareas que se mostrarán en el RecyclerView.
 * @param onItemDelete Función lambda que se ejecuta cuando el usuario elimina una tarea.
 *                     Recibe la posición de la tarea en la lista como parámetro.
 * @param onItemEdit Función lambda que se ejecuta cuando el usuario edita una tarea.
 *                   Recibe la posición de la tarea en la lista como parámetro.
 */
class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onItemDelete: (Int) -> Unit,
    private val onItemEdit: (Int) -> Unit
) : RecyclerView.Adapter<TaskViewHolder>() {

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
        holder.render(tasks[position], onItemDelete, onItemEdit)
    }

    /**
     * Agrega una nueva tarea a la lista y notifica al adaptador sobre el nuevo elemento.
     *
     * @param task La tarea que se desea agregar.
     */
    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    /**
     * Actualiza una tarea existente en la lista y notifica al adaptador sobre el cambio.
     *
     * @param position La posición de la tarea a actualizar.
     * @param newDescription La nueva descripción de la tarea.
     */
    fun updateTask(position: Int, newDescription: String) {
        tasks[position] = tasks[position].copy(description = newDescription)
        notifyItemChanged(position)
    }

    /**
     * Obtiene la tarea en una posición específica.
     *
     * @param position La posición de la tarea en la lista.
     * @return La tarea en la posición especificada.
     */
    fun getTask(position: Int): Task {
        return tasks[position]
    }

    /**
     * Obtiene el ID de la tarea en una posición específica.
     *
     * @param position La posición de la tarea en la lista.
     * @return El ID de la tarea.
     */
    fun getTaskId(position: Int): Long {
        return tasks[position].id
    }

    /**
     * Elimina una tarea de la lista en una posición específica y notifica al adaptador.
     *
     * @param position La posición de la tarea en la lista que se desea eliminar.
     *
     * Esta función elimina la tarea de la lista interna `tasks` en la posición dada.
     * Después de eliminarla, notifica al adaptador sobre la eliminación del elemento
     * y actualiza el rango de elementos en el RecyclerView, asegurando que las posiciones
     * de los elementos restantes estén sincronizadas con la vista.
     */
    fun removeTask(position: Int) {
        tasks.removeAt(position)  // Elimina la tarea de la lista interna
        notifyItemRemoved(position)  // Notifica al adaptador que se eliminó un ítem en esta posición
        notifyItemRangeChanged(position, tasks.size)  // Actualiza las posiciones en la vista
    }

}
