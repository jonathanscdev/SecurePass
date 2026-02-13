package mx.jsc.securepass.data.repository

import mx.jsc.securepass.crypto.CryptoManager
import mx.jsc.securepass.data.local.CredentialDao
import mx.jsc.securepass.data.local.CredentialEntity
import mx.jsc.securepass.domain.model.Credential
import mx.jsc.securepass.domain.repository.CredentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CredentialRepositoryImpl @Inject constructor(
    private val credentialDao: CredentialDao,
    private val cryptoManager: CryptoManager
) : CredentialRepository {

    override fun getAllCredentials(): Flow<List<Credential>> {
        return credentialDao.getAllCredentials().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addCredential(
        serviceName: String,
        username: String,
        plainPassword: String
    ) {
        val encryptedPassword = cryptoManager.encrypt(plainPassword.toByteArray(Charsets.UTF_8))
        val entity = CredentialEntity(
            serviceName = serviceName,
            username = username,
            encryptedPassword = encryptedPassword
        )
        credentialDao.insertCredential(entity)
    }

    override suspend fun decryptPassword(credentialId: Long): String {
        val entity = credentialDao.getCredentialById(credentialId)
            ?: throw IllegalArgumentException("Credential with id $credentialId not found.")

        val decryptedBytes = cryptoManager.decrypt(entity.encryptedPassword)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    override suspend fun deleteCredential(credentialId: Long) {
        credentialDao.deleteCredentialById(credentialId)
    }

    private fun CredentialEntity.toDomainModel(): Credential {
        return Credential(
            id = id,
            serviceName = serviceName,
            username = username,
            encryptedPassword = encryptedPassword,
            createdAt = createdAt,
            decryptedPassword = null
        )
    }
}
