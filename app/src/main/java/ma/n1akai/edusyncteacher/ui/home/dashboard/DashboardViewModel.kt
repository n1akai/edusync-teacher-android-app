package ma.n1akai.edusyncteacher.ui.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _classes = MutableLiveData<UiState<List<Class>>>()
    val classes: LiveData<UiState<List<Class>>> get() = _classes

    fun getClasses() {
        viewModelScope.safeLaunch({ teacherRepository.getClasses() }, _classes)
    }

}