package com.example.js_playground

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val startButton = findViewById<Button>(R.id.start_button)
    startButton.setOnClickListener { onStartButtonClick() }
  }

  private fun onStartButtonClick() {
    val intent = Intent(this, HomeActivity::class.java)
    startActivity(intent)
  }
}