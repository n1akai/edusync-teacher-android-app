package ma.n1akai.edusyncteacher.ui.home.modules.marks

import android.os.Bundle
import androidx.fragment.app.viewModels

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.data.network.request.TestMarkRequest
import ma.n1akai.edusyncteacher.databinding.FragmentMarkBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog
import ma.n1akai.edusyncteacher.util.snackbar

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

        binding.markBtn.setOnClickListener {
            val addList = mutableListOf<TestMarkRequest>()
            val updateList = mutableMapOf<Int, Double>()
            markAdapter.items.forEach {
                if (it.test_marks != null) {
                    if (it.test_ids != null) {
                        var i = 0
                        it.test_marks?.forEach { (k, v) ->
                            if (i in it.test_ids!!.indices) {
                                updateList[it.test_ids!![i]] = v
                            } else {
                                addList.add(TestMarkRequest(it.student_id, v, args.module.course_id, k))
                            }
                            i++
                        }
                    } else {
                        it.test_marks?.forEach { (k, v) ->
                            addList.add(TestMarkRequest(it.student_id, v, args.module.course_id, k))
                        }
                    }
                }

            }

            viewModel.addAndUpdateTestMarks(addList, updateList as MutableMap<String, Double>)
        }

        observer()
        addAndUpdateTestMarksObserver()
    }

    private fun observer() {
        viewModel.students.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            markAdapter.items = it
        }
    }

    private fun addAndUpdateTestMarksObserver() {
        viewModel.addAndUpdateTestMarks.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            binding.root.snackbar(it)
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMarkBinding.inflate(inflater, container, false)


}