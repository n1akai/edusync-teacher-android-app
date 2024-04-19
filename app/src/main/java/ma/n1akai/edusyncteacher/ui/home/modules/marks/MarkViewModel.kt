package ma.n1akai.edusyncteacher.ui.home.modules.marks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import ma.n1akai.edusyncteacher.data.model.Student
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

    fun getStudentsWithMarks(classId: Int, courseId: Int) {
        viewModelScope.safeLaunchWithBlock({
            val marksDiffered = async { teacherRepository.getClassMarks(classId, courseId) }
            val studentsDiffered = async { studentRepository.getClassStudents(classId) }

            val marks = marksDiffered.await()
            val students = studentsDiffered.await()

            if (marks is UiState.Success && students is UiState.Success) {
                marks.data.forEach {
                    students.data.find { student ->
                        student.student_id == it.student_id
                    }?.test_marks = it.getStudentsMarks()
                }
                _students.value = students
            } else if (marks is UiState.Failure) {
                _students.value = marks
            }
        }, {

        })
    }

}