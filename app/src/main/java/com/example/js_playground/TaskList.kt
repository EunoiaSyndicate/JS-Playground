package com.example.js_playground

import kotlinx.serialization.Serializable

@Serializable
data class Task(
  val title: String,
  val status: String,
  val difficulty: String,
  val description: String,
  val testInputs: List<List<String>>,
  val testOutputs: List<String>,
)

@Serializable
data class TaskList(val tasks: List<Task>)
