package com.example.twittercounter.ui.viewModel

import android.content.Context
import android.widget.EditText
import androidx.lifecycle.ViewModel
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.twittercounter.copyText.CopyText
import com.example.twittercounter.tweet.postTweet.PushPostTweet
import com.example.twittercounter.tweet.twitter4j.IntegrationSDK

class HandlingActionViewModel(private val context: Context , private val lifecycleScope: LifecycleCoroutineScope) : ViewModel() {

    private val pushPostTweet by lazy { PushPostTweet(context) }
    private val integrationSDK by lazy { IntegrationSDK(context , lifecycleScope) }

    private val copyText by lazy { CopyText() }

    fun onCopyTextClicked(string: String) {
        copyText.copy(string, context)
        Toast.makeText(context, "cody text", Toast.LENGTH_SHORT).show()
    }

    fun onClearTextClicked(editText: EditText) {
        editText.text.clear()
    }

    fun onPostTweetClicked(message: String) {
        if (message.isBlank()) {
            Toast.makeText(context, "Text is empty", Toast.LENGTH_SHORT).show()
        } else {


            //TODO

            /*
               I sorry for not following the required steps as outlined in PDF

               I attempted to work with the integration SDK using
               - "com.twitter.sdk.android", but it did not function as expected
               - The versions com.twitter.sdk.android:twitter:3.3.0, 3.2.0, and 3.1.1
                all resulted in a "Could not find" error

                I also worked with the API and carefully followed the documentation available
                at https://developer.x.com, adhering to each step as instructed
                Despite entering the "consumer_key", "consumer_secret", "access_token", "token_secret", and "bearer_token",
                I received an "Unauthorized" response. A screenshot illustrating this issue is
                attached in the drawable folder as api_x_app.png and api_twitter.png

                But after all that, I solved the problem in some way

                But I'm sorry again

            */



          //  integrationSDK.initialize()

            pushPostTweet.sendTextOnTwitter(message)
        }
    }
}
