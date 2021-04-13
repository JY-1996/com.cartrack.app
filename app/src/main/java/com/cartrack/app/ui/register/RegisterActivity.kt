package com.cartrack.app.ui.register

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cartrack.app.R
import com.cartrack.app.database.AppDatabase
import com.cartrack.app.database.AppRepository
import com.cartrack.app.databinding.ActivityRegisterBinding
import com.cartrack.app.ui.login.LoggedInUserView
import com.cartrack.app.ui.login.afterTextChanged


class RegisterActivity : AppCompatActivity() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var repository: AppRepository
    private lateinit var factory: RegisterViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityRegisterBinding = DataBindingUtil.setContentView(this@RegisterActivity,R.layout.activity_register)

        appDatabase = AppDatabase(this)
        repository = AppRepository(appDatabase)
        factory = RegisterViewModelFactory(repository)
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        registerViewModel.registerResult.observe(this@RegisterActivity, Observer {
            val registerResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (registerResult.error != null) {
                showRegisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                updateUiWithUser(registerResult.success)
            }

            setResult(Activity.RESULT_OK)

            finish()
        })
        val array = resources.getStringArray(R.array.country)
        binding.username.afterTextChanged {
            registerViewModel.registerDataChanged(
                binding.username.text.toString(),
                array[binding.spinner.selectedItemPosition],
                binding.password.text.toString()
            )
        }

//        binding.password.apply {
//            afterTextChanged {
//                registerViewModel.registerDataChanged(
//                    binding.username.text.toString(),
//                    binding.password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        registerViewModel.register(
//                            binding.username.text.toString(),
//                            binding.username.text.toString(),
//                            binding.password.text.toString()
//                        )
//                }
//                false
//            }
//
//            binding.login.setOnClickListener {
//                binding.loading.visibility = View.VISIBLE
//                registerViewModel.register(
//                    binding.username.text.toString(),
//                    binding.password.text.toString()
//                )
//            }
//        }
    }

    private fun updateUiWithUser(model: RegisterSuccessView) {
        val register = getString(R.string.register)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            register,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}