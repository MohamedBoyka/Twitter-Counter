package com.example.twittercounter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.twittercounter.RemainingCharacter.RemainingCharacter
import com.example.twittercounter.databinding.ActivityMainBinding
import com.example.twittercounter.ui.viewModel.HandlingActionViewModel
import com.example.twittercounter.ui.viewModel.HandlingActionViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val maxLength = 280
    private val remainingCharacter by lazy { RemainingCharacter() }
    private val viewModel: HandlingActionViewModel by viewModels { HandlingActionViewModelFactory(this , lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupCounterCharacters(maxLength)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }


    private fun setupCounterCharacters(maxLength: Int) {
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
