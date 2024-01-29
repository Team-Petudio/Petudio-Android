package com.composition.damoa.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.composition.damoa.data.repository.interfaces.ConceptRepository
import com.composition.damoa.data.repository.interfaces.UserRepository
import com.composition.damoa.presentation.screens.home.state.ConceptUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val conceptRepository: ConceptRepository,
) : ViewModel() {
    private val _conceptUiState = MutableStateFlow(ConceptUiState())
    val conceptUiState = _conceptUiState.asStateFlow()
}