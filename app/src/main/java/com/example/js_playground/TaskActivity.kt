package com.example.js_playground

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amrdeveloper.codeview.CodeView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class TaskActivity : AppCompatActivity() {
  companion object {
    const val TASK = "task"
  }

  private lateinit var taskFragment: FrameLayout
  private lateinit var editorFragment: FrameLayout
  private lateinit var testsFragment: FrameLayout
  private lateinit var javaScriptExecutor: JavaScriptExecutor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_task)
    taskFragment = this.findViewById(R.id.task_fragment)
    editorFragment = this.findViewById(R.id.editor_fragment)
    testsFragment = this.findViewById(R.id.tests_fragment)
    javaScriptExecutor = JavaScriptExecutor(this)

    setListeners()
    val json = intent.getStringExtra(TASK)
    val task = Json.decodeFromString<Task>(json!!)
    val codeView = this.findViewById<CodeView>(R.id.codeView)
    EditorConfigurator.configure(this, codeView)
    setTaskData(task)
  }

  private fun setTaskData(task: Task) {
    val taskHeader = findViewById<TextView>(R.id.task_header)
    val taskDescription = findViewById<TextView>(R.id.task_description)
    taskHeader.text = task.title
    taskDescription.text = task.description
  }

  private fun setListeners() {
    findViewById<Button>(R.id.task_button).setOnClickListener { onClickTask() }
    findViewById<Button>(R.id.editor_button).setOnClickListener { onClickEditor() }
    findViewById<Button>(R.id.tests_button).setOnClickListener { onClickTests() }
    findViewById<FloatingActionButton>(R.id.done_button).setOnClickListener { onClickDone() }
  }

  private fun onClickTask() {
    taskFragment.visibility = View.VISIBLE
    editorFragment.visibility = View.GONE
    testsFragment.visibility = View.GONE
  }

  private fun onClickEditor() {
    taskFragment.visibility = View.GONE
    editorFragment.visibility = View.VISIBLE
    testsFragment.visibility = View.GONE
  }

  private fun onClickTests() {
    taskFragment.visibility = View.GONE
    editorFragment.visibility = View.GONE
    testsFragment.visibility = View.VISIBLE
  }

  private fun onClickDone() {
    javaScriptExecutor.executeScript()
  }
}