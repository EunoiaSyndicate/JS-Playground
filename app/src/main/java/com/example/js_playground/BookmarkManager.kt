package com.example.js_playground

import android.content.Context
import android.view.View
import android.widget.PopupMenu

class BookmarkManager(private val context: Context) {

  private val sharedPreferencesKey = "favorites"
  private fun getFavoriteTaskIds(): Set<String> {
    val sharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
    return sharedPreferences.getStringSet(FAVORITE_TASK_IDS_KEY, emptySet()) ?: emptySet()
  }

  fun addFavoriteTask(taskId: String) {
    val favoriteTaskIds = getFavoriteTaskIds().toMutableSet()
    favoriteTaskIds.add(taskId)
    saveFavoriteTaskIds(favoriteTaskIds)
  }

  fun removeFavoriteTask(taskId: String) {
    val favoriteTaskIds = getFavoriteTaskIds().toMutableSet()
    favoriteTaskIds.remove(taskId)
    saveFavoriteTaskIds(favoriteTaskIds)
  }

  private fun saveFavoriteTaskIds(favoriteTaskIds: Set<String>) {
    val sharedPreferences = context.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putStringSet(FAVORITE_TASK_IDS_KEY, favoriteTaskIds)
    editor.apply()
  }

  fun showFavoritesMenu(anchorView: View) {
    val popupMenu = PopupMenu(context, anchorView)
    val favoriteTaskIds = getFavoriteTaskIds()

    for (taskId in favoriteTaskIds) {
      popupMenu.menu.add(taskId).setOnMenuItemClickListener {
        true
      }
    }

    popupMenu.show()
  }

  companion object {
    private const val FAVORITE_TASK_IDS_KEY = "favorite_task_ids"
  }
}