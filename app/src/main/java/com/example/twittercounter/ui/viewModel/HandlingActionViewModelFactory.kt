package com.example.twittercounter.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HandlingActionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HandlingActionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HandlingActionViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
