package ma.n1akai.edusyncteacher.data.model

data class Teacher(
    val teacher_id: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone_number: String
) {
    fun getFullName(): String {
        return "${last_name.uppercase()} ${first_name.uppercase()}"
    }
}