package ma.n1akai.edusyncteacher.ui.home.modules

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.databinding.FragmentModulesBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class ModulesFragment : BaseFragment<FragmentModulesBinding>() {

    private val viewModel: ModulesViewModel by viewModels()
    private val moduleAdapter = ModuleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getModules()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.modulesRc.apply {
            adapter = moduleAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        observer()
    }

    private fun observer() {
        viewModel.modules.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            moduleAdapter.items = it
            binding.modulesTv.text = requireContext().getString(R.string.modules_size, it.size)
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentModulesBinding.inflate(inflater, container, false)
}