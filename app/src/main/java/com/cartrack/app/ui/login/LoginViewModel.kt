package com.cartrack.app.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cartrack.app.R
import com.cartrack.app.database.LoginRepository
import com.cartrack.app.database.Entity

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    suspend fun getAll() = repository.getAll()

    suspend fun insert(entity: Entity) = repository.insert(entity)

    suspend fun checkUser(username:String, password:String, country:String): Entity? = repository.checkUser(username, password, country)

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun loginDataChanged(username: String, password: String, country:String) {
        Log.e("name",username)
        Log.e("name",password)
        Log.e("name",country)

        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else if (country == "Choose Country") {
            _loginForm.value = LoginFormState(countryError = R.string.choose_country)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.length > 4
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\\$%\\^&\\*]).{8,}\$")
        return regex.matches(password)
    }
}