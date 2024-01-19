package com.composition.damoa.presentation.screens.home

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composition.damoa.R
import com.composition.damoa.presentation.common.components.BigDescription
import com.composition.damoa.presentation.common.components.BigTitle
import com.composition.damoa.presentation.common.components.MediumDescription
import com.composition.damoa.presentation.screens.pointCharge.PointChargeActivity
import com.composition.damoa.presentation.ui.theme.Gray10
import com.composition.damoa.presentation.ui.theme.Gray30
import com.composition.damoa.presentation.ui.theme.Gray40

data class Account(
    val email: String,
    val accountType: AccountType,
) {
    enum class AccountType {
        GOOGLE,
        KAKAO,
        APPLE,
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileTitle()
        UserAccount(
            modifier = Modifier.padding(top = 28.dp),
            account = Account(email = "petudio@naver.com", accountType = Account.AccountType.GOOGLE),
        )
        SettingList(modifier = Modifier.padding(top = 28.dp))
        Spacer(modifier = Modifier.weight(1F))
        LogoutButton(modifier = Modifier.padding(top = 28.dp))
        SignOutButton(modifier = Modifier.padding(top = 12.dp))
    }
}

@Composable
private fun LogoutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        modifier =
            modifier
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
    account: Account,
) {
    when (account.accountType) {
        Account.AccountType.GOOGLE -> GoogleAccount(modifier, account.email)
        Account.AccountType.KAKAO -> KakaoAccount(modifier, account.email)
        Account.AccountType.APPLE -> AppleAccount(modifier, account.email)
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
private fun AppleAccount(
    modifier: Modifier = Modifier,
    email: String,
) {
    Account(
        modifier = modifier,
        iconRes = R.drawable.ic_account_apple,
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
    coin: Int = 1000,
) {
    val context = LocalContext.current
    Column(modifier) {
        PointCharge(coin) { context.startActivity(PointChargeActivity.getIntent(context)) }
        Question()
        Ask()
        TermOfUse()
        Privacy()
    }
}

@Composable
private fun PointCharge(
    coin: Int,
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
                    descriptionRes = R.string.profile_option_point,
                    fontColor = Color.Black,
                )
                Point(coin = coin)
            }
        },
        onClick = onClick,
    )
}

@Composable
private fun Question() {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_question,
                fontColor = Color.Black,
            )
        },
    )
}

@Composable
private fun Ask() {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_ask,
                fontColor = Color.Black,
            )
        },
    )
}

@Composable
private fun TermOfUse() {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_terms_of_use,
                fontColor = Color.Black,
            )
        },
    )
}

@Composable
private fun Privacy() {
    SettingOptionItem(
        item = {
            BigDescription(
                descriptionRes = R.string.profile_option_privacy,
                fontColor = Color.Black,
            )
        },
    )
}

@Composable
private fun Point(
    modifier: Modifier = Modifier,
    coin: Int,
) {
    Row(
        modifier = modifier.padding(end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(end = 4.dp),
            painter = painterResource(id = R.drawable.img_gold_coin),
            contentDescription = null,
            tint = Color.Unspecified,
        )
        Text(
            text = String.format("%,d", coin),
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
        modifier =
            modifier
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

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}