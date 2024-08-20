package com.example.twittercounter.ui.viewModel

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HandlingActionViewModelFactory(private val context: Context , private val lifecycleScope: LifecycleCoroutineScope) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HandlingActionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HandlingActionViewModel(context , lifecycleScope) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
