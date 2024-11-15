package ma.n1akai.edusyncteacher.ui.home.modules

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class ModulesViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _modules = MutableLiveData<UiState<List<Module>>>()
    val modules: LiveData<UiState<List<Module>>> get() = _modules

    fun getModules() {
        viewModelScope.safeLaunch({ teacherRepository.getModules() }, _modules)
    }


}