package es.aitorgu.scrollinfinitoMejorado

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivity es la actividad principal de la aplicación.
 * Permite al usuario añadir, visualizar y eliminar tareas en una lista.
 */
class MainActivity : AppCompatActivity() {
    // Elementos de la interfaz
    private lateinit var etTask: EditText
    private lateinit var btnAddTask: Button
    private lateinit var rvTasks: RecyclerView

    // Adaptador para gestionar las tareas en el RecyclerView
    private lateinit var adapter: TaskAdapter

    // Instancia de la base de datos
    private lateinit var taskDatabase: TaskDatabase

    /**
     * Método onCreate se llama al iniciar la actividad.
     * Configura la interfaz de usuario y llama a métodos de inicialización.
     *
     * @param savedInstanceState Bundle con el estado de la instancia, si está disponible.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskDatabase = TaskDatabase(this)  // Inicializa la base de datos
        initUi()
    }

    /**
     * Inicializa la interfaz de usuario, incluyendo variables, listeners, y el RecyclerView.
     */
    private fun initUi() {
        initVariables()
        initListeners()
        initRecyclerView()
    }

    /**
     * Configura el RecyclerView para mostrar la lista de tareas.
     * Obtiene las tareas de la base de datos, configura el layout manager,
     * y establece el adaptador para el RecyclerView.
     */
    private fun initRecyclerView() {
        val tasks = taskDatabase.getTasks()  // Obtiene tareas de la base de datos
        rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks, { deleteTask(it) }, { editTask(it) })
        rvTasks.adapter = adapter
    }

    /**
     * Elimina una tarea de la lista tras una confirmación del usuario.
     * Muestra un diálogo de confirmación antes de eliminar la tarea seleccionada.
     *
     * @param position La posición de la tarea en la lista que se desea eliminar.
     */
    private fun deleteTask(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar tarea")
            .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
            .setPositiveButton("Sí") { dialog, _ ->
                val taskId = adapter.getTaskId(position)  // Obtiene el ID de la tarea
                taskDatabase.deleteTask(taskId)  // Elimina la tarea de la base de datos
                adapter.removeTask(position)  // Elimina la tarea del adaptador y notifica al RecyclerView
                playDeleteSound()  // Opcional: reproduce un sonido al eliminar
                adapter.notifyItemRemoved(position)  // Notifica al adaptador que se ha eliminado un elemento
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    /**
     * Inicializa las variables de los elementos de la interfaz de usuario
     * obteniéndolas a través de sus IDs.
     */
    private fun initVariables() {
        etTask = findViewById(R.id.etTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvTasks = findViewById(R.id.rvTasks)
    }

    /**
     * Configura los listeners de los elementos de la interfaz.
     * Añade un listener de clics al botón de añadir tarea.
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener { addTask() }
    }

    /**
     * Añade una nueva tarea a la lista si el campo de texto no está vacío.
     * Guarda la tarea en la base de datos después de agregarla.
     */
    private fun addTask() {
        val newTaskDescription = etTask.text.toString().trim()

        if (newTaskDescription.isEmpty()) {
            etTask.error = "La tarea no puede estar vacía"
            return
        } else {
            playAddSound()
        }

        val newTaskId = taskDatabase.addTask(newTaskDescription)  // Añade la tarea a la base de datos
        adapter.addTask(Task(newTaskId, newTaskDescription))  // Añade la tarea al adaptador
        etTask.setText("")  // Limpia el campo de texto
    }

    /**
     * Reproduce el sonido cuando se añade una tarea.
     */
    private fun playAddSound() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.add_sound)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    /**
     * Método para reproducir el sonido de eliminación de tarea.
     */
    private fun playDeleteSound() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.delete_sound)
        mediaPlayer.start()

        // Liberar los recursos del MediaPlayer después de la reproducción
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    private fun editTask(position: Int) {
        val currentTask = adapter.getTask(position)  // Obtiene la tarea actual del adaptador
        val editText = EditText(this).apply {
            setText(currentTask.description)  // Prellena el EditText con la descripción actual
        }

        AlertDialog.Builder(this)
            .setTitle("Editar tarea")
            .setView(editText)
            .setPositiveButton("Guardar") { dialog, _ ->
                val newTaskDescription = editText.text.toString().trim()
                if (newTaskDescription.isNotEmpty()) {
                    val taskId = currentTask.id
                    taskDatabase.updateTask(taskId, newTaskDescription)  // Actualiza la tarea en la base de datos
                    adapter.updateTask(position, newTaskDescription)  // Actualiza la tarea en el adaptador
                } else {
                    editText.error = "La tarea no puede estar vacía"
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
