package mx.jsc.securepass.domain.model

/**
 * Domain model representing a stored credential.
 * The password field holds the plaintext password only when decrypted;
 * otherwise it is null (i.e., passwords are never cached in plaintext at the list level).
 */
data class Credential(
    val id: Long = 0,
    val serviceName: String,
    val username: String,
    val encryptedPassword: ByteArray,
    val createdAt: Long = System.currentTimeMillis(),
    val decryptedPassword: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Credential) return false
        return id == other.id &&
                serviceName == other.serviceName &&
                username == other.username &&
                encryptedPassword.contentEquals(other.encryptedPassword) &&
                createdAt == other.createdAt &&
                decryptedPassword == other.decryptedPassword
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + serviceName.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + encryptedPassword.contentHashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (decryptedPassword?.hashCode() ?: 0)
        return result
    }
}
