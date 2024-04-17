package ma.n1akai.edusyncteacher.data.network.request

import java.util.Date

data class AbsentRequest(
    val student_id: Int,
    val date: String,
    val class_id: Int,
    val start_time: String,
    val end_time: String
)