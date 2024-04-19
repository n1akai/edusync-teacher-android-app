package ma.n1akai.edusyncteacher.ui.home.modules.marks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.data.network.request.TestMarkRequest
import ma.n1akai.edusyncteacher.data.network.response.BaseResponse
import ma.n1akai.edusyncteacher.data.repository.StudentRepository
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunchWithBlock
import javax.inject.Inject

@HiltViewModel
class MarkViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _students = MutableLiveData<UiState<List<Student>>>()
    val students: LiveData<UiState<List<Student>>> get() = _students

    private val _addAndUpdateTestMarks = MutableLiveData<UiState<String>>()
    val addAndUpdateTestMarks: LiveData<UiState<String>> get() = _addAndUpdateTestMarks

    fun getStudentsWithMarks(classId: Int, courseId: Int) {
        viewModelScope.safeLaunchWithBlock({
            _students.value = UiState.Loading
            val marksDiffered = async { teacherRepository.getClassMarks(classId, courseId) }
            val studentsDiffered = async { studentRepository.getClassStudents(classId) }

            val marks = marksDiffered.await()
            val students = studentsDiffered.await()

            if (marks is UiState.Success && students is UiState.Success) {
                marks.data.forEach {
                    students.data.find { student ->
                        student.student_id == it.student_id
                    }?.let {student ->
                        student.test_marks = it.getStudentsMarks()
                        student.test_ids = it.getTestIds()
                    }
                }
                val newStudent = students.data.sortedBy {
                    it.position
                }
                _students.value = UiState.Success(newStudent)
            } else {
                _students.value = UiState.Failure("Something went wrong!")
            }
        }, {

        })
    }

    fun addAndUpdateTestMarks(list: List<TestMarkRequest>, map: MutableMap<String, Double>) {
        viewModelScope.safeLaunchWithBlock({

            var addDf: Deferred<UiState<BaseResponse>>? = null
            var updateDf: Deferred<UiState<BaseResponse>>? = null

            if (list.isNotEmpty()) {
                addDf = async { teacherRepository.addTestMarks(list) }
            }

            if (map.isNotEmpty()) {
                updateDf = async { teacherRepository.updateTestMarks(map) }
            }

            val add = addDf?.await()
            val update = updateDf?.await()


            var msg = ""

            if (add != null) {
                msg += "Add "
            }

            if (update != null) {
                msg += "Update"
            }
            println(msg)
            _addAndUpdateTestMarks.value = UiState.Success(msg)


        }) {
            _addAndUpdateTestMarks.value = UiState.Failure(it)
        }
    }

}