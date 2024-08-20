package com.example.twittercounter

import android.widget.EditText
import android.widget.TextView

class RemainingCharacter {
    fun updateRemainingCharacters(editText: EditText, textView: TextView, maxLength: Int) {
        val remaining = maxLength - editText.text.length
        textView.text = remaining.toString()
    }
}