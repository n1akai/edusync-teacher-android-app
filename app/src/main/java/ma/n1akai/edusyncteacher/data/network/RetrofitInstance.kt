package ma.n1akai.edusyncteacher.data.network

import ma.n1akai.edusyncteacher.data.network.interceptor.AuthInterceptor
import ma.n1akai.edusyncteacher.data.network.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance(
    private val networkConnectionInterceptor: NetworkConnectionInterceptor,
    private val authInterceptor: AuthInterceptor
) {

    companion object {
        const val BASE_URL = "http://10.0.2.2/"
    }

    fun <T> buildApi(
        api: Class<T>
    ) : T {
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(networkConnectionInterceptor)
            .also {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                it.addInterceptor(logging)
            }.addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(api)
    }

}