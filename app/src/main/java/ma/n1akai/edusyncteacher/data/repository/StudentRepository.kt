package ma.n1akai.edusyncteacher.data.repository

import ma.n1akai.edusyncteacher.data.network.SafeApiCall
import ma.n1akai.edusyncteacher.data.network.TeacherApi

class StudentRepository(
    private val api: TeacherApi
) : SafeApiCall {

    suspend fun getStudentsByHomework(classId: Int, homeworkId: Int) =
        safeApiCall { api.getStudentsByHomework(classId, homeworkId) }

    suspend fun getClassStudents(classId: Int) = safeApiCall { api.getClassStudents(classId) }

}