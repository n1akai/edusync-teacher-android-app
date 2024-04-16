package ma.n1akai.edusyncteacher.data.network.request

data class HomeworkRequest(
    val homework: String,
    val class_id: Int,
    val course_id: Int,
    val description: String
)