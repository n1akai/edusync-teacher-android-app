package ma.n1akai.edusyncteacher.data.repository

import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.network.SafeApiCall
import ma.n1akai.edusyncteacher.data.network.TeacherApi
import ma.n1akai.edusyncteacher.data.network.request.AbsentRequest
import ma.n1akai.edusyncteacher.data.network.request.HomeworkRequest
import ma.n1akai.edusyncteacher.data.network.request.TestMarkRequest
import retrofit2.http.Path

class TeacherRepository(
    private val api: TeacherApi
) : SafeApiCall {

    suspend fun getTeacher() = safeApiCall { api.getTeacher() }
    suspend fun getClasses() = safeApiCall { api.getClasses() }
    suspend fun getHomeworks() = safeApiCall { api.getHomeworks() }
    suspend fun addHomework(homework: HomeworkRequest) = safeApiCall { api.addHomework(homework) }
    suspend fun getModulesByClass(classId: Int) = safeApiCall { api.getModulesByClass(classId) }
    suspend fun registerAttendance(list: List<AbsentRequest>) =
        safeApiCall { api.registerAttendance(list) }
    suspend fun getModules() = safeApiCall { api.getModules() }
    suspend fun addTestNumber(classId: Int, courseId: Int, numTest: Int) =
        safeApiCall { api.addTestNumber(classId, courseId, numTest) }
    suspend fun getClassMarks(classId: Int, courseId: Int) =
        safeApiCall { api.getClassMarks(classId, courseId) }
    suspend fun addTestMarks(list: List<TestMarkRequest>) =
        safeApiCall { api.addTestMarks(list) }
    suspend fun updateTestMarks(map: MutableMap<String, Double>) =
        safeApiCall { api.updateTestMarks(map) }
}