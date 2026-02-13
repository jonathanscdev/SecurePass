package mx.jsc.securepass.domain.repository

import mx.jsc.securepass.domain.model.Credential
import kotlinx.coroutines.flow.Flow

interface CredentialRepository {

    /**
     * Observes all stored credentials. Passwords are NOT decrypted in this flow.
     */
    fun getAllCredentials(): Flow<List<Credential>>

    /**
     * Adds a new credential. The [plainPassword] will be encrypted before storage.
     */
    suspend fun addCredential(serviceName: String, username: String, plainPassword: String)

    /**
     * Decrypts and returns the plaintext password for the given credential ID.
     */
    suspend fun decryptPassword(credentialId: Long): String

    /**
     * Deletes the credential with the given ID.
     */
    suspend fun deleteCredential(credentialId: Long)
}
