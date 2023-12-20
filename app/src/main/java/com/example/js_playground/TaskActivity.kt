package com.example.js_playground

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TaskActivity : AppCompatActivity() {
  companion object {
    const val EXTRA_TASK_DATA = "extra_task_data"
  }

  private lateinit var titleTextView: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_task)

    titleTextView = findViewById(R.id.textView)

    val taskData = intent.getParcelableExtra<CardData>(EXTRA_TASK_DATA)

    titleTextView.text = taskData?.title
  }

  fun openTasksTextView(view: View) {
    titleTextView.text = "Tasks Button Clicked"
  }

  fun openCodeEditor(view: View) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragmentContainer, CodeEditorFragment())
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
  }

  fun openTestsResultTextView(view: View) {
    titleTextView.text = "Tests Result Button Clicked"
  }
}