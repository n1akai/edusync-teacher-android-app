package ma.n1akai.edusyncteacher.data.network.interceptor

import android.content.Context
import android.content.Intent
import ma.n1akai.edusyncteacher.ui.login.AuthActivity
import ma.n1akai.edusyncteacher.util.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context,
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenManager.getToken()
        // If there's token, then send it with request header
        val request = if (token != null) {
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }
        val response = chain.proceed(request)

        // If not authorized, clear token and go to auth activity
        if (response.code == 401) {
            tokenManager.clearToken()

            context.startActivity(Intent(context, AuthActivity::class.java).also { intent ->
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            })
        }

        return response
    }
}