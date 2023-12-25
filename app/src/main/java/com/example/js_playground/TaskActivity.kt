package com.example.js_playground

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.PopupMenu
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

  private lateinit var starButton: ImageButton
  private lateinit var titleTextView: TextView
  private lateinit var bookmarkButton: ImageButton
  private var isStarred = false
  private lateinit var bookmarkManager: BookmarkManager
  private lateinit var cardId: String
  private lateinit var popupMenu: PopupMenu
  private lateinit var menuButton: ImageButton
  private lateinit var themeManager: ThemeManager
  private lateinit var taskFragment: FrameLayout
  private lateinit var editorFragment: FrameLayout
  private lateinit var testsFragment: FrameLayout
  private lateinit var javaScriptExecutor: JavaScriptExecutor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    themeManager = ThemeManager(this)
    themeManager.applyTheme()
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

    titleTextView = findViewById(R.id.task_header)
    cardId = task.title
    titleTextView.text = task.title

    themeManager.setLightModeClickListener(R.id.light_mode_button)
    themeManager.updateLightModeButton(R.id.light_mode_button)

    val toHomeButton = findViewById<ImageButton>(R.id.to_home_button)
    toHomeButton.setOnClickListener {
      val intent = Intent(this, HomeActivity::class.java)
      startActivity(intent)
    }

    bookmarkManager = BookmarkManager(this)
    starButton = findViewById(R.id.star)
    starButton.setOnClickListener { toggleStarState() }

    val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
    isStarred = sharedPreferences.getBoolean(cardId, false)
    updateStarButton()

    bookmarkButton = findViewById(R.id.bookmark_button)
    bookmarkButton.setOnClickListener { bookmarkManager.showFavoritesMenu(it) }

    popupMenu = PopupMenu(this, bookmarkButton)
    popupMenu.inflate(R.menu.menu_favorites)

    val infoButton = findViewById<ImageButton>(R.id.info_button)
    infoButton.setOnClickListener {
      val intent = Intent(this, AboutUsActivity::class.java)
      startActivity(intent)
    }

    menuButton = findViewById(R.id.menu_button)
    menuButton.setOnClickListener {
      showPopupMenu(it)
    }
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

  private fun toggleStarState() {
    isStarred = !isStarred

    val sharedPreferences = getPreferences(Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(cardId, isStarred)
    editor.apply()

    if (isStarred) bookmarkManager.addFavoriteTask(cardId)
    else bookmarkManager.removeFavoriteTask(cardId)

    updateStarButton()
  }

  private fun updateStarButton() {
    val drawableResId = if (isStarred) R.drawable.baseline_grade_24
    else R.drawable.baseline_star_border_24

    starButton.setImageResource(drawableResId)
  }

  private fun showPopupMenu(view: View) {
    val popupMenu = PopupMenu(this, view)
    popupMenu.inflate(R.menu.menu_task)

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
        R.id.action_to_home -> {
          startActivity(Intent(this, HomeActivity::class.java))
          true
        }
        R.id.action_add_to_favourites -> {
          bookmarkManager.addFavoriteTask(cardId)
          toggleStarState()
          true
        }
        else -> false
      }
    }
    popupMenu.show()
  }
}