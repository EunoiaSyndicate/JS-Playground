package com.example.js_playground

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TaskActivity : AppCompatActivity(), ErrorHandlerInterface {
  companion object {
    const val TASK = "task"
  }

  private lateinit var task: Task
  private lateinit var starButton: ImageButton
  private lateinit var titleTextView: TextView
  private lateinit var testsResult: TextView
  private lateinit var bookmarkButton: ImageButton
  private var isStarred = false
  private lateinit var bookmarkManager: BookmarkManager
  private lateinit var cardId: String
  private lateinit var codeView: CodeView
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
    javaScriptExecutor = JavaScriptExecutor()

    setListeners()
    val json = intent.getStringExtra(TASK)
    task = Json.decodeFromString<Task>(json!!)
    codeView = this.findViewById(R.id.codeView)
    EditorConfigurator.configure(this, codeView)
    setTaskData()

    titleTextView = findViewById(R.id.task_header)
    testsResult = findViewById(R.id.tests_result)
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


  private fun setTaskData() {
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

  override fun showError(message: String) {
    testsFragment.background = ColorDrawable(getColor(R.color.fail))
    testsResult.text = message
  }

  private fun showTestResult(results: List<Boolean>) {
    var message = ""
    if (results.all { it }) {
      testsFragment.background = ColorDrawable(getColor(R.color.success))
    } else {
      testsFragment.background = ColorDrawable(getColor(R.color.fail))
    }
    results.forEachIndexed { index, b ->
      val symbol = if (b) '+' else '-'
      val input = Json.encodeToString(task.testInputs[index])
      val output = Json.encodeToString(task.testOutputs[index])
      message += "$symbol $input -> $output\n"
    }
    testsResult.text = message
  }

  private fun onClickDone() {
    onClickTests()
    val results = mutableListOf<Boolean>()
    val userInput = codeView.text.toString()
    val testInputs = task.testInputs
    testInputs.forEachIndexed { index, item ->
      val res = javaScriptExecutor.executeScript(this, userInput, item)
      if (res === null) return
      results.add(res.toString() == task.testOutputs[index])
    }
    showTestResult(results)
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