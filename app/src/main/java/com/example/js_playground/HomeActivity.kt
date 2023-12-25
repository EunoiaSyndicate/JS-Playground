package com.example.js_playground

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.nio.charset.StandardCharsets

class HomeActivity : AppCompatActivity() {
  private lateinit var themeManager: ThemeManager
  private lateinit var bookmarkManager: BookmarkManager
  private lateinit var bookmarkButton: ImageButton
  private lateinit var menuButton: ImageButton
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    themeManager = ThemeManager(this)
    themeManager.applyTheme()
    setContentView(R.layout.activity_home)
    val taskList = getCardList()
    fillRecyclerView(taskList)

    bookmarkButton = findViewById(R.id.bookmark_button)
    bookmarkManager = BookmarkManager(this)
    bookmarkButton.setOnClickListener { bookmarkManager.showFavoritesMenu(it) }
    themeManager.setLightModeClickListener(R.id.light_mode_button)
    themeManager.updateLightModeButton(R.id.light_mode_button)

    val infoButton = findViewById<ImageButton>(R.id.info_button)
    infoButton.setOnClickListener {
      val intent = Intent(this, AboutUsActivity::class.java)
      startActivity(intent)
    }

    val toMainButton = findViewById<ImageButton>(R.id.to_home_button)
    toMainButton.setOnClickListener {
      val intent = Intent(this, MainActivity::class.java)
      startActivity(intent)
    }

    menuButton = findViewById(R.id.menu_button)
    menuButton.setOnClickListener {
      showPopupMenu(it)
    }
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

  private fun showPopupMenu(view: View) {
    val popupMenu = PopupMenu(this, view)
    popupMenu.inflate(R.menu.menu_home)

    popupMenu.setOnMenuItemClickListener { item ->
      when (item.itemId) {
        R.id.action_bookmarks -> {
          bookmarkManager.showFavoritesMenu(view)
          true
        }
        R.id.action_about_us -> {
          startActivity(Intent(this, AboutUsActivity::class.java))
          true
        }
        R.id.action_change_theme -> {
          themeManager.toggleTheme()
          true
        }
        R.id.action_to_main -> {
          startActivity(Intent(this, MainActivity::class.java))
          true
        }
        else -> false
      }
    }
    popupMenu.show()
  }
}