package es.aitorgu.scrollinfinitoMejorado

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.aitorgu.scrollinfinitoMejorado.TaskApplication.Companion.prefs

/**
 * MainActivity es la actividad principal de la aplicación.
 * Permite al usuario añadir, visualizar y eliminar tareas en una lista.
 */
class MainActivity : AppCompatActivity() {
    // Elementos de la interfaz
    lateinit var etTask: EditText
    lateinit var btnAddTask: Button
    lateinit var rvTasks: RecyclerView

    // Adaptador para gestionar las tareas en el RecyclerView
    lateinit var adapter: TaskAdapter

    // Lista mutable que almacena las tareas del usuario
    var tasks = mutableListOf<String>()

    /**
     * Método onCreate se llama al iniciar la actividad.
     * Configura la interfaz de usuario y llama a métodos de inicialización.
     *
     * @param savedInstanceState Bundle con el estado de la instancia, si está disponible.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
     * Obtiene las tareas guardadas de las preferencias, configura el layout manager,
     * y establece el adaptador para el RecyclerView.
     */
    private fun initRecyclerView() {
        tasks = prefs.getTasks()
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
                playDeleteSound()
                tasks.removeAt(position)
                adapter.notifyDataSetChanged()
                prefs.saveTasks(tasks)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
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
     * Muestra un error si el campo está vacío, y guarda la lista en las preferencias
     * después de agregar la nueva tarea.
     */
    private fun addTask() {
        val newTask = etTask.text.toString().trim()

        if (newTask.isEmpty()) {
            etTask.error = "La tarea no puede estar vacía"
            return
        }else{
            playAddSound()
        }

        tasks.add(newTask)
        prefs.saveTasks(tasks)
        adapter.notifyDataSetChanged()
        etTask.setText("")
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
        val currentTask = tasks[position]
        val editText = EditText(this).apply {
            setText(currentTask)
        }

        AlertDialog.Builder(this)
            .setTitle("Editar tarea")
            .setView(editText)
            .setPositiveButton("Guardar") { dialog, _ ->
                val newTaskText = editText.text.toString().trim()
                if (newTaskText.isNotEmpty()) {
                    tasks[position] = newTaskText  // Actualiza la tarea en la lista
                    adapter.notifyItemChanged(position)  // Notifica el cambio
                    prefs.saveTasks(tasks)  // Guarda la lista actualizada
                } else {
                    editText.error = "La tarea no puede estar vacía"
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
