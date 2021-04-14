package com.cartrack.app.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cartrack.app.R
import com.cartrack.app.database.LoginDatabase
import com.cartrack.app.database.LoginRepository
import com.cartrack.app.database.LoginViewModelFactory
import com.cartrack.app.ui.detail.ItemListActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var loading: ProgressBar
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var database: LoginDatabase
    private lateinit var repository: LoginRepository
    private lateinit var factory: LoginViewModelFactory
    private lateinit var arrays: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val country = findViewById<Spinner>(R.id.country)

        val login = findViewById<Button>(R.id.login)
        loading = findViewById(R.id.loading)

        database = LoginDatabase(this)
        repository = LoginRepository(database.getLoginDao())
        factory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        arrays = resources.getStringArray(R.array.country).toList()
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        username.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    arrays[country.selectedItemPosition]
                )
            }
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    arrays[country.selectedItemPosition]
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login.performClick()
                }
                false
            }
        }
        country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString(),
                    arrays[country.selectedItemPosition]
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                loginViewModel.checkUser(username.text.toString(), password.text.toString(), arrays[country.selectedItemPosition]).also {
                    loading.visibility = View.INVISIBLE
                    if (it != null) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Hi, " + it.username,
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginActivity, ItemListActivity::class.java)
                        startActivity(intent)
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            resources.getString(R.string.login_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                        username.setText("")
                        password.setText("")
                        country.setSelection(0)
                    }
                }
            }

        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}