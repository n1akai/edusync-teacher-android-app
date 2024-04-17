package ma.n1akai.edusyncteacher.data.network

import ma.n1akai.edusyncteacher.data.network.response.AuthResponse
import ma.n1akai.edusyncteacher.data.network.response.BaseResponse
import ma.n1akai.edusyncteacher.util.UiState
import retrofit2.HttpException

interface SafeApiCall {

    suspend fun <T> safeApiCall(call: suspend () -> T): UiState<T> {
        return try {
            when (val data = call.invoke()) {
                is AuthResponse -> {
                    if (data.error) {
                        return UiState.Failure(data.message)
                    }
                    return UiState.Success(data)
                }

                is BaseResponse -> {
                    if (data.error) {
                        return UiState.Failure(data.message)
                    }
                    return UiState.Success(data)
                }

                else -> return UiState.Success(data)
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException ->
                    UiState.Failure("${throwable.code()}: ${throwable.response()?.errorBody()}")

                else ->
                    UiState.Failure("Network Error: ${throwable.message}")
            }
        }
    }

}