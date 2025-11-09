package com.fazq.rimayalert.features.home.ui.states

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.fazq.rimayalert.core.states.BaseUiState
import com.fazq.rimayalert.core.ui.extensions.getDisplayName
import com.fazq.rimayalert.features.auth.domain.model.UserModel
import com.fazq.rimayalert.features.home.domain.model.CommunityValidationResponseModel
import com.fazq.rimayalert.features.home.domain.model.IncidentModel
import com.fazq.rimayalert.features.home.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class HomeScreenState(
    private val viewModel: HomeViewModel,
    private val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState
) {
    var localUiState by mutableStateOf(HomeUiState())
        private set

    var isCheckingCommunity by mutableStateOf(false)
        private set

    var showLocationDialog by mutableStateOf(false)
        private set

    var communityCheckCompleted by mutableStateOf(false)
        private set

    var homeState by mutableStateOf<BaseUiState>(BaseUiState.EmptyState)
        private set

    var checkCommunityState by mutableStateOf<BaseUiState>(BaseUiState.EmptyState)
        private set

    var assignCommunityState by mutableStateOf<BaseUiState>(BaseUiState.EmptyState)
        private set

    var user by mutableStateOf<UserModel?>(null)
        private set

    var isFineLocationGranted by mutableStateOf(false)
        private set

    var isCoarseLocationGranted by mutableStateOf(false)
        private set

    var isLocationDeniedPermanently by mutableStateOf(false)
        private set

    val isLoading: Boolean
        get() = homeState is BaseUiState.LoadingState

    val hasAnyLocationPermission: Boolean
        get() = isFineLocationGranted || isCoarseLocationGranted

    init {
        observeViewModelStates()
        observeUserChanges()
        observeCheckCommunityState()
        observeAssignCommunityState()
        observeHomeState()
        observeLocationPermissions()
        checkInitialPermissions()
    }

    private fun observeViewModelStates() {
        scope.launch {
            viewModel.homeUiState.collect { state ->
                homeState = state
            }
        }

        scope.launch {
            viewModel.checkCommunityState.collect { state ->
                checkCommunityState = state
            }
        }

        scope.launch {
            viewModel.assignCommunityState.collect { state ->
                assignCommunityState = state
            }
        }

        scope.launch {
            viewModel.user.collect { userData ->
                user = userData
            }
        }
    }

    private fun observeLocationPermissions() {
        scope.launch {
            viewModel.isFineLocationGranted.collect { granted ->
                isFineLocationGranted = granted
            }
        }

        scope.launch {
            viewModel.isCoarseLocationGranted.collect { granted ->
                isCoarseLocationGranted = granted
            }
        }

        scope.launch {
            viewModel.isLocationDeniedPermanently.collect { denied ->
                isLocationDeniedPermanently = denied
            }
        }
    }

    private fun observeUserChanges() {
        scope.launch {
            snapshotFlow { user }.collect { userData ->
                userData?.let {
                    localUiState = localUiState.copy(userName = it.getDisplayName())
                }
            }
        }
    }

    private fun observeCheckCommunityState() {
        scope.launch {
            snapshotFlow { checkCommunityState }.collect { state ->
                when (state) {
                    is BaseUiState.LoadingState -> isCheckingCommunity = true

                    is BaseUiState.SuccessState<*> -> {
                        isCheckingCommunity = false
                        handleCheckCommunitySuccess(state.data as? CommunityValidationResponseModel)
                        viewModel.resetCheckCommunityState()
                    }

                    is BaseUiState.ErrorState -> {
                        isCheckingCommunity = false
                        showLocationDialog = true
                        communityCheckCompleted = true
                        showSnackbar(state.message)
                        viewModel.resetCheckCommunityState()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeAssignCommunityState() {
        scope.launch {
            snapshotFlow { assignCommunityState }.collect { state ->
                when (state) {
                    is BaseUiState.LoadingState -> isCheckingCommunity = true

                    is BaseUiState.SuccessState<*> -> {
                        isCheckingCommunity = false
                        handleAssignCommunitySuccess(state.data as? CommunityValidationResponseModel)
                        viewModel.resetAssignCommunityState()
                    }

                    is BaseUiState.ErrorState -> {
                        isCheckingCommunity = false
                        showSnackbar(state.message)
                        viewModel.resetAssignCommunityState()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun observeHomeState() {
        scope.launch {
            snapshotFlow { homeState }.collect { state ->
                when (state) {
                    is BaseUiState.SuccessState<*> -> {
                        val incident = state.data as? List<IncidentModel>
                        incident?.let {
                            localUiState = localUiState.copy(
                                recentActivities = it,
                                isRefreshing = false
                            )
                        }
                        viewModel.resetState()
                    }

                    is BaseUiState.ErrorState -> {
                        showSnackbar(state.message)
                        viewModel.resetState()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun checkInitialPermissions() {
        scope.launch {
            if (!communityCheckCompleted && !viewModel.hasLocationPermission()) {
                showLocationDialog = true
            }
        }
    }

    private fun handleCheckCommunitySuccess(response: CommunityValidationResponseModel?) {
        response?.let {
            localUiState = localUiState.copy(
                hasCommunity = it.hasCommunity,
                communityName = it.community?.name
            )

            if (!it.hasCommunity) {
                showLocationDialog = true
            }
        }
        communityCheckCompleted = true
    }

    private fun handleAssignCommunitySuccess(response: CommunityValidationResponseModel?) {
        response?.let {
            localUiState = localUiState.copy(
                hasCommunity = it.hasCommunity,
                communityName = it.community?.name
            )
            showLocationDialog = false
            showSnackbar(it.message ?: "Comunidad asignada correctamente")
        }
    }

    private fun showSnackbar(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    fun onDismissLocationDialog() {
        showLocationDialog = false
        communityCheckCompleted = true
    }

    fun handleLocationPermissionsResult(
        permissions: Map<String, Boolean>,
        shouldShowRationale: Boolean
    ) {
        viewModel.handleLocationPermissionsResult(permissions, shouldShowRationale)
    }

    fun onRefresh() {
        scope.launch {
            viewModel.loadHomeData()
        }
    }
}


@Composable
fun rememberHomeScreenState(
    viewModel: HomeViewModel,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope()
): HomeScreenState {
    return remember(viewModel, snackbarHostState, scope) {
        HomeScreenState(viewModel, scope, snackbarHostState)
    }
}