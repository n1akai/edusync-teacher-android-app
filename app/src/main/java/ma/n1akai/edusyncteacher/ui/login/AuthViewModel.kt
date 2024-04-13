package ma.n1akai.edusyncteacher.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ma.n1akai.edusyncteacher.data.network.response.AuthResponse
import ma.n1akai.edusyncteacher.data.repository.AuthRepository
import ma.n1akai.edusyncteacher.util.UiState
import ma.n1akai.edusyncteacher.util.safeLaunch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _login = MutableLiveData<UiState<AuthResponse>>()
    val login: LiveData<UiState<AuthResponse>> get() = _login

    fun login(email: String, password: String) {
        viewModelScope.safeLaunch({
            authRepository.login(email, password)
        }, _login)
    }

}