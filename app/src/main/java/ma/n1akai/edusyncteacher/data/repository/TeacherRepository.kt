package ma.n1akai.edusyncteacher.data.repository

import ma.n1akai.edusyncteacher.data.network.SafeApiCall
import ma.n1akai.edusyncteacher.data.network.TeacherApi
import retrofit2.http.Path

class TeacherRepository(
    private val api: TeacherApi
) : SafeApiCall {

    suspend fun getTeacher() = safeApiCall { api.getTeacher() }
    suspend fun getClasses() = safeApiCall { api.getClasses() }
    suspend fun getHomeworks() = safeApiCall { api.getHomeworks() }
}