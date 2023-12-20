package com.example.js_playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amrdeveloper.codeview.CodeView


class CodeEditorFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_code_editor, container, false)
    val codeView: CodeView = view.findViewById(R.id.codeView)

    return view
  }
}