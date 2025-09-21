package com.example.gitscope.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gitscope.data.di.modules.MainDispatcher
import com.example.gitscope.domain.usecase.CalculateTotalForkUseCase
import com.example.gitscope.domain.usecase.GetUserRepositoryUseCase
import com.example.gitscope.domain.usecase.GetUserUseCase
import com.example.gitscope.domain.usecase.GetUserWithRepositoriesUseCase
import com.example.gitscope.presentation.uistate.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.gitscope.data.remote.Result
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getUserRepositoryUseCase: GetUserRepositoryUseCase,
    private val getUserWithRepositoriesUseCase: GetUserWithRepositoriesUseCase,
    private val calculateTotalForkUseCase: CalculateTotalForkUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun updateSearchQuery(query: String) {
        // 'update' is a  'Atomic' operation and prevent race condition
        _uiState.update {
            it.copy(
                searchQuery = query
            )
        }
    }

    fun searchUser(userId: String = uiState.value.searchQuery) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.update { it ->
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                when (val result = getUserWithRepositoriesUseCase(userId)) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                user = result.data.user,
                                repositories = result.data.repositories,
                                totalForks = result.data.totalForks,
                                error = null
                            )

                        }
                    }

                    is Result.Error -> {
                        clearUser()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = null
                            )
                        }

                    }
                }
            } catch (e: CancellationException) {
                return@launch
            } catch (e: UnknownHostException) {
                clearUser()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "No internet connection"
                    )
                }
            } catch (e: Exception) {
                clearUser()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Something went wrong"
                    )
                }

            }

        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }

    fun clearSearchQuery() {
        _uiState.update {
            it.copy(
                searchQuery = ""
            )
        }
    }

    fun clearUser() {
        _uiState.update {
            it.copy(
                user = null,
                repositories = emptyList(),
                totalForks = 0
            )
        }
    }

    fun clearUiState() {
        _uiState.update {
            it.copy(
                isLoading = false,
                user = null,
                repositories = emptyList(),
                error = null,
                totalForks = 0
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}