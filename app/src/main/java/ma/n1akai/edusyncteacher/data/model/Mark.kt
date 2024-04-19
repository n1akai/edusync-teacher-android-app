package ma.n1akai.edusyncteacher.data.model

data class Mark(
    val test_id: Int,
    val student_id: Int,
    val test_marks: String,
    val mark: Double,
    val course_id: Int,
    val test_code: String,
    var test_ids: String
) {
    fun getStudentsMarks(): MutableMap<String, Double> {
        val arr = test_marks.split(",")
        val map = arr.associate {
            it.split(":").let{ (cc, note) -> cc to note.toDouble() }
        }
        return map.toMutableMap()
    }

    fun getTestIds(): List<Int> {
        return test_ids.split(",") as List<Int>
    }
}