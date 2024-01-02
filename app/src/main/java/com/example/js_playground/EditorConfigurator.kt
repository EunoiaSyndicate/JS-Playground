package com.example.js_playground

import android.content.Context
import android.graphics.Color
import com.amrdeveloper.codeview.CodeView
import java.util.regex.Pattern

class EditorConfigurator {
  companion object {
    private val syntaxPatterns = mapOf<Pattern, Int>(
      Pattern.compile("\\b(if|else|case|default|for|switch|while|function|return|var|let|const|in|of)\\b|=>") to Color.rgb(0,0,255),
      Pattern.compile("\"(?:[^\"\\\\]|\\\\.)*\"|'(?:[^'\\\\]|\\\\.)*'") to Color.rgb(0,128,0),
      Pattern.compile("(//.*\$|/\\*[\\s\\S]*?\\*/)") to Color.rgb(128,128,128),
      Pattern.compile("\\b\\d+\\b") to Color.rgb(128,0,128),
    )

    private val pairCompleteMap = mapOf(
      '{' to '}',
      '[' to ']',
      '(' to ')',
      '\'' to '\'',
      '"' to '"',
    )

    fun configure(context: Context, codeView: CodeView) {
      codeView.setEnableLineNumber(true)
      codeView.setEnableHighlightCurrentLine(true)
      codeView.enablePairComplete(true)
      codeView.setEnableAutoIndentation(true)
      codeView.setLineNumberTextColor(Color.GRAY)
      codeView.setHighlightCurrentLineColor(context.getColor(R.color.highlight_grey))
      codeView.setLineNumberTextSize(40f)
      codeView.setSyntaxPatternsMap(syntaxPatterns)
      codeView.setPairCompleteMap(pairCompleteMap)
      codeView.setIndentationStarts(setOf('{', '.'));
      codeView.setIndentationEnds(setOf('}'));
      codeView.setTabLength(2)
    }
  }
}