package ma.n1akai.edusyncteacher.ui.home.modules.testsnumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.network.response.BaseResponse
import ma.n1akai.edusyncteacher.data.repository.TeacherRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class TestsNumberViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _testNum = MutableLiveData<UiState<BaseResponse>>()
    val testNum: LiveData<UiState<BaseResponse>> get() = _testNum

    fun addTestNum(classId: Int, courseId: Int, numTest: Int) {
        viewModelScope.safeLaunch({ teacherRepository.addTestNumber(classId, courseId, numTest) }, _testNum)
    }

}