package ma.n1akai.edusyncteacher.ui.home.homework

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.databinding.FragmentHomeworkBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class HomeworkFragment : BaseFragment<FragmentHomeworkBinding>() {

    private val viewModel: HomeworkViewModel by viewModels()
    private lateinit var spinnerAdapter: SpinnerAdapter
    private val studentsHomeworkAdapter = StudentsHomeworkAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spinnerAdapter = SpinnerAdapter(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHomeworks()
        binding.homeworkSpinner.adapter = spinnerAdapter
        binding.homeworkSpinner.apply {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val homework = selectedItem as Homework
                    viewModel.getStudents(homework.class_id, homework.homework_id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        }

        binding.homeworkRc.apply {
            adapter = studentsHomeworkAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

        homeworkObserver()
        studentsObserver()

        binding.homeworkFabAdd.setOnClickListener {
            findNavController()
                .navigate(HomeworkFragmentDirections.actionHomeworkFragmentToAddHomeworkFragment())
        }
    }

    private fun homeworkObserver() {
        viewModel.homeworks.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            spinnerAdapter.items = it
        }
    }

    private fun studentsObserver() {
        viewModel.students.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            studentsHomeworkAdapter.items = it
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeworkBinding.inflate(inflater, container, false)


}