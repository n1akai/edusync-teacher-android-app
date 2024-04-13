package ma.n1akai.edusyncteacher.data.network

import ma.n1akai.edusyncteacher.data.network.response.AuthResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/teachers")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : AuthResponse

}