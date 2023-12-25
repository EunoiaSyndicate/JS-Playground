package com.example.js_playground

import android.content.res.Configuration
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ThemeManager(private val activity: AppCompatActivity) {
  private val lightMode: Int = AppCompatDelegate.MODE_NIGHT_NO
  private val darkMode: Int = AppCompatDelegate.MODE_NIGHT_YES
  private fun getCurrentTheme(): Int {
    return when (activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
      Configuration.UI_MODE_NIGHT_NO -> lightMode
      Configuration.UI_MODE_NIGHT_YES -> darkMode
      else -> lightMode
    }
  }

  fun applyTheme() {
    AppCompatDelegate.setDefaultNightMode(getCurrentTheme())
  }

  fun toggleTheme() {
    val newMode = if (getCurrentTheme() == lightMode) darkMode
    else lightMode

    AppCompatDelegate.setDefaultNightMode(newMode)
    activity.recreate()
  }

  fun setLightModeClickListener(buttonId: Int) {
    val lightModeButton = activity.findViewById<ImageButton>(buttonId)
    lightModeButton.setOnClickListener {
      toggleTheme()
    }
  }

  private fun getLightModeButtonDrawable(): Int {
    return if (getCurrentTheme() != lightMode) R.drawable.light_mode_24px
    else R.drawable.dark_mode_24px
  }

  fun updateLightModeButton(buttonId: Int) {
    val lightModeButton = activity.findViewById<ImageButton>(buttonId)
    lightModeButton.setImageResource(getLightModeButtonDrawable())
  }
}