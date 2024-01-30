package com.composition.damoa.presentation.screens.profileCreation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composition.damoa.R
import com.composition.damoa.data.model.Pet
import com.composition.damoa.data.model.PetColor
import com.composition.damoa.data.model.ProfileConceptDetail
import com.composition.damoa.presentation.common.extensions.onUi
import com.composition.damoa.presentation.screens.login.LoginActivity
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationViewModel.Companion.KEY_CONCEPT_ID
import com.composition.damoa.presentation.screens.profileCreation.ProfileCreationViewModel.UiEvent
import com.composition.damoa.presentation.screens.profileCreation.state.PetInfoUiState
import com.composition.damoa.presentation.ui.theme.PetudioTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProfileCreationActivity : ComponentActivity() {
    private val viewModel: ProfileCreationViewModel by viewModels()
    private val photoPicker = PhotoPicker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileCreation(
                viewModel = viewModel,
                onPhotoSelect = ::launchPhotoPicker
            )
        }
    }

    private fun launchPhotoPicker() {
        val selectedImages = viewModel.selectedImages.value
        photoPicker.launchPhotoPicker(this, selectedImages) { images ->
            // images를 용량 줄여서 File로 map (View 로직)
            // viewModel에서 해당 file을 서버로 보내 동물 사진인지 파악 (ViewModel 로직)
            // 동물 사진인 images만 viewModel.selectedImages에 저장 (ViewModel 로직)
            // 해당 작업이 끝나면 PhotoUploadResultScreen으로 이동 (View 로직)
        }
    }

    companion object {
        fun startActivity(context: Context, conceptId: Long) {
            context.startActivity(getIntent(context, conceptId))
        }

        private fun getIntent(
            context: Context,
            conceptId: Long,
        ): Intent = Intent(context, ProfileCreationActivity::class.java)
            .putExtra(KEY_CONCEPT_ID, conceptId)
    }
}

@Composable
private fun ProfileCreation(
    viewModel: ProfileCreationViewModel,
    onPhotoSelect: () -> Unit = {},
) {
    PetudioTheme {
        val activity = LocalContext.current as? ComponentActivity
        val navController = rememberNavController()
        val conceptDetailUiState by viewModel.conceptDetailUiState.collectAsStateWithLifecycle()
        val petPhotosUiState by viewModel.petPhotosUiState.collectAsStateWithLifecycle()
        val petUiState by viewModel.petInfoUiState.collectAsStateWithLifecycle()

        activity?.onUi {
            viewModel.uiEvent.collectLatest { event ->
                when (event) {
                    UiEvent.PAYMENT_SUCCESS -> navController.navigate(ProfileCreationScreen.PAYMENT_RESULT.route)
                    UiEvent.PAYMENT_FAILED_LACK_OF_COIN -> navController.navigate(ProfileCreationScreen.PAYMENT.route)
                    UiEvent.TOKEN_EXPIRED -> {
                        LoginActivity.startActivity(activity)
                        activity.finish()
                    }
                }
            }
        }

        Scaffold(
            topBar = {
                ProfileCreationTopAppBar(
                    onNavigationClick = { if (!navController.popBackStack()) activity?.finish() },
                )
            },
        ) { padding ->
            ProfileCreationNavHost(
                modifier = Modifier.padding(top = padding.calculateTopPadding()),
                navController = navController,
                profileConceptDetail = conceptDetailUiState.conceptDetail,
                pets = petPhotosUiState.pets,
                petInfoUiState = petUiState,
                onPetNameChanged = viewModel::updatePetName,
                onPetColorSelected = viewModel::updateColor,
                onPetAddClick = viewModel::addPetWithPayment,
                onPhotoSelect = onPhotoSelect,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProfileCreationTopAppBar(onNavigationClick: () -> Unit = {}) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}

@Composable
private fun ProfileCreationNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    profileConceptDetail: ProfileConceptDetail,
    pets: List<Pet>,
    petInfoUiState: PetInfoUiState,
    onPetNameChanged: (String) -> Unit,
    onPetColorSelected: (PetColor) -> Unit,
    onPetAddClick: () -> Unit,
    onPhotoSelect: () -> Unit = {},
    startDestination: ProfileCreationScreen = ProfileCreationScreen.PROFILE_CREATION_INTRODUCE,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route,
    ) {
        composable(ProfileCreationScreen.PROFILE_CREATION_INTRODUCE.route) {
            ProfileCreationIntroduceScreen(
                navController = navController,
                profileConceptDetail = profileConceptDetail
            )
        }
        composable(ProfileCreationScreen.PET_PHOTO_SELECT.route) {
            PetPhotoSelectScreen(pets = pets, navController = navController)
        }
        composable(ProfileCreationScreen.PET_NAME.route) {
            PetNameScreen(
                navController = navController,
                petName = petInfoUiState.petName,
                onPetNameChanged = onPetNameChanged
            )
        }
        composable(ProfileCreationScreen.PET_COLOR.route) {
            PetColorScreen(
                navController = navController,
                selectedPetColor = petInfoUiState.petColor,
                onPetColorSelected = onPetColorSelected,
            )
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_INTRODUCE.route) {
            PhotoUploadIntroduceScreen(navController = navController, onClickPhotoUpload = onPhotoSelect)
        }
        composable(ProfileCreationScreen.PHOTO_UPLOAD_RESULT.route) {
            PhotoUploadResultScreen(
                navController = navController,
                isShowKeepGoingButton = petInfoUiState.isValidPetPhotoSize(),
                onPetAddClick = {
                    // 포인트 부족하면 결제하기 화면
                    // 포인트 있으면 이미지 전송하고 완료 화면
                }
            )
        }
        composable(ProfileCreationScreen.PAYMENT.route) {
            PaymentScreen(navController = navController)
        }
        composable(ProfileCreationScreen.PAYMENT_RESULT.route) {
            PaymentResultScreen(navController = navController)
        }
    }
}

enum class ProfileCreationScreen(
    val route: String,
) {
    PROFILE_CREATION_INTRODUCE("profileCreationIntroduce"),
    PET_PHOTO_SELECT("petPhotoSelect"),
    PET_NAME("petName"),
    PET_COLOR("petColor/{petType}"),
    PHOTO_UPLOAD_INTRODUCE("photoUploadIntroduce"),
    PHOTO_UPLOAD_RESULT("photoUploadResult"),
    PAYMENT("payment"),
    PAYMENT_RESULT("paymentResult"),
}
