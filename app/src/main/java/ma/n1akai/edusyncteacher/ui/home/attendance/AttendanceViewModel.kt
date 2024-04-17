package ma.n1akai.edusyncteacher.ui.home.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.data.network.request.AbsentRequest
import ma.n1akai.edusyncteacher.data.network.response.BaseResponse
import ma.n1akai.edusyncteacher.data.repository.StudentRepository
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _students = MutableLiveData<UiState<List<Student>>>()
    val students: LiveData<UiState<List<Student>>> get() = _students

    private val _absent = MutableLiveData<UiState<BaseResponse>>()
    val absent: LiveData<UiState<BaseResponse>> get() = _absent

    fun getClassStudents(classId: Int) {
        viewModelScope.safeLaunch({ studentRepository.getClassStudents(classId) }, _students)
    }

    fun registerAttendance(list: List<AbsentRequest>) {
        viewModelScope.safeLaunch( { teacherRepository.registerAttendance(list) }, _absent)
    }

}