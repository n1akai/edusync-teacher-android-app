package ma.n1akai.edusyncteacher.ui.home.dashboard

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.component.common.LoadingDialog
import ma.n1akai.edusyncteacher.databinding.FragmentDashboardBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog
import ma.n1akai.edusyncteacher.util.snackbar

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    private val markAttendanceAdapter = MarkAttendanceAdapter()
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getClasses()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observer()
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