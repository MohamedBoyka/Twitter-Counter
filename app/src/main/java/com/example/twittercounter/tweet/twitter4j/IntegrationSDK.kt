package com.example.twittercounter.tweet.twitter4j

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.twittercounter.utility.TwitterConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

class IntegrationSDK(
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope
) {

    private lateinit var twitter: Twitter
    private lateinit var twitterDialog: Dialog
    private var accessToken: AccessToken? = null

    fun initialize() {
        requestTwitterToken()
    }

    private fun requestTwitterToken() {
        lifecycleScope.launch(Dispatchers.IO) {
            setupTwitterInstance()
            authorizeUser()
        }
    }

    private fun setupTwitterInstance() {
        val configuration = ConfigurationBuilder()
            .setDebugEnabled(true)
            .setOAuthConsumerKey(TwitterConstants.CONSUMER_KEY)
            .setOAuthConsumerSecret(TwitterConstants.CONSUMER_SECRET)
            .setIncludeEmailEnabled(true)
            .build()

        twitter = TwitterFactory(configuration).instance
    }

    private suspend fun authorizeUser() {
        try {
            val requestToken = twitter.oAuthRequestToken
            showAuthorizationDialog(requestToken.authorizationURL)
        } catch (e: IllegalStateException) {
            Log.e("TwitterAuthorizationError", e.toString())
        }
    }

    private suspend fun showAuthorizationDialog(url: String) {
        withContext(Dispatchers.Main) {
            val webView = createWebView(url)
            twitterDialog = Dialog(context).apply {
                setContentView(webView)
                show()
            }
        }
    }

    private fun createWebView(url: String): WebView {
        return WebView(context).apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            webViewClient = TwitterWebViewClient()
            loadUrl(url)
        }
    }

    inner class TwitterWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            return if (url.startsWith(TwitterConstants.CALLBACK_URL)) {
                handleCallbackUrl(url)
                twitterDialog.dismiss()
                true
            } else {
                false
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return if (url?.startsWith(TwitterConstants.CALLBACK_URL) == true) {
                handleCallbackUrl(url)
                twitterDialog.dismiss()
                true
            } else {
                false
            }
        }
    }

    private fun handleCallbackUrl(url: String) {
        val oauthVerifier = Uri.parse(url).getQueryParameter("oauth_verifier") ?: ""
        retrieveAccessToken(oauthVerifier)
    }

    private fun retrieveAccessToken(oauthVerifier: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            accessToken = twitter.getOAuthAccessToken(oauthVerifier)
            fetchUserProfile()
        }
    }

    private suspend fun fetchUserProfile() {
        val user = withContext(Dispatchers.IO) { twitter.verifyCredentials() }
        logUserProfile(user)
    }

    private fun logUserProfile(user: twitter4j.User) {
        Log.d("TwitterId", user.id.toString())
        Log.d("TwitterHandle", user.screenName)
        Log.d("TwitterName", user.name)
        Log.d("TwitterEmail", user.email ?: "Email permission not granted")
        Log.d("TwitterProfilePic", user.profileImageURLHttps.replace("_normal", ""))
        Log.d("TwitterAccessToken", accessToken?.token ?: "")
    }
}
