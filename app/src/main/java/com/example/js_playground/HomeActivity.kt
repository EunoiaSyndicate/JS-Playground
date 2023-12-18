package com.example.js_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    example()
  }

  private fun example() {
    val recyclerView = findViewById<RecyclerView>(R.id.task_recycler)
    val itemList = listOf(
      CardData("Title1", "✔️", "Easy"),
      CardData("Title2", "❌", "Medium"),
      CardData("Title3", "❌", "Easy"),
      CardData("Title4", "✔️", "Easy"),
      CardData("Title5", "❌", "Medium"),
      CardData("Title6", "✔️", "Hard"),
      CardData("Title7", "✔️", "Hard"),
      CardData("Title8", "❌", "Easy"),
    )

    recyclerView.layoutManager = GridLayoutManager(this, 2)
    recyclerView.adapter = CardAdapter(this, itemList)
  }
}