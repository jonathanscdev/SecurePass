package mx.jsc.securepass.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mx.jsc.securepass.domain.model.Credential
import mx.jsc.securepass.domain.repository.CredentialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val credentials: List<Credential> = emptyList(),
    val revealedPasswords: Map<Long, String> = emptyMap(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val showAddDialog: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CredentialRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        observeCredentials()
    }

    private fun observeCredentials() {
        viewModelScope.launch {
            repository.getAllCredentials()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = emptyList()
                )
                .collect { credentials ->
                    _uiState.value = _uiState.value.copy(
                        credentials = credentials,
                        isLoading = false
                    )
                }
        }
    }

    fun addCredential(serviceName: String, username: String, password: String) {
        if (serviceName.isBlank() || username.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "All fields are required."
            )
            return
        }

        viewModelScope.launch {
            try {
                repository.addCredential(serviceName.trim(), username.trim(), password)
                _uiState.value = _uiState.value.copy(
                    showAddDialog = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to save credential: ${e.localizedMessage}"
                )
            }
        }
    }

    fun togglePasswordVisibility(credentialId: Long) {
        viewModelScope.launch {
            val currentRevealed = _uiState.value.revealedPasswords
            if (currentRevealed.containsKey(credentialId)) {
                // Hide the password
                _uiState.value = _uiState.value.copy(
                    revealedPasswords = currentRevealed - credentialId
                )
            } else {
                // Decrypt and reveal
                try {
                    val plainPassword = repository.decryptPassword(credentialId)
                    _uiState.value = _uiState.value.copy(
                        revealedPasswords = currentRevealed + (credentialId to plainPassword)
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Decryption failed: ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    fun deleteCredential(credentialId: Long) {
        viewModelScope.launch {
            try {
                repository.deleteCredential(credentialId)
                // Also remove from revealed passwords if present
                val currentRevealed = _uiState.value.revealedPasswords
                if (currentRevealed.containsKey(credentialId)) {
                    _uiState.value = _uiState.value.copy(
                        revealedPasswords = currentRevealed - credentialId
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to delete: ${e.localizedMessage}"
                )
            }
        }
    }

    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true, errorMessage = null)
    }

    fun dismissAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false, errorMessage = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}
