package ma.n1akai.edusyncteacher.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ma.n1akai.edusyncteacher.component.common.LoadingDialog
import java.net.SocketTimeoutException

fun String.isValidEmail() : Boolean {
    val emailRegex = Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$""")
    return emailRegex.matches(this)
}

fun CoroutineScope.safeLaunchWithBlock(
    block: suspend CoroutineScope.() -> Unit,
    onError: (String) -> Unit
) {
    this.launch {
        try {
            block()
        } catch (e: ApiException) {
            onError(e.message!!)
        } catch (e: NoInternetException) {
            onError(e.message!!)
        } catch (e: SocketTimeoutException) {
            onError("Connection to server failed or timeout: ${e.message}")
        } catch (e: Exception) {
            onError("Unknown Error has occurred: ${e.message}")
        }
    }
}

fun<T> CoroutineScope.safeLaunch(block: suspend () -> UiState<T>, liveData: MutableLiveData<UiState<T>>) {
    launch {
        try {
            liveData.value = UiState.Loading
            liveData.value = block.invoke()
        } catch (e: ApiException) {
            liveData.value = UiState.Failure(e.message!!)
        } catch (e: NoInternetException) {
            liveData.value = UiState.Failure(e.message!!)
        } catch (e: SocketTimeoutException) {
            liveData.value = UiState.Failure("Connection to server failed or timeout: ${e.message}")
        } catch (e: Exception) {
            liveData.value = UiState.Failure("Unknown Error has occurred: ${e.message}")
        }
    }
}

inline fun <T> LiveData<UiState<T>>.observeWithLoadingDialog(
    owner: LifecycleOwner,
    context: Context,
    crossinline onSuccess: (T) -> Unit
) {
    val loadingDialog = LoadingDialog(context)
    observe(owner) { state ->
        when (state) {
            is UiState.Failure -> {
                loadingDialog.hide()

            }
            UiState.Loading -> loadingDialog.show()
            is UiState.Success -> {
                loadingDialog.hide()
                onSuccess(state.data)
            }
        }
    }
}