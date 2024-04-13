package ma.n1akai.edusyncteacher.data.network.response

data class AuthResponse (
    val error: Boolean,
    val message: String,
    val token: String
)