package com.cartrack.app.ui.register

import com.cartrack.app.ui.login.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class RegisterResult(
    val success: RegisterSuccessView? = null,
    val error: Int? = null
)