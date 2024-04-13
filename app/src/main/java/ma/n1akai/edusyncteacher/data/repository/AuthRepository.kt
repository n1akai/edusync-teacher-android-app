package ma.n1akai.edusyncteacher.data.repository

import ma.n1akai.edusyncteacher.data.network.AuthApi
import ma.n1akai.edusyncteacher.data.network.SafeApiCall

class AuthRepository(
    private val api: AuthApi
) : SafeApiCall {

    suspend fun login(email: String, password: String) = safeApiCall { api.login(email, password) }

}