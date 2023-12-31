package com.example.js_playground

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CardAdapter(private val context: Context, private val taskList: TaskList) :
  RecyclerView.Adapter<CardAdapter.ViewHolder>() {

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
      val layoutParams = itemView.layoutParams as GridLayoutManager.LayoutParams
      layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
      layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
      layoutParams.setMargins(30)
      itemView.layoutParams = layoutParams
    }

    val cardTitle: TextView = itemView.findViewById(R.id.card_title)
    val cardStatus: TextView = itemView.findViewById(R.id.card_status)
    val cardDifficultly: TextView = itemView.findViewById(R.id.card_difficulty)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return taskList.tasks.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = taskList.tasks[position]
    val status = context.getString(R.string.status)
    val difficultly = context.getString(R.string.difficultly)

    holder.cardTitle.text = item.title
    holder.cardStatus.text = String.format("%s: %s",status, item.status)
    holder.cardDifficultly.text = String.format("%s: %s", difficultly, item.difficulty)
    holder.itemView.setOnClickListener { onClickCardListener(item) }
  }

  private fun onClickCardListener(item: Task) {
    val intent = Intent(context, TaskActivity::class.java)
    val json = Json.encodeToString(item)
    intent.putExtra(TaskActivity.TASK, json)
    context.startActivity(intent)
  }
}