package ma.n1akai.edusyncteacher.ui.home.homework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.data.model.Teacher
import ma.n1akai.edusyncteacher.data.repository.StudentRepository
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class HomeworkViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _homeworks = MutableLiveData<UiState<List<Homework>>>()
    val homeworks: LiveData<UiState<List<Homework>>> get() = _homeworks

    private val _students = MutableLiveData<UiState<List<Student>>>()
    val students: LiveData<UiState<List<Student>>> get() = _students

    fun getHomeworks() {
        viewModelScope.safeLaunch({ teacherRepository.getHomeworks() }, _homeworks)
    }

    fun getStudents(classId: Int, homeworkId: Int) {
        viewModelScope.safeLaunch({ studentRepository.getStudentsByHomework(classId, homeworkId) }, _students)
    }


}