package ma.n1akai.edusyncteacher.ui.home.attendance

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.databinding.FragmentAttendanceBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog

@AndroidEntryPoint
class AttendanceFragment : BaseFragment<FragmentAttendanceBinding>() {

    private val viewModel: AttendanceViewModel by viewModels()
    private var classId: Int = 0
    private val studentsAdapter = StudentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        classId = requireArguments().getInt("classId")
        viewModel.getClassStudents(classId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.studentsRc.apply {
            adapter = studentsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }

        viewModel.students.observeWithLoadingDialog(viewLifecycleOwner, requireContext()) {
            studentsAdapter.items = it
        }


    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAttendanceBinding.inflate(inflater, container, false)

}