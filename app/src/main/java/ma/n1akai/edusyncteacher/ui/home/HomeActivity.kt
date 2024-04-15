package ma.n1akai.edusyncteacher.ui.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.component.common.LoadingDialog
import ma.n1akai.edusyncteacher.data.model.Teacher
import ma.n1akai.edusyncteacher.databinding.ActivityHomeBinding
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.snackbar

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var teacher: Teacher

    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setUpToolBar() {
        binding.toolbar.setupWithNavController(navController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
        setUpToolBar()
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.getTeacher()
        val loadingDialog = LoadingDialog(this)
        viewModel.teacher.observe(this) {
            when(it) {
                is UiState.Failure -> {
                    loadingDialog.hide()
                    binding.root.snackbar(it.error)
                }
                UiState.Loading -> loadingDialog.show()
                is UiState.Success -> {
                    loadingDialog.hide()
                    teacher = it.data
                    binding.toolbar.apply {
                        title = teacher.getFullName()
                    }
                }
            }
        }
    }
}