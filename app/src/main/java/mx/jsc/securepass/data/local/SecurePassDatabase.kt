package mx.jsc.securepass.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CredentialEntity::class],
    version = 1,
    exportSchema = true
)
abstract class SecurePassDatabase : RoomDatabase() {
    abstract fun credentialDao(): CredentialDao
}
