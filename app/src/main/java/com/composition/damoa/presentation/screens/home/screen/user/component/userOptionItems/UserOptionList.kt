package com.composition.damoa.presentation.screens.home.screen.user.component.userOptionItems

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.composition.damoa.presentation.common.extensions.navigateToPrivacy
import com.composition.damoa.presentation.common.extensions.navigateToTermOfUse
import com.composition.damoa.presentation.common.extensions.navigateToWebsite
import com.composition.damoa.presentation.screens.giftcard.GiftCardActivity
import com.composition.damoa.presentation.screens.store.StoreActivity


@Composable
fun UserOptionList(
    modifier: Modifier = Modifier,
    myOwnTicketCount: Int,
    isLogin: Boolean,
) {
    val context = LocalContext.current

    Column(modifier) {
        if (isLogin) {
            TicketPurchase(myOwnTicketCount) { StoreActivity.startActivity(context) }
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