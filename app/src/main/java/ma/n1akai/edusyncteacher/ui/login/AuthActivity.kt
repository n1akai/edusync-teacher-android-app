package ma.n1akai.edusyncteacher.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.component.common.LoadingDialog
import ma.n1akai.edusyncteacher.databinding.ActivityAuthBinding
import ma.n1akai.edusyncteacher.ui.home.HomeActivity
import ma.n1akai.edusyncteacher.util.TokenManager
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.isValidEmail
import ma.n1akai.edusyncteacher.util.snackbar
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var viewModel: AuthViewModel
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.authBtnLogin.setOnClickListener {
            login()
        }
        observer()
    }

    private fun validate() : Boolean {
        binding.apply {
            email = authEtEmail.text.toString()
            password = authEtPassword.text.toString()
        }
        return email.isValidEmail()
    }

    private fun login() {
        if (validate()) {
            viewModel.login(email, password)
        }
    }

    private fun observer() {
        val loadingDialog = LoadingDialog(this)
        viewModel.login.observe(this) {
            when(it) {
                is UiState.Failure -> {
                    loadingDialog.hide()
                    binding.root.snackbar(it.error)
                }
                UiState.Loading -> loadingDialog.show()
                is UiState.Success -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    tokenManager.saveToken(it.data.token)
                    finish()
                }
            }
        }
    }

}