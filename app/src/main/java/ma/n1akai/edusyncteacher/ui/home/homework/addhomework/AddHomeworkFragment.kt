package ma.n1akai.edusyncteacher.ui.home.homework.addhomework

import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.network.request.HomeworkRequest
import ma.n1akai.edusyncteacher.databinding.FragmentAddHomeworkBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.ui.home.homework.SpinnerAdapter
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class AddHomeworkFragment : BaseFragment<FragmentAddHomeworkBinding>() {

    private val viewModel: AddHomeworkViewModel by viewModels()
    private lateinit var homework: HomeworkRequest
    private lateinit var selectedClass: Class
    private lateinit var selectedModule: Module

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        classes()
        modules()
        submit()
    }

    private fun classes() {
        viewModel.getClasses()
        val classesAdapter = SpinnerAdapter(requireContext())
        viewModel.classes.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            classesAdapter.items = it
        }
        binding.addSpClasses.apply {
            adapter = classesAdapter
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedClass = selectedItem as Class
                    viewModel.getModules(selectedClass.class_id)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    private fun modules() {
        val coursesAdapter = SpinnerAdapter(requireContext())
        viewModel.modules.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            println(it[0])
            coursesAdapter.items = it
        }
        binding.addSpCourses.apply {
            adapter = coursesAdapter
            onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedModule = selectedItem as Module
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
        }
    }

    private fun submit() {
        viewModel.homework.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Success!")
                .setMessage(it.message)
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog, which ->
                    findNavController().navigateUp()
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }

        binding.addBtnSubmit.setOnClickListener {
            binding.apply {
                val homeworkName = addEtHomeworkName.text.toString()
                val classId = selectedClass.class_id
                val moduleId = selectedModule.course_id
                val description = addEtDescription.text.toString()
                homework = HomeworkRequest(homeworkName, classId, moduleId, description)
            }
            viewModel.addHomework(homework)
        }
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddHomeworkBinding.inflate(inflater, container, false)

}