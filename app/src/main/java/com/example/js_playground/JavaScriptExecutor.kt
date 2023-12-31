package com.example.js_playground

import android.content.Context
import androidx.javascriptengine.JavaScriptSandbox
import java.util.concurrent.TimeUnit

class JavaScriptExecutor(private val context: Context) {
  fun executeScript(): Boolean {
    val jsSandbox = JavaScriptSandbox.createConnectedInstanceAsync(context).get()
    val jsIsolate = jsSandbox.createIsolate()
    val code = "function sum(a, b) { let r = a + b; return r.toString(); }; sum(3, 4)"
    val resultFuture = jsIsolate.evaluateJavaScriptAsync(code)
    val result = resultFuture[5, TimeUnit.SECONDS]
    println(result)
    return true
  }
}