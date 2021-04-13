package com.cartrack.app.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cartrack.app.database.AppRepository

class RegisterViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(AppRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            Log.e("message",e.message.toString())
        }
        return super.create(modelClass)
    }
}