package com.cartrack.app.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(
    private val repository: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(LoginRepository::class.java)
            return constructor.newInstance(repository)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.create(modelClass)
    }
}