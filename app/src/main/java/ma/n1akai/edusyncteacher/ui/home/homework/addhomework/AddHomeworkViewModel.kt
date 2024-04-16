package ma.n1akai.edusyncteacher.ui.home.homework.addhomework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.network.request.HomeworkRequest
import ma.n1akai.edusyncteacher.data.network.response.BaseResponse
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class AddHomeworkViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _homework = MutableLiveData<UiState<BaseResponse>>()
    val homework: LiveData<UiState<BaseResponse>> get() = _homework

    private val _classes = MutableLiveData<UiState<List<Class>>>()
    val classes: LiveData<UiState<List<Class>>> get() = _classes

    private val _modules = MutableLiveData<UiState<List<Module>>>()
    val modules: LiveData<UiState<List<Module>>> get() = _modules

    fun addHomework(homework: HomeworkRequest) {
        viewModelScope.safeLaunch( { teacherRepository.addHomework(homework) }, _homework )
    }

    fun getClasses() {
        viewModelScope.safeLaunch({ teacherRepository.getClasses() }, _classes)
    }

    fun getModules(classId: Int) {
        viewModelScope.safeLaunch({ teacherRepository.getModulesByClass(classId) }, _modules)
    }
}