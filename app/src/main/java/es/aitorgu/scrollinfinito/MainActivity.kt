package es.aitorgu.scrollinfinito

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.aitorgu.scrollinfinito.TaskApplication.Companion.prefs

class MainActivity : AppCompatActivity() {

    private lateinit var btnAddTask:Button
    private lateinit var etTask:EditText
    private lateinit var rvTask:RecyclerView

    private lateinit var adapter:TaskAdapter

    var tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUi()
    }

    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTask.layoutManager = LinearLayoutManager(this)
        adapter= TaskAdapter(tasks,{deleteTask(it)})
        rvTask.adapter = adapter
    }
    private fun deleteTask(position:Int){
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        prefs.saveTasks(tasks)
    }

    private fun initListeners() {
       btnAddTask.setOnClickListener{addTask()}
    }

    private fun addTask() {
        val taskToAdd=etTask.text.toString()
        tasks.add(taskToAdd)
        adapter.notifyDataSetChanged()
        etTask.setText("")
        prefs.saveTasks(tasks)
    }

    private fun initView() {
        btnAddTask=findViewById<Button>(R.id.btnAddTask)
        etTask=findViewById<EditText>(R.id.etTask)
        rvTask=findViewById<RecyclerView>(R.id.rvTask)

    }


}