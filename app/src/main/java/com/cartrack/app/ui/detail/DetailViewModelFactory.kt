package com.cartrack.app.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cartrack.app.data.detail.DetailRepository

class DetailViewModelFactory(private val repository: DetailRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return DetailViewModel(repository) as T
        }
    }