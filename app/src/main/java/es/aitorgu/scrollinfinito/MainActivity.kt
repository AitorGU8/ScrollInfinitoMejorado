package es.aitorgu.scrollinfinito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.aitorgu.scrollinfinito.TaskApplication.Companion.prefs

class MainActivity : AppCompatActivity() {
    lateinit var etTask: EditText
    lateinit var btnAddTask: Button
    lateinit var rvTasks: RecyclerView

    lateinit var adapter:TaskAdapter

    var tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun initUi() {
        initVariables()
        initListeners()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) {deleteTask(it)}
        rvTasks.adapter = adapter

    }

    private fun deleteTask(position: Int) {
        // Crea el cuadro de diálogo de confirmación
        AlertDialog.Builder(this)
            .setTitle("Eliminar tarea")
            .setMessage("¿Estás seguro de que deseas eliminar esta tarea?")
            .setPositiveButton("Sí") { dialog, _ ->
                // Si el usuario confirma, eliminamos la tarea
                tasks.removeAt(position)
                adapter.notifyDataSetChanged()
                prefs.saveTasks(tasks)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Si el usuario cancela, cerramos el diálogo
                dialog.dismiss()
            }
            .show()
    }


    private fun initVariables() {
        etTask = findViewById(R.id.etTask)
        btnAddTask = findViewById(R.id.btnAddTask)
        rvTasks = findViewById(R.id.rvTasks)
    }

    private fun initListeners() {
        btnAddTask.setOnClickListener {addTask()}
    }

    private fun addTask() {
        // Eliminamos espacios en blanco adicionales
        val newTask = etTask.text.toString().trim()

        if (newTask.isEmpty()) {
            etTask.error = "La tarea no puede estar vacía"
            return  // Salimos del método si la tarea está vacía
        }

        tasks.add(newTask)
        prefs.saveTasks(tasks)
        adapter.notifyDataSetChanged()
        // Limpiamos el campo de texto después de añadir la tarea
        etTask.setText("")
    }
}