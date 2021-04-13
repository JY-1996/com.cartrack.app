package com.cartrack.app.ui.register

/**
 * Data validation state of the register form.
 */
data class RegisterFormState(val usernameError: Int? = null,
                          val countryError: Int? = null,
                          val passwordError: Int? = null,
                          val isDataValid: Boolean = false)