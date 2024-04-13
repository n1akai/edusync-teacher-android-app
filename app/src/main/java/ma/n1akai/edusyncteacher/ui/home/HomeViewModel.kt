package ma.n1akai.edusyncteacher.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Teacher
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _teacher = MutableLiveData<UiState<Teacher>>()
    val teacher: LiveData<UiState<Teacher>> get() = _teacher

    fun getTeacher() {
        viewModelScope.safeLaunch({ teacherRepository.getTeacher() }, _teacher)
    }

}