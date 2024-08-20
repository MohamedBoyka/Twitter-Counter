package com.example.twittercounter.ui.viewModel

import android.content.Context
import android.widget.EditText
import androidx.lifecycle.ViewModel
import android.widget.Toast
import com.example.twittercounter.copyText.CopyText
import com.example.twittercounter.tweet.postTweet.PushPostTweet

class HandlingActionViewModel(private val context: Context) : ViewModel() {

    private val pushPostTweet by lazy { PushPostTweet(context) }
    private val copyText by lazy { CopyText() }

    fun onCopyTextClicked(string: String) {
        Toast.makeText(context, "cody text", Toast.LENGTH_SHORT).show()
        copyText.copy(string, context)
    }

    fun onClearTextClicked(editText: EditText) {
        editText.text.clear()
    }

    fun onPostTweetClicked(message: String) {
        if (message.isBlank()) {
            Toast.makeText(context, "Text is empty", Toast.LENGTH_SHORT).show()
        } else {

            /*  twitterIntegration = IntegrationSDK(this, lifecycleScope)
              twitterIntegration.initialize()*/

            pushPostTweet.sendTextOnTwitter(message)
        }
    }
}
