package com.composition.damoa.presentation.screens.home.screen.user

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.data.model.User
import com.composition.damoa.presentation.common.components.BigDescription
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.LoginButton
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.common.components.PetudioDialog
import com.composition.damoa.presentation.common.extensions.navigateToPrivacy
import com.composition.damoa.presentation.common.extensions.navigateToTermOfUse
import com.composition.damoa.presentation.common.extensions.navigateToWebsite
import com.composition.damoa.presentation.screens.giftcard.GiftCardActivity
import com.composition.damoa.presentation.screens.home.screen.user.state.UserUiState
import com.composition.damoa.presentation.screens.ticketPurchase.TicketPurchaseActivity
import com.composition.damoa.presentation.ui.theme.AlertIconColor
import com.composition.damoa.presentation.ui.theme.Gray10
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.Gray40


@Composable
fun UserScreen(
    modifier: Modifier = Modifier,
    userUiState: UserUiState,
    onLogout: () -> Unit,
    onSignOut: () -> Unit,
    onLogin: () -> Unit,
) {
    var isShowLogoutDialog by rememberSaveable { mutableStateOf(false) }
    var isShowSignOutDialog by rememberSaveable { mutableStateOf(false) }
    val user = userUiState.user
    val isLogin = userUiState.isLogined

    Column(
        modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileTitle()
        if (isLogin) UserAccount(modifier = Modifier.padding(top = 28.dp), user = user)
        SettingList(modifier = Modifier.padding(top = 28.dp), ticket = user.ticket, isLogin = isLogin)
        Spacer(modifier = Modifier.weight(1F))
        if (isLogin) {
            LogoutButton(modifier = Modifier.padding(top = 28.dp)) { isShowLogoutDialog = true }
            SignOutButton(modifier = Modifier.padding(top = 12.dp)) { isShowSignOutDialog = true }
        } else {
            LoginButton(modifier = Modifier.padding(bottom = 28.dp), onClick = onLogin)
        }

        if (isShowLogoutDialog) {
            LogoutDialog(
                onDismissClick = { isShowLogoutDialog = false },
                onLogoutClick = {
                    onLogout()
                    isShowLogoutDialog = false
                },
            )
        }
        if (isShowSignOutDialog) {
            SignOutDialog(
                onDismissClick = { isShowSignOutDialog = false },
                onSignOutClick = {
                    onSignOut()
                    isShowSignOutDialog = false
                },
            )
        }
    }
}

@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Gray30),
        onClick = onClick,
    ) {
        MediumDescription(descriptionRes = R.string.logout, fontColor = Color.Black)
    }
}

@Composable
private fun SignOutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        TextButton(onClick = onClick) {
            Text(
                text = stringResource(R.string.sign_out),
                color = Color.Black,
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

@Composable
private fun ProfileTitle() {
    BigTitle(titleRes = R.string.profile_title)
}

@Composable
private fun UserAccount(
    modifier: Modifier = Modifier,
    user: User,
) {
    when (user.socialType) {
        User.SocialType.GOOGLE -> GoogleAccount(modifier, user.email)
        User.SocialType.KAKAO -> KakaoAccount(modifier, user.email)
        User.SocialType.APPLE -> Unit
    }
}

@Composable
private fun GoogleAccount(
    modifier: Modifier = Modifier,
    email: String,
) {
    Account(
        modifier = modifier,
        iconRes = R.drawable.ic_account_google,
        email = email,
    )
}

@Composable
private fun KakaoAccount(
    modifier: Modifier = Modifier,
    email: String,
) {
    Account(
        modifier = modifier,
        iconRes = R.drawable.ic_account_kakao,
        email = email,
    )
}

@Composable
private fun Account(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    email: String,
) {
    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Gray10)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = email,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 8.dp),
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
private fun SettingList(
    modifier: Modifier = Modifier,
    ticket: Int,
    isLogin: Boolean,
) {
    val context = LocalContext.current
    Column(modifier) {
        if (isLogin) {
            TicketPurchase(ticket) { TicketPurchaseActivity.startActivity(context) }
            GiftCard { GiftCardActivity.startActivity(context) }
        }
        Question { context.navigateToWebsite("https://petudio.notion.site/09ab51dbfaae49bfbb3cc9278fdcdb19?pvs=4") }
        Email { context.sendEmail() }
        TermOfUse { context.navigateToTermOfUse() }
        Privacy { context.navigateToPrivacy() }
    }
}

private fun Context.sendEmail() {
    val emailIntent = Intent(Intent.ACTION_SENDTO)
        .setData(Uri.parse("mailto:official.petudio@gmail.com"))
        .putExtra(Intent.EXTRA_SUBJECT, "[Petudio] ")

    if (emailIntent.resolveActivity(packageManager) != null) {
        startActivity(emailIntent)
    }
}

@Composable
private fun TicketPurchase(
    ticketCount: Int,
    onClick: () -> Unit,
) {
    SettingOptionItem(
        item = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BigDescription(
                    descriptionRes = R.string.profile_option_ticket,
                    fontColor = Color.Black,
                )
                Ticket(value = ticketCount)
            }
        },
        onClick = onClick,
    )
}

@Composable
private fun GiftCard(
    onClick: () -> Unit,
) {
    SettingOptionItem(
        item = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                BigDescription(
                    descriptionRes = R.string.item_giftcard,
                    fontColor = Color.Black,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_giftcard),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        },
        onClick = onClick,
    )
}

@Composable
private fun Question(
    onClick: () -> Unit,
) {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_question,
                fontColor = Color.Black,
            )
        },
        onClick = onClick,
    )
}

@Composable
private fun Email(
    onClick: () -> Unit,
) {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_ask,
                fontColor = Color.Black,
            )
        },
        onClick = onClick,
    )
}

@Composable
private fun TermOfUse(
    onClick: () -> Unit,
) {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_terms_of_use,
                fontColor = Color.Black,
            )
        },
        onClick = onClick,
    )
}

@Composable
private fun Privacy(
    onClick: () -> Unit,
) {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_privacy,
                fontColor = Color.Black,
            )
        },
        onClick = onClick,
    )
}

@Composable
private fun Ticket(
    modifier: Modifier = Modifier,
    value: Int,
) {
    Row(
        modifier = modifier.padding(end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(end = 4.dp),
            painter = painterResource(id = R.drawable.ic_ticket),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = String.format("%,d", value),
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun SettingOptionItem(
    modifier: Modifier = Modifier,
    item: @Composable () -> Unit,
    endIcon: @Composable () -> Unit = { Icon(Icons.Filled.KeyboardArrowRight, null, tint = Gray40) },
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .background(Gray10)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(modifier = Modifier.weight(1F)) { item() }
        endIcon()
    }
}

@Composable
private fun LogoutDialog(
    onDismissClick: () -> Unit = {},
    onLogoutClick: () -> Unit,
) {
    PetudioDialog(
        dialogTitleRes = R.string.logout,
        dialogDescRes = R.string.logout_desc,
        onDismissClick = onDismissClick,
        onConfirmClick = onLogoutClick,
    )
}

@Composable
private fun SignOutDialog(
    onDismissClick: () -> Unit = {},
    onSignOutClick: () -> Unit,
) {
    PetudioDialog(
        painter = painterResource(R.drawable.alert),
        iconColor = AlertIconColor,
        dialogTitleRes = R.string.sign_out,
        dialogDescRes = R.string.sign_out_desc,
        onDismissClick = onDismissClick,
        onConfirmClick = onSignOutClick,
    )
}