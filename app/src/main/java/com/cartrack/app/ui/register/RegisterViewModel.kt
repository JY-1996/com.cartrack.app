package com.cartrack.app.ui.register

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrack.app.R
import com.cartrack.app.data.Result
import com.cartrack.app.data.register.RegisterRepository
import com.cartrack.app.database.AppDatabase
import com.cartrack.app.database.AppRepository
import com.cartrack.app.ui.login.LoggedInUserView
import com.cartrack.app.ui.login.LoginFormState
import com.cartrack.app.ui.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val appRepository: AppRepository) : ViewModel() {
    var username: String = ""
    var password: String = ""
    var confirm_password: String = ""
    var country: String = ""

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult


    suspend fun register(username: String, country:String, password: String) {
//         can be launched in a separate asynchronous job
        val result = appRepository.insert(username, country, password)

//        if (result.value!!) {
            _registerResult.value = RegisterResult(success = RegisterSuccessView(displayName = result.value!!.username))
//        } else {
//            _registerResult.value = RegisterResult(error = R.string.login_failed)
//        }
    }

    fun registerDataChanged(username: String, country:String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isCountryChoose(country)) {
            _registerForm.value = RegisterFormState(countryError = R.string.choose_country)
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isCountryChoose(country: String): Boolean {
        return country == "Choose Country"
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}