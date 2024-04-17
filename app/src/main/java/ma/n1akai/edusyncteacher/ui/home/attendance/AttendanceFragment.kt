package ma.n1akai.edusyncteacher.ui.home.attendance

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import ma.n1akai.edusyncteacher.R
import ma.n1akai.edusyncteacher.data.network.request.AbsentRequest
import ma.n1akai.edusyncteacher.databinding.FragmentAttendanceBinding
import ma.n1akai.edusyncteacher.ui.BaseFragment
import ma.n1akai.edusyncteacher.util.observeWithLoadingDialog
import ma.n1akai.edusyncteacher.util.snackbar
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class AttendanceFragment : BaseFragment<FragmentAttendanceBinding>() {

    private val viewModel: AttendanceViewModel by viewModels()
    private val studentsAdapter = StudentsAdapter()
    private val args: AttendanceFragmentArgs by navArgs()
    private val today = SimpleDateFormat("dd/MM/YYYY").format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getClassStudents(args.classId)

        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar.apply {
            subtitle = "${args.className} - $today"
        }
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


        submit()

    }

    private fun submit() {

        binding.attendanceBtnSubmit.setOnClickListener {
            val startTime = binding.attendanceEtStartTime.text.toString()
            val endTime = binding.attendanceEtEndTime.text.toString()
            if (startTime.isEmpty() || endTime.isEmpty()) {
                binding.root.snackbar("Times are required")
                return@setOnClickListener
            }
            val classId = args.classId

            val list = mutableListOf<AbsentRequest>()

            studentsAdapter.absentStudents.forEach {
                list.add(AbsentRequest(it.student_id, SimpleDateFormat("YYYY-MM-dd").format(Date()), classId, startTime, endTime))
            }

            viewModel.registerAttendance(list)
        }

        viewModel.absent.observeWithLoadingDialog(viewLifecycleOwner, requireActivity()) {
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
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAttendanceBinding.inflate(inflater, container, false)

}