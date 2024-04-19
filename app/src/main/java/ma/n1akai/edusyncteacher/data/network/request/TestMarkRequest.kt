package ma.n1akai.edusyncteacher.data.network.request

import ma.n1akai.edusyncteacher.data.model.Mark

data class TestMarkRequest (
    val student_id: Int,
    val mark: Double,
    val course_id: Int,
    val test_code: String
)