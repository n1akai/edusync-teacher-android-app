package ma.n1akai.edusyncteacher.data.model

data class Class (
    val class_id: Int,
    val branch_id: Int,
    val class_name: String,
    val class_year: Int,
    val remarks: String,
    val branch_name: String
)