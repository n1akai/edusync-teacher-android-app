package ma.n1akai.edusyncteacher.data.network

import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.data.model.Teacher
import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherApi {

    @GET("/teacher/show")
    suspend fun getTeacher(): Teacher

    @GET("/teacher/classes")
    suspend fun getClasses(): List<Class>

    @GET("/teacher/homeworks")
    suspend fun getHomeworks(): List<Homework>

    @GET("/teacher/class/{classId}/homeworks/{homeworkId}")
    suspend fun getStudentsByHomework(
        @Path("classId") classId: Int,
        @Path("homeworkId") homeworkId: Int
    ): List<Student>

}