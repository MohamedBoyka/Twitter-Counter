package com.example.twittercounter.copyText

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

class CopyText {
    fun copy(text: String, context: Context) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context,"Copy Text",Toast.LENGTH_SHORT).show()
    }
}