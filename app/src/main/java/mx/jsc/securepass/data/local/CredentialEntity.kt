package mx.jsc.securepass.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class CredentialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "service_name")
    val serviceName: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "encrypted_password", typeAffinity = ColumnInfo.BLOB)
    val encryptedPassword: ByteArray,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CredentialEntity) return false
        return id == other.id &&
                serviceName == other.serviceName &&
                username == other.username &&
                encryptedPassword.contentEquals(other.encryptedPassword) &&
                createdAt == other.createdAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + serviceName.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + encryptedPassword.contentHashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}
