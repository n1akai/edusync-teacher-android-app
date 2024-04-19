package ma.n1akai.edusyncteacher.ui.home.modules.marks

import android.os.Bundle
import androidx.fragment.app.viewModels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.databinding.FragmentMarkBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class MarkFragment : BaseFragment<FragmentMarkBinding>() {

    private val viewModel: MarkViewModel by viewModels()
    private val args: MarkFragmentArgs by navArgs()
    private val markAdapter = MarkAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val module = args.module
        viewModel.getStudentsWithMarks(module.class_id, module.course_id)
        markAdapter.numberOfTests = module.num_test!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.markRc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = markAdapter
        }

        observer()
    }

    private fun observer() {
        viewModel.students.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            markAdapter.items = it
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMarkBinding.inflate(inflater, container, false)


}