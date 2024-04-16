package ma.n1akai.edusyncteacher.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.component.common.LoadingDialog
import ma.n1akai.edusyncteacher.data.model.Teacher
import ma.n1akai.edusyncteacher.databinding.ActivityHomeBinding
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog
import ma.n1akai.edusyncteacher.util.snackbar

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var teacher: Teacher

    private fun setUpNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
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

        viewModel.teacher.observeWithLoadingDialog(this, this) {
            teacher = it
            setUpToolbarTitleSubTitle()
        }

        destinationUi()
    }

    private fun destinationUi() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val id = destination.id
            when (id) {
                R.id.dashboardFragment -> {
                    setUpToolbarTitleSubTitle()
                }

                else -> {
                    binding.toolbar.apply {
                        setSubtitle(null)
                    }
                    binding.homeIvProfile.isVisible = false
                }
            }
        }


    }

    private fun setUpToolbarTitleSubTitle() {
        if (this@HomeActivity::teacher.isInitialized) {
            binding.toolbar.apply {
                val fullName = teacher.getFullName()
                setTitle(fullName)
                setSubtitle(teacher.cne.uppercase())
            }
            binding.homeIvProfile.isVisible = true
        }
    }

}