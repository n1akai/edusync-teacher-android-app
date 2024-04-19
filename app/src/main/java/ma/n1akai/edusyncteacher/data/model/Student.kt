package ma.n1akai.edusyncteacher.data.model

data class Student (
    val student_id: Int,
    val first_name: String,
    val last_name: String,
    val position: Int,
    val has_homework: Int,
    var test_marks: MutableMap<String, Double>?,
    var test_ids: List<Int>?
) {
    fun getFullName(): String {
        return "${last_name.uppercase()} ${first_name.uppercase()}"
    }

    fun getPosition(): String {
        return if (position > 9) {
            "0${position}"
        } else {
            "00${position}"
        }
    }

    fun isHomeworkDone(): Boolean {
        return has_homework == 1
    }
}