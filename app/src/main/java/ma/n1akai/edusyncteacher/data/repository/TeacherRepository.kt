package ma.n1akai.edusyncteacher.data.repository

import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.network.SafeApiCall
import ma.n1akai.edusyncteacher.data.network.TeacherApi
import ma.n1akai.edusyncteacher.data.network.request.HomeworkRequest
import retrofit2.http.Path

class TeacherRepository(
    private val api: TeacherApi
) : SafeApiCall {

    suspend fun getTeacher() = safeApiCall { api.getTeacher() }
    suspend fun getClasses() = safeApiCall { api.getClasses() }
    suspend fun getHomeworks() = safeApiCall { api.getHomeworks() }
    suspend fun addHomework(homework: HomeworkRequest) = safeApiCall { api.addHomework(homework) }
    suspend fun getModulesByClass(classId: Int) = safeApiCall { api.getModulesByClass(classId) }
}