package com.example.twittercounter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.example.twittercounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val remainingCharacter = RemainingCharacter() // Create an instance of RemainingCharacter
    private val copyText = CopyText()
    private val maxLength = 280


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding object
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatcher(maxLength)
        init()
    }

    fun init(){

        binding.let {
            it.btnCopy.setOnClickListener {
                copyText.copy(binding.edInput.text.toString() , this)
            }

            it.btnClear.setOnClickListener {
                binding.edInput.text.clear()
            }
        }
    }

    fun setupTextWatcher(maxLength : Int){
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