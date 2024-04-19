package ma.n1akai.edusyncteacher.data.model

data class Mark(
    val test_id: Int,
    val student_id: Int,
    val test_marks: String
) {
    fun getStudentsMarks(): MutableMap<String, Double> {
        return test_marks.split(",").associate {
            val (cc, note) = it.split("=")
            cc to note.toDouble()
        }.toMutableMap()
    }
}