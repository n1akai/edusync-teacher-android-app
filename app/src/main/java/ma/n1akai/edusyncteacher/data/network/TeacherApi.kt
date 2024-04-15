package ma.n1akai.edusyncteacher.data.network

import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Teacher
import retrofit2.http.GET

interface TeacherApi {

    @GET("/teacher/show")
    suspend fun getTeacher(): Teacher

    @GET("/teacher/classes")
    suspend fun getClasses(): List<Class>

}