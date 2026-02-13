package mx.jsc.securepass.di

import android.content.Context
import androidx.room.Room
import mx.jsc.securepass.crypto.CryptoManager
import mx.jsc.securepass.data.local.CredentialDao
import mx.jsc.securepass.data.local.SecurePassDatabase
import mx.jsc.securepass.data.repository.CredentialRepositoryImpl
import mx.jsc.securepass.domain.repository.CredentialRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SecurePassDatabase {
        return Room.databaseBuilder(
            context,
            SecurePassDatabase::class.java,
            "securepass_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCredentialDao(database: SecurePassDatabase): CredentialDao {
        return database.credentialDao()
    }

    @Provides
    @Singleton
    fun provideCredentialRepository(
        credentialDao: CredentialDao,
        cryptoManager: CryptoManager
    ): CredentialRepository {
        return CredentialRepositoryImpl(credentialDao, cryptoManager)
    }
}
