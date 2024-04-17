package ma.n1akai.edusyncteacher.data.model

data class Module(
    val course_id: Int,
    val course_name: String,
    val course_code: String,
    val MHT: Int,
    val Coef: Int,
    val branch_id: Int,
    val branch_name: String,
    val class_name: String,
    val class_id: Int

)