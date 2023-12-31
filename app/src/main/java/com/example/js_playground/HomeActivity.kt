package com.example.js_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.nio.charset.StandardCharsets

class HomeActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    val taskList = getCardList()
    fillRecyclerView(taskList)
  }

  private fun fillRecyclerView(taskList: TaskList) {
    val recyclerView = findViewById<RecyclerView>(R.id.task_recycler)
    recyclerView.layoutManager = GridLayoutManager(this, 2)
    recyclerView.adapter = CardAdapter(this, taskList)
  }

  private fun getCardList(): TaskList {
    val inputStream = resources.openRawResource(R.raw.tasks)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    val jsonString = String(buffer, StandardCharsets.UTF_8)
    return Json.decodeFromString<TaskList>(jsonString)
  }
}