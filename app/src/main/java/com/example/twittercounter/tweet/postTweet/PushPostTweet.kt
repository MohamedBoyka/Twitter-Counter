package com.example.twittercounter.tweet.postTweet

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class PushPostTweet(private val context: Context) {

    fun sendTextOnTwitter(message: String) {
        val tweetIntent = createTweetIntent(message)

        if (isTwitterAppInstalled(tweetIntent)) {
            context.startActivity(tweetIntent)
        } else {
            openTwitterInBrowser(message)
        }
    }

    private fun createTweetIntent(message: String): Intent {
        return Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, message)
        }
    }

    private fun isTwitterAppInstalled(intent: Intent): Boolean {
        val packageManager = context.packageManager
        val resolvedInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        return resolvedInfoList.any { resolveInfo ->
            resolveInfo.activityInfo.packageName.startsWith("com.twitter.android").also { found ->
                if (found) {
                    intent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name
                    )
                }
            }
        }
    }

    private fun openTwitterInBrowser(message: String) {
        val webIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://twitter.com/intent/tweet?text=${urlEncode(message)}")
        }
        context.startActivity(webIntent)
        Toast.makeText(context, "Twitter app not found. Redirecting to browser.", Toast.LENGTH_LONG).show()
    }

    private fun urlEncode(text: String): String {
        return try {
            URLEncoder.encode(text, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            Log.e("PushPostTweet", "UTF-8 encoding failed", e)
            ""
        }
    }
}
