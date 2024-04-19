package ma.n1akai.edusyncteacher.data.network

import ma.n1akai.edusyncteacher.data.model.Class
import ma.n1akai.edusyncteacher.data.model.Homework
import ma.n1akai.edusyncteacher.data.model.Mark
import ma.n1akai.edusyncteacher.data.model.Module
import ma.n1akai.edusyncteacher.data.model.Student
import ma.n1akai.edusyncteacher.data.model.Teacher
import ma.n1akai.edusyncteacher.data.network.request.AbsentRequest
import ma.n1akai.edusyncteacher.data.network.request.HomeworkRequest
import ma.n1akai.edusyncteacher.data.network.request.TestMarkRequest
import ma.n1akai.edusyncteacher.data.network.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @GET("/teacher/class/{classId}/course")
    suspend fun getModulesByClass(@Path("classId") classId: Int): List<Module>

    @POST("/teacher/homeworks")
    suspend fun addHomework(@Body homework: HomeworkRequest): BaseResponse

    @GET("/teacher/class/{classId}/students")
    suspend fun getClassStudents(@Path("classId") classId: Int): List<Student>

    @POST("/teacher/absent")
    suspend fun registerAttendance(@Body list: List<AbsentRequest>): BaseResponse

    @GET("/teacher/courses")
    suspend fun getModules(): List<Module>

    @FormUrlEncoded
    @PUT("/teacher/testnum/{classId}/{courseId}")
    suspend fun addTestNumber(
        @Path("classId") classId: Int,
        @Path("courseId") courseId: Int,
        @Field("num_test") numTest: Int
    ): BaseResponse

    @GET("/teacher/tests/course/{courseId}/class/{classId}")
    suspend fun getClassMarks(
        @Path("classId") classId: Int,
        @Path("courseId") courseId: Int,
    ): List<Mark>

    @POST("/teacher/tests")
    suspend fun addTestMarks(@Body list: List<TestMarkRequest>): BaseResponse

    @FormUrlEncoded
    @POST("/teacher/tests/update")
    suspend fun updateTestMarks(
        @FieldMap map: MutableMap<String, Double>
    ): BaseResponse
}