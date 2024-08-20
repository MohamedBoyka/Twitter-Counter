package com.example.twittercounter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.twittercounter.RemainingCharacter.RemainingCharacter
import com.example.twittercounter.copyText.CopyText
import com.example.twittercounter.databinding.ActivityMainBinding
import com.example.twittercounter.tweet.postTweet.PushPostTweet

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val maxLength = 280
    private val pushPostTweet by lazy { PushPostTweet(this) }
    private val copyText by lazy { CopyText() }
    private val remainingCharacter by lazy { RemainingCharacter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatcher(maxLength)
        initializeActions()
    }

    private fun initializeActions() {
        binding.btnPushPost.setOnClickListener {
            val message = binding.edInput.text.toString()
            if (message.isBlank()) {
                Toast.makeText(this, "Text is empty", Toast.LENGTH_SHORT).show()
            } else {

                /*  twitterIntegration = IntegrationSDK(this, lifecycleScope)
                  twitterIntegration.initialize()*/

                pushPostTweet.sendTextOnTwitter(message)
            }
        }

        binding.btnCopy.setOnClickListener {
            copyText.copy(binding.edInput.text.toString(), this)
        }

        binding.btnClear.setOnClickListener {
            binding.edInput.text.clear()
        }
    }

    private fun setupTextWatcher(maxLength: Int) {
        binding.edInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentLength = s?.length ?: 0
                binding.tvType.text = "$currentLength/$maxLength"
                remainingCharacter.updateRemainingCharacters(binding.edInput, binding.tvRemaining, maxLength)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
