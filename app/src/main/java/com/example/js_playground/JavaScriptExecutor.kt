package com.example.js_playground

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8ScriptCompilationException
import com.eclipsesource.v8.V8ScriptExecutionException

class JavaScriptExecutor {
  fun executeScript(handler: ErrorHandlerInterface, userInput: String, list: List<String>): Any? {
    val runtime = V8.createV8Runtime()
    val args = list.joinToString(", ")
    try {
      return runtime.executeScript("($userInput)($args)")
    } catch (e: V8ScriptCompilationException) {
      handler.showError(e.jsMessage)
    }
    return null
  }
}