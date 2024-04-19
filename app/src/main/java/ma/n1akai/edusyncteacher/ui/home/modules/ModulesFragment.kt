package ma.n1akai.edusyncteacher.ui.home.modules

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.databinding.FragmentModulesBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class ModulesFragment : BaseFragment<FragmentModulesBinding>() {

    private val viewModel: ModulesViewModel by viewModels()
    private val moduleAdapter = ModuleAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getModules()
        binding.modulesRc.apply {
            adapter = moduleAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        handleOnModuleClick()
        observer()
    }

    private fun observer() {
        viewModel.modules.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            moduleAdapter.items = it
            binding.modulesTv.text = requireContext().getString(R.string.modules_size, it.size)
        }
    }

    private fun handleOnModuleClick() {
        moduleAdapter.listener = object : ModuleAdapter.OnModuleClickListener {
            override fun onModuleClick(module: Module, view: View) {
                if (module.num_test == null) {
                    findNavController()
                        .navigate(
                            ModulesFragmentDirections
                                .actionModulesFragmentToTestsNumberFragment(module)
                        )
                } else {
                    findNavController()
                        .navigate(
                            ModulesFragmentDirections
                                .actionModulesFragmentToMarkFragment(module)
                        )
                }
            }
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentModulesBinding.inflate(inflater, container, false)
}