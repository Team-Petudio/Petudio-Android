package com.composition.damoa.presentation.screens.profileCreation.screen.payment

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.composition.damoa.R
import com.composition.damoa.data.model.PetType
import com.composition.damoa.presentation.common.extensions.showToast
import com.composition.damoa.presentation.common.utils.permissionRequester.Permission
import com.composition.damoa.presentation.common.utils.permissionRequester.PermissionRequester
import com.composition.damoa.presentation.screens.profileCreation.screen.payment.component.PaymentResultContent


@Composable
fun PaymentResultScreen(
    modifier: Modifier = Modifier,
    petType: PetType,
) {
    launchNotificationPermissionRequester(context = LocalContext.current)

    Surface(
        color = Color.White,
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize(),
    ) {
        PaymentResultContent(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            petType = petType,
        )
    }
}

private fun launchNotificationPermissionRequester(context: Context) {
    PermissionRequester().launch(
        context = context,
        permission = Permission.POST_NOTIFICATION,
        dialogMessage = context.getString(R.string.profile_completion_push_permission_request_message),
        onGranted = { context.showToast(R.string.profile_completion_push_permission_granted_message) },
        onDenied = { context.showToast(R.string.profile_completion_push_permission_denied_message) },
    )
}
