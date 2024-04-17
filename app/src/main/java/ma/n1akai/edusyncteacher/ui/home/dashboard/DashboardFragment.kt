package ma.n1akai.edusyncteacher.ui.home.dashboard

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.component.common.LoadingDialog
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.databinding.FragmentDashboardBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.ui.login.AuthActivity
import ma.n1akai.edusyncteacher.util.TokenManager
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog
import ma.n1akai.edusyncteacher.util.snackbar
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val markAttendanceAdapter = MarkAttendanceAdapter()
    private val viewModel: DashboardViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getClasses()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToHomeworkListener()
        setTodayDate()
        setUpRecyclerView()
        observer()
        logout()
        registerAttendance()
        goToProfileListener()
        gotToModulesListener()
    }

    private fun logout() {
        binding.dashboardLogout.setOnClickListener {
            tokenManager.clearToken()
            startActivity(Intent(requireActivity(), AuthActivity::class.java).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            })
        }
    }

    private fun goToHomeworkListener() {
        binding.dashboardHomework.setOnClickListener {
            navigateTo(DashboardFragmentDirections.actionDashboardFragmentToHomeworkFragment())
        }
    }

    private fun goToProfileListener() {
        val profileImage = requireActivity().findViewById<CircleImageView>(R.id.home_iv_profile)
        profileImage.setOnClickListener {
            navigateTo(DashboardFragmentDirections.actionDashboardFragmentToProfileFragment())
        }
        binding.dashboardProfile.setOnClickListener {
            navigateTo(DashboardFragmentDirections.actionDashboardFragmentToProfileFragment())
        }
    }

    private fun gotToModulesListener() {
        binding.dashboardModules.setOnClickListener {
            navigateTo(DashboardFragmentDirections.actionDashboardFragmentToModulesFragment())
        }
    }

    private fun navigateTo(directions: NavDirections) {
        findNavController().navigate(directions)
    }

    private fun registerAttendance() {
        markAttendanceAdapter.listener = object : MarkAttendanceAdapter.OnClassClickListener {
            override fun onClassClick(theClass: Class, view: View) {
                findNavController()
                    .navigate(
                        DashboardFragmentDirections.actionDashboardFragmentToAttendanceFragment(
                            theClass.class_id, theClass.class_name
                        )
                    )
            }
        }
    }

    private fun setTodayDate() {
        val fromater = SimpleDateFormat("dd/MM/YYYY")
        binding.dashboardTvTodayDate.text = fromater.format(Date())
    }

    private fun setUpRecyclerView() {
        binding.homeClasses.apply {
            adapter = markAttendanceAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observer() {
        viewModel.classes.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            markAttendanceAdapter.items = it
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDashboardBinding.inflate(inflater, container, false)
}