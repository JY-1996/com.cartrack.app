package com.cartrack.app.ui.login

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val countryError: Int? = null,
    val isDataValid: Boolean = false
)