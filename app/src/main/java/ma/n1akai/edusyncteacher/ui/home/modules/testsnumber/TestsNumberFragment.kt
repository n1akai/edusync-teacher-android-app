package ma.n1akai.edusyncteacher.ui.home.modules.testsnumber

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.databinding.FragmentTestsNumberBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.fromHtml
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog
import ma.n1akai.edusyncteacher.util.snackbar

@AndroidEntryPoint
class TestsNumberFragment : BaseFragment<FragmentTestsNumberBinding>() {

    private val viewModel: TestsNumberViewModel by viewModels()
    private val args: TestsNumberFragmentArgs by navArgs()
    private lateinit var module: Module

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            testsNumberTvMsg.text =
                requireContext().getString(R.string.select_tests_message, args.module.course_name)
                    .fromHtml()
            testsNumberSpNum.adapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listOf(2, 3, 4)
                )
            binding.testsNumberBtnSubmit.setOnClickListener {
                module = args.module
                module.num_test = testsNumberSpNum.selectedItem as Int
                viewModel.addTestNum(
                    module.class_id,
                    module.course_id,
                    module.num_test!!
                )
            }
        }

        observer()
    }

    private fun observer() {
        viewModel.testNum.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            findNavController()
                .navigate(
                    TestsNumberFragmentDirections.actionTestsNumberFragmentToMarkFragment(
                        module,
                        module.getFullModuleName()
                    )
                )
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTestsNumberBinding.inflate(inflater, container, false)

}